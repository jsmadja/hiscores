package com.shmup.hiscores.controllers;

import com.shmup.hiscores.ContainerDatabaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerIntegrationTest extends ContainerDatabaseTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void get_last_player_scores_of_game() throws Exception {
        this.mvc.perform(get("/me/games/1/scores")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void get_me() throws Exception {
        this.mvc.perform(get("/me")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                            "id":1,
                            "createdAt":"2013-11-29T19:13:32.000+00:00",
                            "name":"anzymus",
                            "shmupUserId":33489,
                            "hideMedals":false,
                            "vip":true,
                            "administrator":true,
                            "superAdministrator":true,
                            "authenticated":true
                        }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    void get_recommendations() throws Exception {
        this.mvc.perform(get("/me/recommendations")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON))
                .andExpect(content().json("""
                        {}
                        """))
                .andExpect(status().isOk());
    }

    @Test
    void get_killList() throws Exception {
        this.mvc.perform(get("/me/kill-list")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
