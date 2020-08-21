package com.shmup.hiscores.controllers;

import com.shmup.hiscores.dto.PlatformWithGameCount;
import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.services.PlatformService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class PlatformsController {

    private PlatformService platformService;

    @RequestMapping("/platforms")
    public List<PlatformWithGameCount> findAllPlatforms() {
        return this.platformService.findPlatformsWithGameCount();
    }

    @RequestMapping("/platforms/{platformTitle}/games")
    public List<Game> findGamesByPlatform(@PathVariable("platformTitle") String platformTitle) {
        return platformService.findGamesByPlatform(platformTitle);
    }
}
