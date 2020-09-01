package com.shmup.hiscores.controllers;

import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.services.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Deprecated
@AllArgsConstructor
@RestController
public class MedalsController {

    private final PlayerService playerService;

    @GetMapping("/player/{shmupUserId}/medals.png")
    public void medals(@PathVariable Long shmupUserId, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        Player player = playerService.findByShmupUserId(shmupUserId);
        byte[] bytes = playerService.createMedalsFor(player);
        response.setStatus(200);
        response.getOutputStream().write(bytes);
    }

}
