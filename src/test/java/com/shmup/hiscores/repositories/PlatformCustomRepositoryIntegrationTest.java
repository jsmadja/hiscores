package com.shmup.hiscores.repositories;

import com.shmup.hiscores.ContainerDatabaseTest;
import com.shmup.hiscores.dto.PlatformWithGameCount;
import com.shmup.hiscores.models.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PlatformCustomRepositoryIntegrationTest extends ContainerDatabaseTest {

    @Autowired
    private PlatformCustomRepository platformRepository;

    @Test
    public void should_find_all_games_by_platform_title() {
        List<Game> games = platformRepository.findGamesByPlatform("NG");
        assertThat(games).hasSize(1);
        assertThat(games.get(0).getTitle()).isEqualTo("Strikers 1945 PLUS");
    }

}