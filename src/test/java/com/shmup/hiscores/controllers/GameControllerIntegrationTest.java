package com.shmup.hiscores.controllers;

import com.shmup.hiscores.ContainerDatabaseTest;
import com.shmup.hiscores.dto.GameForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.Cookie;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerIntegrationTest extends ContainerDatabaseTest {

    @Autowired
    private MockMvc mvc;

    @Value("${api.shmup-cookie-name}")
    private String shmupCookieName;

    @Value("${api.shmup-cookie-userid}")
    private String shmupUserId;

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
        Cookie cookie = new Cookie(shmupCookieName, shmupUserId);
        this.mvc.perform(post("/games")
                .cookie(cookie)
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(game)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", isA(Integer.class)))
                .andExpect(content().contentType(APPLICATION_JSON));
    }

}