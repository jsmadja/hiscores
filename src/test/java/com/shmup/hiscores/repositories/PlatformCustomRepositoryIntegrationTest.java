package com.shmup.hiscores.repositories;

import com.shmup.hiscores.ContainerDatabaseTest;
import com.shmup.hiscores.models.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PlatformCustomRepositoryIntegrationTest extends ContainerDatabaseTest {

    @Autowired
    private PlatformCustomRepository platformRepository;

    @Autowired
    private PlatformRepository platformRepository2;

    @Transactional
    @Test
    public void should_find_all_games_by_platform_title() {
        platformRepository2.findAll().forEach(x -> {
            System.err.println(x + " " + x.getGame().getTitle());
        });
        List<Game> games = platformRepository.findGamesByPlatform("NG");
        assertThat(games).isNotEmpty();
        assertThat(games.get(0).getTitle()).isNotBlank();
    }

}