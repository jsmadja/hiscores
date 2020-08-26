package com.shmup.hiscores.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Test
    public void should_find_mode_by_id() {
        Mode mode = new Mode();
        mode.setId(1L);
        Game game = Game.builder().modes(new ArrayList<>()).build();
        game.add(mode);
        assertThat(game.getModeById(1L).get()).isEqualTo(mode);
    }

    @Test
    public void should_not_find_mode_by_id() {
        Mode mode = new Mode();
        mode.setId(1L);
        Game game = Game.builder().modes(new ArrayList<>()).build();
        game.add(mode);
        assertThat(game.getModeById(2L).isEmpty()).isTrue();
    }

    @Test
    public void should_add_platforms() {
        List<Platform> platforms = new ArrayList<>();
        platforms.add(Platform.builder().name("PS2").build());
        Game game = Game.builder().platforms(platforms).build();
        game.addNewPlatforms(new String[]{"PS4", "PS1"});
        assertThat(game.getPlatforms()).extracting("name").contains("PS1", "PS2", "PS4");
    }
}