package com.shmup.hiscores.repositories;

import com.shmup.hiscores.ContainerDatabaseTest;
import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ScoreRepositoryIntegrationTest extends ContainerDatabaseTest {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void should_get_all_scores_by_players_and_games() {
        Player player = playerRepository.findByName("Mickey");
        Game game = gameRepository.findById(1L).get();
        List<Score> scores = scoreRepository.findByPlayerAndRankIsNotNullAndGameOrderByCreatedAtDesc(player, game);
        assertThat(scores).isNotEmpty();
    }

    @Test
    public void should_not_change_created_date_when_updating_a_score() {
        Score score = scoreRepository.findById(1L).get();
        Date createdAt = new Date(score.getCreatedAt().getTime());
        Date updatedAt = new Date(score.getUpdatedAt().getTime());
        int newRank = score.getRank() + 1;
        score.setRank(newRank);
        Score savedScore = scoreRepository.save(score);
        assertThat(savedScore.getCreatedAt().getTime()).isEqualTo(createdAt.getTime());
        assertThat(savedScore.getUpdatedAt().getTime()).isGreaterThan(updatedAt.getTime());
        assertThat(savedScore.getRank()).isEqualTo(newRank);
    }

}