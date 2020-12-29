package com.shmup.hiscores.controllers;

import com.shmup.hiscores.models.*;
import com.shmup.hiscores.services.PlatformService;
import com.shmup.hiscores.services.PlayerService;
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

    @Mock
    private PlayerService playerService;

    @Test
    public void should_get_last_scores_of_game() {
        List<Score> dbScores = new ArrayList<>();
        Player player = mock(Player.class);
        Game game = mock(Game.class);

        when(scoreService.getPlayerLastScoresOfGame(player, game)).thenReturn(dbScores);

        List<Score> scores = playerController.getPlayerLastScoresOfGame(player, game);

        assertThat(scores).isEqualTo(dbScores);
    }

    @Test
    public void should_return_player_when_calling_me() {
        Player player = mock(Player.class);
        assertThat(playerController.me(player)).isEqualTo(player);
    }

    @Test
    public void should_get_player_recommendations() {
        Player player = mock(Player.class);
        Recommendations recommendations = mock(Recommendations.class);

        when(playerService.getRecommendationsFor(player)).thenReturn(recommendations);

        Recommendations actualRecommendations = playerController.getRecommendations(player);

        assertThat(actualRecommendations).isEqualTo(recommendations);
    }

    @Test
    public void should_get_player_kill_list() {
        Player player = mock(Player.class);
        List<KillListItem> kilList = new ArrayList<>();

        when(playerService.getKillListFor(player)).thenReturn(kilList);

        List<KillListItem> actualKillList = playerController.getKillList(player);

        assertThat(actualKillList).isEqualTo(kilList);
    }

}