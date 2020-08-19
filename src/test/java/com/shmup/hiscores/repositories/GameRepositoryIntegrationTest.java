package com.shmup.hiscores.repositories;

import com.shmup.hiscores.ContainerDatabaseTest;
import com.shmup.hiscores.models.Game;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GameRepositoryIntegrationTest extends ContainerDatabaseTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void should_load_all_games() {
        List<Game> games = gameRepository.findByOrderByTitleAsc();
        Assertions.assertThat(games).hasSize(583);
    }

}