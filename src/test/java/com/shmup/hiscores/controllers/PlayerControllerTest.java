package com.shmup.hiscores.controllers;

import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import com.shmup.hiscores.services.ScoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @InjectMocks
    private PlayerController playerController;

    @Mock
    private ScoreService scoreService;

    @Test
    public void should_get_last_scores_of_game() {
        List<Score> dbScores = new ArrayList<>();
        Player player = mock(Player.class);
        Game game = mock(Game.class);

        when(scoreService.getPlayerLastScoresOfGame(player, game)).thenReturn(dbScores);

        List<Score> scores = playerController.getPlayerLastScoresOfGame(player, game);

        assertThat(scores).isEqualTo(dbScores);
    }

}