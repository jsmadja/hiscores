package com.shmup.hiscores.controllers;

import com.shmup.hiscores.dto.ScoreForm;
import com.shmup.hiscores.models.*;
import com.shmup.hiscores.services.*;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.apache.commons.lang3.StringUtils.*;

@Deprecated
@AllArgsConstructor
@RestController
public class PlayerController {

    private final ModeService modeService;
    private final ShipService shipService;
    private final PlayerService playerService;
    private final ScoreService scoreService;
    private final GameService gameService;
    private final PlatformService platformService;
    private final StageService stageService;
    private final DifficultyService difficultyService;

    @RequestMapping("/me")
    public Player me(@RequestAttribute("player") Player player) {
        return player;
    }

    @RequestMapping("/me/recommendations")
    public Recommendations getRecommendations(@RequestAttribute("player") Player player) {
        return playerService.getRecommendationsFor(player);
    }

    @RequestMapping("/me/kill-list")
    public List<KillListItem> getKillList(@RequestAttribute("player") Player player) {
        return playerService.getKillListFor(player);
    }

    @RequestMapping("/me/games")
    public List<Game> findGames(@RequestAttribute("player") Player player) {
        return gameService.findByPlayer(player);
    }

    @RequestMapping("/me/scores")
    public List<Score> findScores(@ApiIgnore @RequestAttribute("player") Player player) {
        return scoreService.getLastScoresOf(player);
    }

    @RequestMapping(value = "/me/scores/{id}", method = RequestMethod.POST)
    public Score edit(@ApiIgnore @RequestAttribute("player") Player player,
                      @PathVariable("id") Score score,
                      @RequestParam(required = false) MultipartFile photo,
                      @RequestParam(required = false) MultipartFile inp,
                      @RequestParam(required = false) String value,
                      @RequestParam Platform platform,
                      @RequestParam(required = false) Stage stage,
                      @RequestParam(required = false) Mode mode,
                      @RequestParam(required = false) Ship ship,
                      @RequestParam(required = false) Difficulty difficulty,
                      @RequestParam(required = false) String comment,
                      @RequestParam(required = false) String replay,
                      @RequestParam(required = false) Integer minutes,
                      @RequestParam(required = false) Integer seconds,
                      @RequestParam(required = false) Integer milliseconds) throws IOException {
        Logger.getAnonymousLogger().info("Mise a jour du score envoyé par " + player.getName());
        if (!score.isPlayedBy(player)) {
            throw new RuntimeException("Unauthorized");
        }
        score.setStage(stage);
        score.setMode(mode);
        score.setDifficulty(difficulty);
        score.setShip(ship);
        score.setComment(comment);
        score.setPlatform(platform);
        score.setValue(toScoreValue(value, minutes, seconds, milliseconds));
        if (photo != null) {
            storePhoto(score, photo);
        }
        if (inp != null) {
            storeInp(score, inp);
        }
        score.setReplay(replay);
        if (score.getValue() == null) {
            throw new RuntimeException("Invalid score value");
        }
        Optional<Score> bestScore = scoreService.getBestScoreFor(player, score.getGame(), score.getMode(), score.getDifficulty());
        Integer oldRank = null;
        if (bestScore.isPresent()) {
            oldRank = bestScore.get().getRank();
        }
        scoreService.update(score);
        gameService.recomputeRanking(score.getGame(), score);
        score = scoreService.refresh(score);
        if (oldRank != null && score.getRank() != null) {
            score.setProgression(oldRank - score.getRank());
            scoreService.update(score);
        }
        return score;
    }

    @RequestMapping(value = "/me/scores", method = RequestMethod.POST)
    public Score submit(
            @ApiIgnore @RequestAttribute("player") Player player,
            @RequestParam(required = false) MultipartFile photo,
            @RequestParam(required = false) MultipartFile inp,
            @RequestParam(required = false) String value,
            @RequestParam Long game,
            @RequestParam Long platform,
            @RequestParam(required = false) Long stage,
            @RequestParam(required = false) Long mode,
            @RequestParam(required = false) Long ship,
            @RequestParam(required = false) Long difficulty,
            @RequestParam(required = false) String comment,
            @RequestParam(required = false) String replay,
            @RequestParam(required = false) Integer minutes,
            @RequestParam(required = false) Integer seconds,
            @RequestParam(required = false) Integer milliseconds
    ) throws IOException {
        if (!player.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }

        ScoreForm scoreForm = new ScoreForm(value, minutes, seconds, milliseconds, platform, game, comment, replay, photo, inp, difficulty, ship, mode, stage);

        Logger.getAnonymousLogger().info("Nouveau score envoyé par " + player.getName() + ", " + scoreForm.toString());
        Score score = createScore(scoreForm, player);
        if (score.getValue() == null) {
            throw new RuntimeException("Veuillez saisir une valeur de score.");
        }
        if (photo != null) {
            storePhoto(score, photo);
        }
        if (inp != null) {
            storeInp(score, inp);
        }
        Optional<Score> bestScore = scoreService.getBestScoreFor(player, score.getGame(), score.getMode(), score.getDifficulty());
        Integer oldRank = null;
        if (bestScore.isPresent()) {
            oldRank = bestScore.get().getRank();
        }
        scoreService.save(score);
        gameService.recomputeRanking(score.getGame(), score);
        score = scoreService.refresh(score);

        if (oldRank != null && score.getRank() != null) {
            score.setProgression(oldRank - score.getRank());
            scoreService.update(score);
        }
        return score;
    }

