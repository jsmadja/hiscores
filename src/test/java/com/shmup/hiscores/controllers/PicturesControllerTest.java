package com.shmup.hiscores.controllers;

import com.shmup.hiscores.drawer.Images;
import com.shmup.hiscores.drawer.RankingPicture;
import com.shmup.hiscores.drawer.SignaturePicture;
import com.shmup.hiscores.drawer.VersusPicture;
import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Ranking;
import com.shmup.hiscores.models.Versus;
import com.shmup.hiscores.services.CacheService;
import com.shmup.hiscores.services.GameService;
import com.shmup.hiscores.services.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PicturesControllerTest {

    @InjectMocks
    private PicturesController platformsController;

    @Mock
    private CacheService cacheService;

    @Mock
    private GameService gameService;

    @Mock
    private PlayerService playerService;

    @Mock
    private Images images;

    @Mock
    private VersusPicture versusPicture;

    @Mock
    private SignaturePicture signaturePicture;

    @Mock
    private RankingPicture rankingPicture;

    @Test
    public void should_get_medals_picture_without_cache() throws IOException {
        byte[] picture = new byte[0];
        when(cacheService.getMedalsPictureOf(1L)).thenReturn(Optional.empty());
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);

        Player player = mock(Player.class);
        when(playerService.findByShmupUserId(1L)).thenReturn(player);
        when(playerService.createMedalsFor(player)).thenReturn(picture);

        platformsController.medals(1L, response);

        verify(cacheService).setMedalsPictureOf(1L, picture);
        verify(response).setContentType("image/png");
        verify(response).setStatus(200);
        verify(outputStream).write(picture);
    }

    @Test
    public void should_get_medals_picture_from_cache() throws IOException {
        byte[] picture = new byte[0];
        when(cacheService.getMedalsPictureOf(1L)).thenReturn(Optional.of(picture));
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);

        platformsController.medals(1L, response);

        verify(response).setContentType("image/png");
        verify(response).setStatus(200);
        verify(outputStream).write(picture);
    }

    @Test
    public void should_get_ranking_picture_without_cache() throws IOException {
        byte[] picture = new byte[0];
        Game game = mock(Game.class);
        List<Ranking> rankings = new ArrayList<>();
        when(gameService.getRankingsOf(game)).thenReturn(rankings);
        when(cacheService.getRankingPictureOf(game)).thenReturn(Optional.empty());
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);
        BufferedImage image = mock(BufferedImage.class);
        when(rankingPicture.createRankingPicture(game, rankings)).thenReturn(image);
        when(images.toBytesC(image)).thenReturn(picture);

        platformsController.getRankingPicture(game, response);

        verify(cacheService).setRankingPictureOf(game, picture);
        verify(response).setContentType("image/png");
        verify(response).setStatus(200);
        verify(outputStream).write(picture);
    }

    @Test
    public void should_get_ranking_picture_from_cache() throws IOException {
        byte[] picture = new byte[0];
        Game game = mock(Game.class);
        when(cacheService.getRankingPictureOf(game)).thenReturn(Optional.of(picture));
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);

        platformsController.getRankingPicture(game, response);

        verify(response).setContentType("image/png");
        verify(response).setStatus(200);
        verify(outputStream).write(picture);
    }

    @Test
    public void should_get_signature_picture_without_cache() throws IOException {
        byte[] picture = new byte[0];
        Player player = mock(Player.class);
        when(cacheService.getSignaturePictureOf(player)).thenReturn(Optional.empty());
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);
        BufferedImage image = mock(BufferedImage.class);
        when(signaturePicture.createSignaturePicture(player)).thenReturn(image);
        when(images.toBytesC(image)).thenReturn(picture);

        platformsController.getSignature(player, response);

        verify(cacheService).setSignaturePictureOf(player, picture);
        verify(response).setContentType("image/png");
        verify(response).setStatus(200);
        verify(outputStream).write(picture);
    }

    @Test
    public void should_get_signature_picture_from_cache() throws IOException {
        byte[] picture = new byte[0];
        Player player = mock(Player.class);
        when(cacheService.getSignaturePictureOf(player)).thenReturn(Optional.of(picture));
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);

        platformsController.getSignature(player, response);

        verify(response).setContentType("image/png");
        verify(response).setStatus(200);
        verify(outputStream).write(picture);
    }

    @Test
    public void should_get_versus_picture_without_cache() throws IOException {
        byte[] picture = new byte[0];
        Player player = mock(Player.class);
        when(cacheService.getVersusPictureOf(player)).thenReturn(Optional.empty());
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);

        Versus versus = mock(Versus.class);
        when(playerService.getBestVersusFor(player)).thenReturn(versus);

        BufferedImage bufferedImage = mock(BufferedImage.class);
        when(this.versusPicture.createVersusPicture(player, versus)).thenReturn(bufferedImage);
        when(images.toBytesC(any())).thenReturn(picture);

        platformsController.getVersusPicture(player, response);

        verify(cacheService).setVersusPictureOf(player, picture);
        verify(response).setContentType("image/png");
        verify(response).setStatus(200);
        verify(outputStream).write(picture);
    }

    @Test
    public void should_get_versus_picture_from_cache() throws IOException {
        byte[] picture = new byte[0];
        Player player = mock(Player.class);
        when(cacheService.getVersusPictureOf(player)).thenReturn(Optional.of(picture));
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);

        platformsController.getVersusPicture(player, response);

        verify(response).setContentType("image/png");
        verify(response).setStatus(200);
        verify(outputStream).write(picture);
    }

}