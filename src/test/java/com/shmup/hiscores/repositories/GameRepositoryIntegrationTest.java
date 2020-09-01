package com.shmup.hiscores.repositories;

import com.shmup.hiscores.ContainerDatabaseTest;
import com.shmup.hiscores.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GameRepositoryIntegrationTest extends ContainerDatabaseTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ModeRepository modeRepository;

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

    @Transactional
    @Test
    public void should_add_mode_to_game() {
        Game game = Game.builder()
                .modes(new ArrayList<>())
                .build();
        Game savedGame = gameRepository.save(game);

        Mode mode = Mode.builder().game(savedGame).sortOrder(1L).name("Arcade").build();
        savedGame.add(mode);

        Game updatedGame = gameRepository.save(savedGame);
        assertThat(updatedGame.getModes()).hasSize(1);
        assertThat(updatedGame.getModes().get(0)).extracting("name", "sortOrder", "scoreType").contains("Arcade", 1L, null);
    }

    @Test
    public void should_add_difficulty_to_game() {
        Game game = Game.builder()
                .difficulties(new ArrayList<>())
                .build();
        Game savedGame = gameRepository.save(game);

        Difficulty difficulty = Difficulty.builder().game(savedGame).sortOrder(1L).name("Easy").build();
        savedGame.add(difficulty);

        Game updatedGame = gameRepository.save(savedGame);
        assertThat(updatedGame.getDifficulties()).hasSize(1);
        assertThat(updatedGame.getDifficulties().get(0)).extracting("name", "sortOrder").contains("Easy", 1L);
    }

    @Transactional
    @Test
    public void should_add_stage_to_game() {
        Game game = Game.builder()
                .stages(new ArrayList<>())
                .build();
        Game savedGame = gameRepository.save(game);

        Stage stage = Stage.builder().game(savedGame).sortOrder(1L).name("1").build();
        savedGame.add(stage);

        Game updatedGame = gameRepository.save(savedGame);
        assertThat(updatedGame.getStages()).hasSize(1);
        assertThat(updatedGame.getStages().get(0)).extracting("name", "sortOrder").contains("1", 1L);
    }

    @Transactional
    @Test
    public void should_add_ship_to_game() {
        Game game = Game.builder()
                .ships(new ArrayList<>())
                .build();
        Game savedGame = gameRepository.save(game);

        Ship ship = Ship.builder().game(savedGame).sortOrder(1L).name("Type A").build();
        savedGame.add(ship);

        Game updatedGame = gameRepository.save(savedGame);
        assertThat(updatedGame.getShips()).hasSize(1);
        assertThat(updatedGame.getShips().get(0)).extracting("name", "sortOrder").contains("Type A", 1L);
    }

    @Transactional
    @Test
    public void should_add_platform_to_game() {
        Game game = Game.builder()
                .platforms(new ArrayList<>())
                .build();
        Game savedGame = gameRepository.save(game);

        Platform platform = Platform.builder().game(savedGame).name("NG").build();
        savedGame.add(platform);

        Game updatedGame = gameRepository.save(savedGame);
        assertThat(updatedGame.getPlatforms()).hasSize(1);
        assertThat(updatedGame.getPlatforms().get(0).getName()).isEqualTo("NG");
    }

}