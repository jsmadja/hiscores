package com.shmup.hiscores.services;

import com.shmup.hiscores.dto.GameForm;
import com.shmup.hiscores.models.*;
import com.shmup.hiscores.repositories.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private GameRepository gameRepository;

    @Test
    public void should_create_game() {
        GameForm gameForm = GameForm.builder()
                .title("Espgaluda II")
                .cover("https://hiscores.shmup.com/covers/25.jpg")
                .thread("http://forum.shmup.com/viewtopic.php?f=20&t=15093")
                .difficulties(new String[]{"Easy"})
                .modes(new String[]{"Arcade"})
                .platforms(new String[]{"NG"})
                .ships(new String[]{"Type A"})
                .stages(new String[]{"1"})
                .build();

        Game game = mock(Game.class);

        when(gameRepository.save(Mockito.any())).thenReturn(game);

        gameService.createGame(gameForm);

        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);

        verify(gameRepository).save(gameArgumentCaptor.capture());
        Game savedGame = gameArgumentCaptor.getValue();
        assertThat(savedGame.getTitle()).isEqualTo("Espgaluda II");
        assertThat(savedGame.getCover()).isEqualTo("https://hiscores.shmup.com/covers/25.jpg");
        assertThat(savedGame.getThread()).isEqualTo("http://forum.shmup.com/viewtopic.php?f=20&t=15093");
        assertThat(savedGame.getDifficulties()).hasSize(1).containsExactly(Difficulty.builder().game(savedGame).name("Easy").sortOrder(10L).build());
        assertThat(savedGame.getModes()).hasSize(1).containsExactly(Mode.builder().game(savedGame).name("Arcade").sortOrder(10L).build());
        assertThat(savedGame.getShips()).hasSize(1).containsExactly(Ship.builder().game(savedGame).name("Type A").sortOrder(10L).build());
        assertThat(savedGame.getStages()).hasSize(1).containsExactly(Stage.builder().game(savedGame).name("1").sortOrder(10L).build());
        assertThat(savedGame.getPlatforms()).hasSize(1).containsExactly(Platform.builder().game(savedGame).name("NG").build());
    }

}