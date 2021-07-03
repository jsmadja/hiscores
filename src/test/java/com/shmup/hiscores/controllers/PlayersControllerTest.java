package com.shmup.hiscores.controllers;

import com.shmup.hiscores.dto.GameSetting;
import com.shmup.hiscores.dto.PlayerDTO;
import com.shmup.hiscores.dto.PlayerForm;
import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import com.shmup.hiscores.models.Versus;
import com.shmup.hiscores.services.PlayerService;
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
class PlayersControllerTest {

    @InjectMocks
    private PlayersController playersController;

    @Mock
    private PlayerService playerService;

    @Test
    public void should_get_all_players() {
        List<Player> players = List.of(new Player(1L, "player1"), new Player(2L, "player2"));
        when(playerService.findAll()).thenReturn(players);

        List<PlayerDTO> playersDto = playersController.findAll();

        assertThat(playersDto).isEqualTo(List.of(new PlayerDTO(1L, "player1"), new PlayerDTO(2L, "player2")));
    }

    @Test
    public void should_get_player_scores() {
        Player player = mock(Player.class);
        when(player.getScores()).thenReturn(new ArrayList<>());

        List<Score> scores = playersController.getPlayerScores(player);

        assertThat(scores).isEqualTo(player.getScores());
    }

    @Test
    public void should_get_shmup_player_scores() {
        Player player = mock(Player.class);
        when(player.getScores()).thenReturn(new ArrayList<>());
        when(playerService.findByShmupUserId(1L)).thenReturn(player);

        List<Score> scores = playersController.getShmupPlayerScores(1L);

        assertThat(scores).isEqualTo(player.getScores());
    }

    @Test
    public void should_get_versus() {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Versus versus = mock(Versus.class);
        when(player1.getComparisonWith(player2)).thenReturn(versus);

        Versus actualVersus = playersController.getVersus(player1, player2);

        assertThat(actualVersus).isEqualTo(versus);
    }

    @Test
    public void should_create_player() {
        PlayerForm playerForm = new PlayerForm("name", 1L);
        Player player = mock(Player.class);
        when(player.isAdministrator()).thenReturn(true);
        when(playerService.findOrCreatePlayer("name", 1L)).thenReturn(player);

        ResponseEntity<PlayerDTO> response = playersController.createPlayer(player, playerForm);

        verify(playerService).findOrCreatePlayer("name", 1L);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
    }

}