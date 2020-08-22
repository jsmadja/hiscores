package com.shmup.hiscores.repositories;

import com.shmup.hiscores.ContainerDatabaseTest;
import com.shmup.hiscores.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
public class GameRepositoryIntegrationTest extends ContainerDatabaseTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void should_load_all_games() {
        List<Game> games = gameRepository.findByOrderByTitleAsc();
        assertThat(games).extracting("title").contains("Strikers 1945 PLUS");
    }

    @Test
    public void should_persist_game() {
        Game game = Game.builder()
                .title("Espgaluda II")
                .cover("https://hiscores.shmup.com/covers/25.jpg")
                .thread("http://forum.shmup.com/viewtopic.php?f=20&t=15093")
                .build();
        game.setDifficulties(List.of(Difficulty.builder().name("Easy").sortOrder(1L).game(game).build()));
        game.setModes(List.of(Mode.builder().name("Arcade").sortOrder(1L).game(game).build()));
        game.setPlatforms(List.of(Platform.builder().name("NG").game(game).build()));
        game.setShips(List.of(Ship.builder().name("Type A").sortOrder(1L).game(game).build()));
        game.setStages(List.of(Stage.builder().name("1").sortOrder(1L).game(game).build()));

        Game savedGame = gameRepository.save(game);
        assertThat(savedGame.getId()).isNotNull();

        assertThat(savedGame.getPlatforms()).hasSize(1);
        assertThat(savedGame.getPlatforms().get(0).getId()).isNotNull();
        assertThat(savedGame.getPlatforms().get(0).getName()).isEqualTo("NG");

        assertThat(savedGame.getStages()).hasSize(1);
        assertThat(savedGame.getStages().get(0)).extracting("name", "sortOrder").contains("1", 1L);

        assertThat(savedGame.getShips()).hasSize(1);
        assertThat(savedGame.getShips().get(0)).extracting("name", "sortOrder").contains("Type A", 1L);

        assertThat(savedGame.getModes()).hasSize(1);
        assertThat(savedGame.getModes().get(0)).extracting("name", "sortOrder", "scoreType").contains("Arcade", 1L, null);

        assertThat(savedGame.getDifficulties()).hasSize(1);
        assertThat(savedGame.getDifficulties().get(0)).extracting("name", "sortOrder").contains("Easy", 1L);
    }

}