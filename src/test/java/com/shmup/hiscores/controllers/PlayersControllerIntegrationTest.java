package com.shmup.hiscores.controllers;

import com.shmup.hiscores.ContainerDatabaseTest;
import com.shmup.hiscores.dto.PlayerForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayersControllerIntegrationTest extends ContainerDatabaseTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void get_all_players() throws Exception {
        String jsonContent = """
                [
                  {id:1,name:'anzymus'},
                  {id:2,name:'Mickey'},
                  {id:3,name:'Joe'}
                ]
                """;
        this.mvc.perform(get("/players")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));
    }

    @Test
    void get_versus() throws Exception {
        this.mvc.perform(get("/players/1/versus/2")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void get_player_scores() throws Exception {
        this.mvc.perform(get("/players/1/scores")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void get_shmup_player_scores() throws Exception {
        this.mvc.perform(get("/shmup-players/33489/scores")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createPlayer() throws Exception {
        this.mvc.perform(post("/players")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new PlayerForm("Mickey", 312L))))
                .andExpect(status().isCreated());
    }


}
