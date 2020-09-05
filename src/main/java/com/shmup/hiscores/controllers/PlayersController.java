package com.shmup.hiscores.controllers;

import com.shmup.hiscores.drawer.Images;
import com.shmup.hiscores.drawer.SignaturePicture;
import com.shmup.hiscores.drawer.VersusPicture;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import com.shmup.hiscores.models.Versus;
import com.shmup.hiscores.services.PlayerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Deprecated
@AllArgsConstructor
@RestController
public class PlayersController {

    private final PlayerService playerService;

    @Data
    @AllArgsConstructor
    private static class PlayerDto {
        private Long id;
        private String name;
    }

    @RequestMapping("/players")
    public List<PlayerDto> findAll() {
        return this.playerService
                .findAll()
                .stream()
                .map(player -> new PlayerDto(player.getId(), player.getName()))
                .collect(toList());
    }

    @RequestMapping("/players/{player1}/versus/{player2}")
    public Versus getVersus(@PathVariable("player1") Player player1, @PathVariable("player2") Player player2) {
        return player1.getComparisonWith(player2);
    }

    @RequestMapping("/players/{player}/scores")
    public List<Score> getVersus(@PathVariable("player") Player player) {
        return player.getScores();
    }

    @RequestMapping("/player/{player}/signature.png")
    public void getSignature(@PathVariable("player") Player player, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        BufferedImage image = SignaturePicture.createSignaturePicture(player);
        byte[] bytes = Images.toBytes(image);
        response.setStatus(200);
        response.getOutputStream().write(bytes);
    }

    @RequestMapping("/player/{player}/versus.png")
    public void getVersusPicture(@PathVariable("player") Player player, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        BufferedImage image = VersusPicture.createVersusPicture(player, playerService.getBestVersusFor(player));
        byte[] bytes = Images.toBytes(image);
        response.setStatus(200);
        response.getOutputStream().write(bytes);
    }

}