    private Score createScore(ScoreForm data, Player player) {
        String login = player.getName();
        return createScore(data, login);
    }

    private Score createScore(ScoreForm data, String login) {
        Difficulty difficulty = difficulty(data);
        Stage stage = stage(data);
        Ship ship = ship(data);
        Mode mode = mode(data);
        Platform platform = platformService.findById(data.getPlatform());
        Player player = playerService.findOrCreatePlayer(login);
        Game game = gameService.findById(data.getGame());
        BigDecimal value = value(data);
        String comment = data.getComment();
        String replay = data.getReplay();
        return new Score(game, player, stage, ship, mode, difficulty, comment, platform, value, replay);
    }

    private Stage stage(ScoreForm data) {
        Long stage = data.getStage();
        if (stage == null) {
            return null;
        }
        return stageService.findById(stage);
    }

    private Mode mode(ScoreForm data) {
        Mode mode = null;
        if (data.getMode() != null) {
            mode = modeService.findById(data.getMode());
        }
        return mode;
    }

    private Ship ship(ScoreForm data) {
        Ship ship = null;
        if (data.getShip() != null) {
            ship = shipService.findById(data.getShip());
        }
        return ship;
    }

    private void storePhoto(Score score, MultipartFile filePart) throws IOException {
        String filename = filePart.getOriginalFilename().replaceAll("[^a-zA-Z0-9.]+", "");
        String pathname = "/photos/" + new Date().getTime() + "-" + filename;
        Files.copy(filePart.getInputStream(), new File(pathname).toPath());
        score.setPhoto("https://hiscores.shmup.com" + pathname);
    }

    private void storeInp(Score score, MultipartFile filePart) throws IOException {
        String filename = filePart.getOriginalFilename().replaceAll("[^a-zA-Z0-9.]+", "");
        String pathname = "/inp/" + new Date().getTime() + "-" + filename;
        Files.copy(filePart.getInputStream(), new File(pathname).toPath());
        score.setInp("https://hiscores.shmup.com" + pathname);
    }

    public BigDecimal value(ScoreForm data) {
        String scoreValue = data.getValue();
        Integer minutes = data.getMinutes();
        Integer seconds = data.getSeconds();
        Integer milliseconds = data.getMilliseconds();
        return toScoreValue(scoreValue, minutes, seconds, milliseconds);
    }

    public BigDecimal toScoreValue(String scoreValue, Integer minutes, Integer seconds, Integer milliseconds) {
        if (isBlank(scoreValue) && minutes == null && seconds == null && milliseconds == null) {
            return null;
        }
        if (isNotBlank(scoreValue)) {
            StringBuilder strValue = new StringBuilder();
            for (Character c : scoreValue.toCharArray()) {
                if (isNumeric(c.toString())) {
                    strValue.append(c);
                }
            }
            return new BigDecimal(strValue.toString());
        } else {
            minutes = minutes == null ? 0 : minutes;
            seconds = seconds == null ? 0 : seconds;
            milliseconds = milliseconds == null ? 0 : milliseconds;

            return BigDecimal.valueOf(new DateTime().withTimeAtStartOfDay().withDate(0, 1, 1).
                    withMinuteOfHour(minutes).
                    withSecondOfMinute(seconds).
                    withMillisOfSecond(milliseconds).
                    getMillis());
        }
    }

    private Difficulty difficulty(ScoreForm data) {
        Difficulty difficulty = null;
        if (data.getDifficulty() != null) {
            difficulty = difficultyService.findById(data.getDifficulty());
        }
        return difficulty;
    }

}
