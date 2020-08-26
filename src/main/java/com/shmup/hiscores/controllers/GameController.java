package com.shmup.hiscores.controllers;

import com.google.common.collect.Lists;
import com.shmup.hiscores.drawer.Images;
import com.shmup.hiscores.drawer.RankingPicture;
import com.shmup.hiscores.dto.*;
import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Platform;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.services.GameService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.javatuples.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@AllArgsConstructor
@RestController
public class GameController {

    private final GameService gameService;

    @Deprecated
    @RequestMapping("/ui/games/names")
    public Iterable<Pair<Long, String>> allIdAndName() {
        return Lists
                .newArrayList(gameService.findAll())
                .stream()
                .map(game -> new Pair<>(game.getId(), game.getTitle()))
                .collect(toList());
    }

    @Deprecated
    @RequestMapping("/games")
    public List<GameDTO> all() {
        return gameService
                .findAll()
                .stream()
                .map(this::toGameDTO)
                .collect(toList());
    }

    @Deprecated
    private GameDTO toGameDTO(Game game) {
        return new GameDTO(
                game.getCover(),
                game.getId(),
                game.getTitle(),
                game.getPlatforms().stream().map(Platform::getName).collect(toList()),
                game.getPlayers().size(),
                game.getScores().size(),
                game.getOneccs().size());
    }

    @PostMapping("/games")
    @ApiOperation(value = "create a new game")
    public ResponseEntity<Game> createGame(@RequestAttribute(value = "player") Player player, @ApiParam(value = "The game to create", required = true) @Valid @RequestBody GameForm gameForm) {
        if (player.isAdministrator()) {
            return new ResponseEntity<>(gameService.createGame(gameForm), CREATED);
        }
        return new ResponseEntity<>(UNAUTHORIZED);
    }

    @PostMapping("/games/{id}/modes")
    @ApiOperation(value = "create a new mode")
    public ResponseEntity<Game> createMode(
            @RequestAttribute(value = "player") Player player,
            @PathVariable("id") Game game,
            @ApiParam(value = "The mode to create", required = true) @Valid @RequestBody GameSetting gameSetting) {
        if (!player.isAdministrator()) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        game.addNewMode(gameSetting);
        gameService.save(game);
        return new ResponseEntity<>(game, CREATED);
    }

    @PostMapping("/games/{id}/difficulties")
    @ApiOperation(value = "create a new difficulty")
    public ResponseEntity<Game> createDifficulty(
            @RequestAttribute(value = "player") Player player,
            @PathVariable("id") Game game,
            @ApiParam(value = "The difficulty to create", required = true) @Valid @RequestBody GameSetting gameSetting) {
        if (!player.isAdministrator()) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        game.addNewDifficulty(gameSetting);
        gameService.save(game);
        return new ResponseEntity<>(game, CREATED);
    }

    @PostMapping("/games/{id}/ships")
    @ApiOperation(value = "create a new ship")
    public ResponseEntity<Game> createShip(
            @RequestAttribute(value = "player") Player player,
            @PathVariable("id") Game game,
            @ApiParam(value = "The ship to create", required = true) @Valid @RequestBody GameSetting gameSetting) {
        if (!player.isAdministrator()) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        game.addNewShip(gameSetting);
        gameService.save(game);
        return new ResponseEntity<>(game, CREATED);
    }

    @PostMapping("/games/{id}/stages")
    @ApiOperation(value = "create a new stage")
    public ResponseEntity<Game> createStage(
            @RequestAttribute(value = "player") Player player,
            @PathVariable("id") Game game,
            @ApiParam(value = "The stage to create", required = true) @Valid @RequestBody GameSetting gameSetting) {
        if (!player.isAdministrator()) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        game.addNewStage(gameSetting);
        gameService.save(game);
        return new ResponseEntity<>(game, CREATED);
    }

    @PostMapping("/games/{id}/platforms")
    @ApiOperation(value = "create a new platform")
    public ResponseEntity<Game> createPlatform(
            @RequestAttribute(value = "player") Player player,
            @PathVariable("id") Game game,
            @ApiParam(value = "The platforms to create", required = true) @RequestBody String[] platforms) {
        if (!player.isAdministrator()) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        game.addNewPlatforms(platforms);
        gameService.save(game);
        return new ResponseEntity<>(game, CREATED);
    }

    @Deprecated
    @RequestMapping("/games/{id}")
    public Game getById(@PathVariable("id") Long id) {
        return gameService.findById(id);
    }

    @Deprecated
    @RequestMapping("/games/{id}/rankings")
    public List<RankingDTO> getRankingsById(@PathVariable("id") Long id) {
        Game game = gameService.findById(id);
        return gameService
                .getRankingsOf(game)
                .stream()
                .map(ranking -> {
                    return new RankingDTO(
                            ranking.getMode(),
                            ranking.getDifficulty(),
                            ranking
                                    .getScores()
                                    .stream()
                                    .map(score -> new ScoreDTO(
                                                    score.getRank(),
                                                    score.getValue(),
                                                    new PlayerDTO(
                                                            score.getPlayer().getId(),
                                                            score.getPlayer().getName()
                                                    ),
                                                    score.is1CC(),
                                                    score.getPhoto(),
                                                    score.getInp(),
                                                    score.getReplay(),
                                                    score.getStageName(),
                                                    score.getId(),
                                                    score.getComment()
                                            )
                                    )
                                    .collect(toList())
                    );
                })
                .collect(toList());
    }

    @Deprecated
    @RequestMapping("/game/{id}/ranking.png")
    public void getRankingPicture(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Game game = gameService.findById(id);
        BufferedImage image = RankingPicture.createRankingPicture(game, gameService.getRankingsOf(game));
        byte[] bytes = Images.toBytes(image);
        response.setContentType("image/png");
        response.setStatus(200);
        response.getOutputStream().write(bytes);
    }

}
