package com.shmup.hiscores.services;

import com.shmup.hiscores.dto.PlatformWithGameCount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlatformServiceTest {

    @InjectMocks
    private PlatformService platformService;

    @Mock
    private PlatformRepository platformRepository;

    @Test
    public void should_return_games_with_counts() {
        PlatformWithGameCount platformWithCount = mock(PlatformWithGameCount.class);
        when(platformRepository.findPlatformsWithGameCount()).thenReturn(List.of(platformWithCount));

        List<PlatformWithGameCount> platformsWithGameCount = platformService.findPlatformsWithGameCount();

        assertThat(platformsWithGameCount).hasSize(1);
    }

}