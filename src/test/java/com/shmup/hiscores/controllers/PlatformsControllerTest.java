package com.shmup.hiscores.controllers;

import com.shmup.hiscores.dto.PlatformWithGameCount;
import com.shmup.hiscores.services.PlatformService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlatformsControllerTest {

    @InjectMocks
    private PlatformsController platformsController;

    @Mock
    private PlatformService platformService;

    @Test
    public void should_get_platforms() {
        PlatformWithGameCount platformWithGameCount = Mockito.mock(PlatformWithGameCount.class);
        when(platformService.findPlatformsWithGameCount()).thenReturn(List.of(platformWithGameCount));

        List<PlatformWithGameCount> allPlatforms = platformsController.findAllPlatforms();

        assertThat(allPlatforms).hasSize(1);
        assertThat(allPlatforms.get(0)).isEqualTo(platformWithGameCount);
    }

}