package com.shmup.hiscores.services;

import com.shmup.hiscores.dto.GameForm;
import com.shmup.hiscores.models.*;
import com.shmup.hiscores.repositories.GameRepository;
import com.shmup.hiscores.repositories.ScoreCustomRepository;
import com.shmup.hiscores.repositories.ScoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
public class GameService {

    private static final LocalDate FROM_THE_BEGINNING = LocalDate.EPOCH;
    private static final LocalDate TO_THE_END = LocalDate.of(2050,1,1);
    private final GameRepository gameRepository;
    private final ScoreCustomRepository scoreCustomRepository;
    private final ScoreRepository scoreRepository;

    @Deprecated
    public List<Game> findAll() {
        return gameRepository.findByOrderByTitleAsc();
    }

    @Deprecated
    public void recomputeAllRankings(Game game) {
        for (Score score : game.getAllScores()) {
            score.setRank(null);
            scoreRepository.save(score);
        }
        updateRankingsOf(game);
    }

    @Deprecated
    public void recomputeRanking(Game game, final Score referenceScore) {
        resetRankOfAllScoresInRanking(game, referenceScore);
        updateRankingsOf(game);
    }

    @Deprecated
    private void resetRankOfAllScoresInRanking(Game game, Score referenceScore) {
        Predicate<Score> isInSameRanking = input -> input.hasDifficulty(referenceScore.getDifficulty()) && input.hasMode(referenceScore.getMode());
        List<Score> scores = game.getScores();
        List<Score> scoresInSameRankingAsReferenceScore = scores
                .stream()
                .filter(isInSameRanking)
                .collect(toList());
        for (Score score : scoresInSameRankingAsReferenceScore) {
            score.setRank(null);
            scoreRepository.save(score);
        }
    }

    @Deprecated
    public List<Ranking> getRankingsOf(Game game) {
        List<Ranking> rankings = new ArrayList<>();
        if (game.isGeneralRanking()) {
            rankings.add(createGeneralRanking(game, FROM_THE_BEGINNING, TO_THE_END));
        }
        if (!game.hasModes()) {
            if (!game.hasDifficulties()) {
                rankings.add(createGeneralRanking(game, FROM_THE_BEGINNING, TO_THE_END));
            } else {
                for (Difficulty difficulty : game.getDifficulties()) {
                    rankings.add(new Ranking(findBestScoresByVIPPlayers(game, difficulty, null, FROM_THE_BEGINNING, TO_THE_END), difficulty));
                }
            }
        } else {
            for (Mode mode : game.getModes()) {
                if (!game.hasDifficulties()) {
                    rankings.add(new Ranking(findBestScoresByVIPPlayers(game, null, mode, FROM_THE_BEGINNING, TO_THE_END), mode));
                } else {
                    for (Difficulty difficulty : game.getDifficulties()) {
                        rankings.add(new Ranking(findBestScoresByVIPPlayers(game, difficulty, mode, FROM_THE_BEGINNING, TO_THE_END), difficulty, mode));
                    }
                }
            }
        }
        return rankings;
    }

    public List<Ranking> getEventRankingsOf(Game game, LocalDate startDate, LocalDate endDate) {
        List<Ranking> rankings = new ArrayList<>();
        if (game.isGeneralRanking()) {
            rankings.add(createGeneralRanking(game, startDate, endDate));
        }
        if (!game.hasModes()) {
            if (!game.hasDifficulties()) {
                rankings.add(createGeneralRanking(game, startDate, endDate));
            } else {
                for (Difficulty difficulty : game.getDifficulties()) {
                    Collection<Score> bestScoresByVIPPlayers = findBestScoresByVIPPlayers(game, difficulty, null, startDate, endDate);
                    rankings.add(new Ranking(overrideRanks(bestScoresByVIPPlayers), difficulty));
                }
            }
        } else {
            for (Mode mode : game.getModes()) {
                if (!game.hasDifficulties()) {
                    Collection<Score> bestScoresByVIPPlayers = findBestScoresByVIPPlayers(game, null, mode, startDate, endDate);
                    rankings.add(new Ranking(overrideRanks(bestScoresByVIPPlayers), mode));
                } else {
                    for (Difficulty difficulty : game.getDifficulties()) {
                        Collection<Score> bestScoresByVIPPlayers = findBestScoresByVIPPlayers(game, difficulty, mode, startDate, endDate);
                        rankings.add(new Ranking(overrideRanks(bestScoresByVIPPlayers), difficulty, mode));
                    }
                }
            }
        }
        return rankings;
    }

    private Collection<Score> overrideRanks(Collection<Score> bestScoresByVIPPlayers) {
        final AtomicInteger rank = new AtomicInteger(1);
        return bestScoresByVIPPlayers
                .stream()
                .peek(score -> score.setRank(rank.getAndAdd(1)))
                .collect(toList());
    }

