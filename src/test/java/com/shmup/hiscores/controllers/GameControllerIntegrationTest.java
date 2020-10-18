package com.shmup.hiscores.controllers;

import com.shmup.hiscores.ContainerDatabaseTest;
import com.shmup.hiscores.dto.GameForm;
import com.shmup.hiscores.dto.GameSetting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.Cookie;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerIntegrationTest extends ContainerDatabaseTest {

    private static final long BLACK_LABEL_MODE_ID = 2L;
    private static final long ORIGINAL_DIFFICULTY_ID = 4L;

    @Autowired
    private MockMvc mvc;

    @Test
    void createGame_validate_mandatory_fields() throws Exception {
        GameForm game = GameForm.builder().build();
        this.mvc.perform(post("/games")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(game)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", is("title is mandatory")))
                .andExpect(jsonPath("$.cover", is("cover is mandatory")))
                .andExpect(jsonPath("$.thread", is("thread is mandatory")))
                .andExpect(jsonPath("$.platforms", is("platforms is mandatory")))
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Test
    void createGame_only_available_for_administrators() throws Exception {
        GameForm game = GameForm.builder()
                .title("Espgaluda II")
                .cover("https://hiscores.shmup.com/covers/25.jpg")
                .thread("http://forum.shmup.com/viewtopic.php?f=20&t=15093")
                .difficulties(new String[]{"Easy"})
                .modes(new String[]{"Arcade"})
                .platforms(new String[]{"NG"})
                .ships(new String[]{"Type A"})
                .stages(new String[]{"1"})
                .build();
        this.mvc.perform(post("/games")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(game)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createGame_valid_form() throws Exception {
        GameForm game = GameForm.builder()
                .title("Espgaluda II")
                .cover("https://hiscores.shmup.com/covers/25.jpg")
                .thread("http://forum.shmup.com/viewtopic.php?f=20&t=15093")
                .difficulties(new String[]{"Easy"})
                .modes(new String[]{"Arcade"})
                .platforms(new String[]{"NG"})
                .ships(new String[]{"Type A"})
                .stages(new String[]{"1"})
                .build();
        Cookie cookie = createShmupCookie();
        this.mvc.perform(post("/games")
                .cookie(cookie)
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(game)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", isA(Integer.class)))
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Test
    void addMode() throws Exception {
        this.mvc.perform(post("/games/1/modes")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(GameSetting.builder().value("Arcade").build())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.modes[*].name", contains("Black Label", "White Label", "Arcade")));
    }

    @Test
    void addMode_after_another_mode() throws Exception {
        this.mvc.perform(post("/games/1/modes")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(GameSetting.builder().value("Arcade X").afterValue(BLACK_LABEL_MODE_ID).build())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.modes[*].name", hasItem("Arcade X")));
    }

    @Test
    void addDifficulty_after_another_difficulty() throws Exception {
        this.mvc.perform(post("/games/1/difficulties")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(GameSetting.builder().value("Easy").afterValue(ORIGINAL_DIFFICULTY_ID).build())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.difficulties[*].name", contains("Original", "Easy")))
                .andExpect(jsonPath("$.difficulties[*].sortOrder", contains(1, 11)));
    }

    @Test
    void addShip() throws Exception {
        this.mvc.perform(post("/games/1/ships")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(GameSetting.builder().value("Type A").build())))
                .andExpect(status().isCreated());
    }

    @Test
    void addStage() throws Exception {
        this.mvc.perform(post("/games/1/stages")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(GameSetting.builder().value("2-ALL").build())))
                .andExpect(status().isCreated());
    }

    @Test
    void addPlatforms() throws Exception {
        this.mvc.perform(post("/games/1/platforms")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new String[]{"PS4", "PS1"})))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.platforms[*].name", contains("NG", "PCB", "PS1", "PS4", "X360")));
    }

    @Test
    void getGameById() throws Exception {
        this.mvc.perform(get("/games/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Strikers 1945 PLUS")));
    }

    @Test
    void getRankings() throws Exception {
        this.mvc.perform(get("/games/1/rankings")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
