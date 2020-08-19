package com.shmup.hiscores.controllers;

import com.shmup.hiscores.dto.PlatformDTO;
import com.shmup.hiscores.services.PlatformService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class PlatformsController {

    private PlatformService platformService;

    @RequestMapping("/platforms")
    public List<String> findAll() {
        return this.platformService.findAll();
    }

    @RequestMapping("/ui/platforms")
    public List<PlatformDTO> findAllPlatforms() {
        return this.platformService.findPlatformsWithGameCount();
    }

}
