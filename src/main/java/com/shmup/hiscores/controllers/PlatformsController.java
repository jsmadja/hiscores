package com.shmup.hiscores.controllers;

import com.shmup.hiscores.dto.PlatformWithGameCount;
import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.services.PlatformService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlatformsController {

    private PlatformService platformService;

    @GetMapping("/platforms")
    @ApiOperation(value = "Get all platforms")
    public List<PlatformWithGameCount> findAllPlatforms() {
        return this.platformService.findPlatformsWithGameCount();
    }

    @GetMapping("/platforms/{platformTitle}/games")
    public List<Game> findGamesByPlatform(@PathVariable("platformTitle") String platformTitle) {
        return platformService.findGamesByPlatform(platformTitle);
    }
}
