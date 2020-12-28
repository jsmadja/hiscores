package com.shmup.hiscores.drawer;

import com.shmup.hiscores.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.shmup.hiscores.drawer.Images.compareImages;
import static org.assertj.core.api.Assertions.assertThat;

@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
class RankingPictureTest {

    private static final boolean SAVE_SNAPSHOTS = false;

    private final RankingPicture rankingPicture = new RankingPicture();

    @Test
    public void should_create_a_ranking_picture_with_one_ranking_with_mode_and_difficulty() throws IOException {
        Player player = createPlayer();
        Game game = createGame();
        List<Score> scores = createScores(player, game, game.getModes().get(0), game.getDifficulties().get(0));
        game.setScores(scores);

        Ranking ranking = new Ranking(scores, game.getDifficulties().get(0), game.getModes().get(0));
        List<Ranking> rankings = List.of(ranking);
        BufferedImage actual = rankingPicture.createRankingPicture(game, rankings);
        File expected = new File("src/test/resources/one_ranking_with_mode_and_difficulty.png");
        if (SAVE_SNAPSHOTS) {
            Images.saveSnasphot(actual, expected);
        }
        assertThat(compareImages(actual, ImageIO.read(expected))).isTrue();
    }

    @Test
    public void should_create_a_ranking_picture_with_one_ranking_with_only_mode() throws IOException {
        Player player = createPlayer();
        Game game = createGame();
        List<Score> scores = createScores(player, game, game.getModes().get(0), null);
        game.setScores(scores);

        Ranking ranking = new Ranking(scores, game.getModes().get(0));
        List<Ranking> rankings = List.of(ranking);
        BufferedImage actual = rankingPicture.createRankingPicture(game, rankings);
        File expected = new File("src/test/resources/one_ranking_with_only_mode.png");
        if (SAVE_SNAPSHOTS) {
            Images.saveSnasphot(actual, expected);
        }
        assertThat(compareImages(actual, ImageIO.read(expected))).isTrue();
    }

    @Test
    public void should_create_a_ranking_picture_with_one_ranking_with_only_difficulty() throws IOException {
        Player player = createPlayer();
        Game game = createGame();
        List<Score> scores = createScores(player, game, null, game.getDifficulties().get(0));
        game.setScores(scores);

        Ranking ranking = new Ranking(scores, game.getDifficulties().get(0));
        List<Ranking> rankings = List.of(ranking);
        BufferedImage actual = rankingPicture.createRankingPicture(game, rankings);
        File expected = new File("src/test/resources/one_ranking_with_only_difficulty.png");
        if (SAVE_SNAPSHOTS) {
            Images.saveSnasphot(actual, expected);
        }
        assertThat(compareImages(actual, ImageIO.read(expected))).isTrue();
    }

    @Test
    public void should_create_a_ranking_picture_with_one_general_ranking() throws IOException {
        Player player = createPlayer();
        Game game = createGame();
        List<Score> scores = createScores(player, game, null, null);
        game.setScores(scores);

        Ranking ranking = new Ranking(scores);
        ranking.setGeneral(true);
        List<Ranking> rankings = List.of(ranking);
        BufferedImage actual = rankingPicture.createRankingPicture(game, rankings);
        File expected = new File("src/test/resources/one_ranking_with_one_general_ranking.png");
        if (SAVE_SNAPSHOTS) {
            Images.saveSnasphot(actual, expected);
        }
        assertThat(compareImages(actual, ImageIO.read(expected))).isTrue();
    }

    private List<Score> createScores(Player player, Game game, Mode mode, Difficulty difficulty) {
        Score score1 = new Score(
                game,
                player,
                game.getStages().get(0),
                game.getShips().get(0),
                mode,
                difficulty,
                "a comment",
                game.getPlatforms().get(0),
                new BigDecimal(123),
                "http://replay.com/1",
                "http://photo.com/1"
        );
        score1.setRank(1);

        Score score2 = new Score(
                game,
                player,
                game.getStages().get(0),
                game.getShips().get(0),
                mode,
                difficulty,
                "a comment",
                game.getPlatforms().get(0),
                new BigDecimal(122),
                "http://replay.com/1",
                "http://photo.com/1"
        );
        score2.setRank(2);

        return List.of(score1, score2);
    }

    private Game createGame() {
        Game game = Game.builder()
                .title("Gradius")
                .build();

        Difficulty difficulty = new Difficulty("Easy", 1L, game);
        game.setDifficulties(List.of(difficulty));

        Mode mode = new Mode("Arcade", 1L, game, "");
        game.setModes(List.of(mode));

        Stage stage = new Stage("Stage1", 1L, game);
        game.setStages(List.of(stage));

        Ship ship = new Ship("ShipA", 1L, game);
        game.setShips(List.of(ship));

        Platform platform = new Platform("PCB", game);
        game.setPlatforms(List.of(platform));
        return game;
    }

    private Player createPlayer() {
        Player player = new Player("Player1");
        player.setVip(true);
        return player;
    }

}