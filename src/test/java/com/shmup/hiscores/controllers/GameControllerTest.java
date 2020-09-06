package com.shmup.hiscores.controllers;

import com.shmup.hiscores.dto.GameSetting;
import com.shmup.hiscores.dto.RankingDTO;
import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Ranking;
import com.shmup.hiscores.services.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.CREATED;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @InjectMocks
    private GameController gameController;

    @Mock
    private GameService gameService;

    @Test
    public void should_create_mode() {
        Game game = Game.builder().modes(new ArrayList<>()).build();
        Player player = mock(Player.class);
        when(player.isAdministrator()).thenReturn(true);
        GameSetting gameSetting = new GameSetting("Arcade", 1L);

        ResponseEntity<Game> response = gameController.createMode(player, game, gameSetting);

        verify(gameService).save(game);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isEqualTo(game);
    }

    @Test
    public void should_create_difficulty() {
        Game game = Game.builder().difficulties(new ArrayList<>()).build();
        Player player = mock(Player.class);
        when(player.isAdministrator()).thenReturn(true);
        GameSetting gameSetting = new GameSetting("Easy", 1L);

        ResponseEntity<Game> response = gameController.createDifficulty(player, game, gameSetting);

        verify(gameService).save(game);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isEqualTo(game);
    }

    @Test
    public void should_create_stage() {
        Game game = Game.builder().stages(new ArrayList<>()).build();
        Player player = mock(Player.class);
        when(player.isAdministrator()).thenReturn(true);
        GameSetting gameSetting = new GameSetting("2-ALL", 1L);

        ResponseEntity<Game> response = gameController.createStage(player, game, gameSetting);

        verify(gameService).save(game);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isEqualTo(game);
    }

    @Test
    public void should_create_ship() {
        Game game = Game.builder().ships(new ArrayList<>()).build();
        Player player = mock(Player.class);
        when(player.isAdministrator()).thenReturn(true);
        GameSetting gameSetting = new GameSetting("Type A", 1L);

        ResponseEntity<Game> response = gameController.createShip(player, game, gameSetting);

        verify(gameService).save(game);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isEqualTo(game);
    }

    @Test
    public void should_create_platform() {
        Game game = Game.builder().platforms(new ArrayList<>()).build();
        Player player = mock(Player.class);
        when(player.isAdministrator()).thenReturn(true);

        ResponseEntity<Game> response = gameController.createPlatform(player, game, new String[]{"PS4"});

        verify(gameService).save(game);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isEqualTo(game);
    }

    @Test
    public void should_get_game_from_db() {
        Game dbGame = mock(Game.class);
        when(gameService.findById(1L)).thenReturn(dbGame);

        Game game = gameController.getById(1L);

        assertThat(game).isEqualTo(dbGame);
    }

    @Test
    public void should_get_rankings() {
        Game game = Game.builder().build();
        List<Ranking> rankings = new ArrayList<>();
        when(gameService.getRankingsOf(game)).thenReturn(rankings);
        List<RankingDTO> rankingsDTO = gameController.getRankingsById(game);
        assertThat(rankingsDTO).isEmpty();
    }
}