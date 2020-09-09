package com.shmup.hiscores.repositories;

import com.shmup.hiscores.ContainerDatabaseTest;
import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
        Player player = playerRepository.findByName("anzymus");
        Game game = gameRepository.findById(1L).get();
        List<Score> scores = scoreRepository.findByPlayerAndRankIsNotNullAndGameOrderByCreatedAtDesc(player, game);
        Assertions.assertThat(scores).isNotEmpty();
    }

}