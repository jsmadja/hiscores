package com.shmup.hiscores.repositories;

import com.shmup.hiscores.ContainerDatabaseTest;
import com.shmup.hiscores.dto.PlatformWithGameCount;
import com.shmup.hiscores.services.PlatformRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PlatformRepositoryIntegrationTest extends ContainerDatabaseTest {

    @Autowired
    private PlatformRepository platformRepository;

    @Test
    public void should_find_all_platforms_with_game_count() {
        List<PlatformWithGameCount> platformsWithGameCount = platformRepository.findPlatformsWithGameCount();
        assertThat(platformsWithGameCount).hasSize(3);
        assertThat(platformsWithGameCount.get(0).getGames()).isEqualTo(1);
        assertThat(platformsWithGameCount.get(0).getTitle()).isEqualTo("NG");
    }

}