    @Deprecated
    public List<Ranking> updateRankingsOf(Game game) {
        List<Ranking> rankings = new ArrayList<>();
        if (game.isGeneralRanking()) {
            rankings.add(createGeneralRanking(game, FROM_THE_BEGINNING, TO_THE_END));
        }
        if (!game.hasModes()) {
            if (!game.hasDifficulties()) {
                rankings.add(createGeneralRanking(game, FROM_THE_BEGINNING, TO_THE_END));
            } else {
                for (Difficulty difficulty : game.getDifficulties()) {
                    rankings.add(new Ranking(findBestScoresByVIPPlayers(game, difficulty, null, FROM_THE_BEGINNING, TO_THE_END), difficulty));
                }
            }
        } else {
            for (Mode mode : game.getModes()) {
                if (!game.hasDifficulties()) {
                    rankings.add(new Ranking(findBestScoresByVIPPlayers(game, null, mode, FROM_THE_BEGINNING, TO_THE_END), mode));
                } else {
                    for (Difficulty difficulty : game.getDifficulties()) {
                        rankings.add(new Ranking(findBestScoresByVIPPlayers(game, difficulty, mode, FROM_THE_BEGINNING, TO_THE_END), difficulty, mode));
                    }
                }
            }
        }
        for (Ranking ranking : rankings) {
            for (Score score : ranking.getScores()) {
                scoreRepository.save(score);
            }
        }
        return rankings;
    }

    private Ranking createGeneralRanking(Game game, LocalDate startDate, LocalDate endDate) {
        Ranking ranking = new Ranking(findBestScoresByVIPPlayers(game, startDate, endDate));
        List<Score> scores = new ArrayList<>();
        for (int rank = 0; rank < ranking.getScores().size(); rank++) {
            Score score = ranking.getScores().get(rank);
            scores.add(new Score(score.getCreatedAt(), score.getUpdatedAt(), score.getId(), score.getGame(), score.getPlayer(), score.getStage(), score.getShip(), score.getMode(), score.getDifficulty(), score.getComment(), score.getPlatform(), score.getValue(), score.getReplay(), score.getPhoto(), rank + 1));
        }
        Ranking generalRanking = new Ranking(scores);
        generalRanking.setGeneral(true);
        return generalRanking;
    }

    private Collection<Score> findBestScoresByVIPPlayers(Game game, LocalDate startDate, LocalDate endDate) {
        if (game.getScores() == null) {
            return new ArrayList<>();
        }
        List<Score> scores = scoreCustomRepository.findBestScores(game, startDate, endDate);
        return keepOnlyVIP(scores);
    }

    @Deprecated
    public Collection<Score> findBestScoresByVIPPlayers(Game game, Difficulty difficulty, Mode mode, LocalDate startDate, LocalDate endDate) {
        List<Score> scores = scoreCustomRepository.findBestScores(game, difficulty, mode, startDate, endDate);
        return keepOnlyVIP(scores);
    }

    private Collection<Score> keepOnlyVIP(List<Score> scores) {
        Set<Player> players = new HashSet<>();
        return scores.stream().filter(score -> {
            if (players.contains(score.getPlayer())) {
                return false;
            }
            if (!score.isVip()) {
                return false;
            }
            players.add(score.getPlayer());
            return true;
        }).collect(toList());
    }

    public Game findById(Long gameId) {
        return gameRepository.findById(gameId).get();
    }

    @Deprecated
    public long getGamesCount() {
        return gameRepository.count();
    }

    public Game createGame(GameForm gameForm) {
        AtomicLong difficultySortOrder = new AtomicLong();
        AtomicLong modeSortOrder = new AtomicLong();
        AtomicLong shipSortOrder = new AtomicLong();
        AtomicLong stageSortOrder = new AtomicLong();
        Game game = new Game();
        game.setCover(gameForm.getCover());
        game.setTitle(gameForm.getTitle());
        game.setThread(gameForm.getThread());
        game.setDifficulties(stream(gameForm
                .getDifficulties())
                .map(difficultyName -> {
                    Difficulty difficulty = new Difficulty();
                    difficulty.setGame(game);
                    difficulty.setName(difficultyName);
                    difficulty.setSortOrder(difficultySortOrder.addAndGet(10));
                    return difficulty;
                })
                .collect(toList()));
        game.setModes(stream(gameForm
                .getModes())
                .map(modeName -> {
                    Mode mode = new Mode();
                    mode.setGame(game);
                    mode.setName(modeName);
                    mode.setSortOrder(modeSortOrder.addAndGet(10));
                    return mode;
                })
                .collect(toList()));
        game.setShips(stream(gameForm
                .getShips())
                .map(shipName -> {
                    Ship ship = new Ship();
                    ship.setGame(game);
                    ship.setName(shipName);
                    ship.setSortOrder(shipSortOrder.addAndGet(10));
                    return ship;
                })
                .collect(toList()));
        game.setStages(stream(gameForm
                .getStages())
                .map(stageName -> {
                    Stage stage = new Stage();
                    stage.setGame(game);
                    stage.setName(stageName);
                    stage.setSortOrder(stageSortOrder.addAndGet(10));
                    return stage;
                })
                .collect(toList()));
        game.setPlatforms(stream(gameForm
                .getPlatforms())
                .map(platformName -> {
                    Platform platform = new Platform();
                    platform.setGame(game);
                    platform.setName(platformName);
                    return platform;
                }).collect(toList()));
        return save(game);
    }

    public Game save(Game game) {
        return this.gameRepository.save(game);
    }

    public List<Game> findByPlayer(Player player) {
        return this.gameRepository.findByPlayer(player);
    }

    public List<Game> getUnplayedGames(Player player) {
        return this.gameRepository.findByPlayerNot(player.getId());
    }

}
