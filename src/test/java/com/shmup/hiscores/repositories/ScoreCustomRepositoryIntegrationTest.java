package com.shmup.hiscores.repositories;

import com.shmup.hiscores.ContainerDatabaseTest;
import com.shmup.hiscores.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ScoreCustomRepositoryIntegrationTest extends ContainerDatabaseTest {

    public static final LocalDate MIN_START_DATE = LocalDate.EPOCH;
    private static final LocalDate MAX_END_DATE = LocalDate.now();

    @Autowired
    private ScoreCustomRepository scoreCustomRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private DifficultyRepository difficultyRepository;

    @Autowired
    private ModeRepository modeRepository;

    @Test
    public void should_get_last_scores() {
        List<Score> lastScores = scoreCustomRepository.getLastScores();
        assertThat(lastScores).isNotEmpty();
    }

    @Test
    public void should_get_last_scores_of_a_player() {
        Player player = playerRepository.findById(2L).get();
        List<Score> scores = scoreCustomRepository.getLastScoresOf(player);
        assertThat(scores).isNotEmpty();
    }

    @Transactional
    @Test
    public void should_get_best_score_of_a_player() {
        Score score = scoreRepository.findById(1L).get();
        Score bestScore = scoreCustomRepository.getBestScoreFor(score.getPlayer(), score.getGame(), score.getMode(), score.getDifficulty()).get();
        assertThat(bestScore).isEqualTo(score);
    }

    @Test
    public void should_get_previous_score() {
        Score firstRankedScore = scoreRepository.findById(3L).get();
        Score secondRankedScore = scoreRepository.findById(4L).get();
        Score previousScore = scoreCustomRepository.getPreviousScore(secondRankedScore).get();
        assertThat(previousScore.getId()).isEqualTo(firstRankedScore.getId());
    }

    @Test
    public void should_get_scores_between_to_date() {
        Game game = gameRepository.findById(1L).get();
        List<Score> scores = scoreCustomRepository.findBestScores(game, MIN_START_DATE, MAX_END_DATE);
        assertThat(scores).isNotEmpty();
    }

    @Test
    public void should_get_scores_between_to_date_with_difficulty_and_modes() {
        Game game = gameRepository.findById(1L).get();
        Difficulty difficulty = difficultyRepository.findById(1L).get();
        Mode mode = modeRepository.findById(1L).get();
        List<Score> scores = scoreCustomRepository.findBestScores(game, difficulty, mode, MIN_START_DATE, MAX_END_DATE);
        assertThat(scores).isNotEmpty();
    }

    @Test
    public void should_not_get_scores_between_to_date_in_the_future() {
        Game game = gameRepository.findById(1L).get();
        List<Score> scores = scoreCustomRepository.findBestScores(game, MAX_END_DATE, MAX_END_DATE);
        assertThat(scores).isEmpty();
    }

    @Test
    public void should_not_get_scores_between_to_date_in_the_future_with_difficulty_and_modes() {
        Game game = gameRepository.findById(1L).get();
        Difficulty difficulty = difficultyRepository.findById(1L).get();
        Mode mode = modeRepository.findById(1L).get();
        List<Score> scores = scoreCustomRepository.findBestScores(game, difficulty, mode, MAX_END_DATE, MAX_END_DATE);
        assertThat(scores).isEmpty();
    }

}