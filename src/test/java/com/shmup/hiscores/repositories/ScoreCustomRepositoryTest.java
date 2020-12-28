package com.shmup.hiscores.repositories;

import com.shmup.hiscores.ContainerDatabaseTest;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ScoreCustomRepositoryTest extends ContainerDatabaseTest {

    @Autowired
    private ScoreCustomRepository scoreCustomRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ScoreRepository scoreRepository;

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
        Score secondRankedScore = scoreRepository.findById(4L).get();
        Score firstRankedScore = scoreRepository.findById(2L).get();
        Score previousScore = scoreCustomRepository.getPreviousScore(secondRankedScore).get();
        assertThat(previousScore.getId()).isEqualTo(firstRankedScore.getId());
    }

}