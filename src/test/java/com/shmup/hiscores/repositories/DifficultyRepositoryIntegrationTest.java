package com.shmup.hiscores.repositories;

import com.shmup.hiscores.ContainerDatabaseTest;
import com.shmup.hiscores.models.Difficulty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DifficultyRepositoryIntegrationTest extends ContainerDatabaseTest {

    @Autowired
    private DifficultyRepository difficultyRepository;

    @Test
    public void should_find_unplayed_difficulties_of_played_games() {
        List<Difficulty> difficulties = difficultyRepository.findUnplayedDifficultiesByPlayer(1L);
        assertThat(difficulties).isNotEmpty();
    }

}