package com.shmup.hiscores.controllers;

import com.shmup.hiscores.drawer.Images;
import com.shmup.hiscores.drawer.RankingPicture;
import com.shmup.hiscores.drawer.SignaturePicture;
import com.shmup.hiscores.drawer.VersusPicture;
import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.services.CacheService;
import com.shmup.hiscores.services.GameService;
import com.shmup.hiscores.services.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class PicturesController {

    private final GameService gameService;

    private final CacheService cacheService;

    private final PlayerService playerService;

    private final Images images;

    private final VersusPicture versusPicture;

    private final SignaturePicture signaturePicture;

    private final RankingPicture rankingPicture;

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

    // for forum.shmup.com backward compatibility
    @GetMapping("/game/{id}/ranking.png:refresh")
    public void refreshRankingPicture(@PathVariable("id") Game game, HttpServletResponse response) throws IOException {
        cacheService.removeRankingPictureOf(game);
        getRankingPicture(game, response);
    }

    // for forum.shmup.com backward compatibility
    @GetMapping("/game/{id}/ranking.png")
    public void getRankingPicture(@PathVariable("id") Game game, HttpServletResponse response) throws IOException {
        Optional<byte[]> rankingPicture = cacheService.getRankingPictureOf(game);
        if (rankingPicture.isEmpty()) {
            BufferedImage image = this.rankingPicture.createRankingPicture(game, gameService.getRankingsOf(game));
            byte[] bytes = images.toBytesC(image);
            cacheService.setRankingPictureOf(game, bytes);
            rankingPicture = Optional.of(bytes);
        }
        response.setContentType("image/png");
        response.setStatus(200);
        response.getOutputStream().write(rankingPicture.get());
    }

    @GetMapping("/game/{id}/event-ranking.png")
    public void getEventRankingPicture(
            @PathVariable("id") Game game,
            @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            HttpServletResponse response) throws IOException {
        BufferedImage image = this.rankingPicture.createRankingPicture(game, gameService.getEventRankingsOf(game, startDate, endDate));
        response.setContentType("image/png");
        response.setStatus(200);
        response.getOutputStream().write(images.toBytesC(image));
    }

    @GetMapping("/player/{player}/signature.png")
    public void getSignature(@PathVariable("player") Player player, HttpServletResponse response) throws IOException {
        Optional<byte[]> signaturePicture = cacheService.getSignaturePictureOf(player);
        if (signaturePicture.isEmpty()) {
            BufferedImage image = this.signaturePicture.createSignaturePicture(player);
            byte[] bytes = images.toBytesC(image);
            cacheService.setSignaturePictureOf(player, bytes);
            signaturePicture = Optional.of(bytes);
        }
        response.setContentType("image/png");
        response.setStatus(200);
        response.getOutputStream().write(signaturePicture.get());
    }

    @GetMapping("/player/{player}/versus.png")
    public void getVersusPicture(@PathVariable("player") Player player, HttpServletResponse response) throws IOException {
        Optional<byte[]> versusPicture = cacheService.getVersusPictureOf(player);
        if (versusPicture.isEmpty()) {
            BufferedImage image = this.versusPicture.createVersusPicture(player, playerService.getBestVersusFor(player));
            byte[] bytes = images.toBytesC(image);
            cacheService.setVersusPictureOf(player, bytes);
            versusPicture = Optional.of(bytes);
        }
        response.setContentType("image/png");
        response.setStatus(200);
        response.getOutputStream().write(versusPicture.get());
    }
}
