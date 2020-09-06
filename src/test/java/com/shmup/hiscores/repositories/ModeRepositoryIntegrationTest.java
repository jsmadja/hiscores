package com.shmup.hiscores.repositories;

import com.shmup.hiscores.ContainerDatabaseTest;
import com.shmup.hiscores.models.Mode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ModeRepositoryIntegrationTest extends ContainerDatabaseTest {

    @Autowired
    private ModeRepository modeRepository;

    @Test
    public void should_find_unplayed_modes_of_played_games() {
        List<Mode> modes = modeRepository.findUnplayedModesByPlayer(1L);
        assertThat(modes).isNotEmpty();
    }

}