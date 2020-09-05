package com.shmup.hiscores.controllers;

import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.services.CacheService;
import com.shmup.hiscores.services.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Deprecated
@AllArgsConstructor
@RestController
public class MedalsController {

    private final PlayerService playerService;

    private final CacheService cacheService;

    @GetMapping("/player/{shmupUserId}/medals.png")
    public void medals(@PathVariable Long shmupUserId, HttpServletResponse response) throws IOException {
        Optional<byte[]> medalsPicture = cacheService.getMedalsPictureOf(shmupUserId);
        if (medalsPicture.isEmpty()) {
            Player player = playerService.findByShmupUserId(shmupUserId);
            byte[] bytes = playerService.createMedalsFor(player);
            cacheService.setMedalsPictureOf(shmupUserId, bytes);
            medalsPicture = Optional.of(bytes);
        }
        response.setContentType("image/png");
        response.setStatus(200);
        response.getOutputStream().write(medalsPicture.get());
    }

}
