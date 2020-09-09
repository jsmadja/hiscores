package com.shmup.hiscores.services;

import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import com.shmup.hiscores.repositories.ScoreRepository;
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
class ScoreServiceTest {

    @InjectMocks
    private ScoreService scoreService;

    @Mock
    private ScoreRepository scoreRepository;


    @Test
    public void should_return_games_by_platform() {
        Game game = mock(Game.class);
        Player player = mock(Player.class);

        List<Score> dbScores = new ArrayList<>();
        when(scoreRepository.findByPlayerAndRankIsNotNullAndGameOrderByCreatedAtDesc(player, game)).thenReturn(dbScores);

        List<Score> scores = scoreService.getPlayerLastScoresOfGame(player, game);

        assertThat(scores).isEqualTo(dbScores);
    }

}