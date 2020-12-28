package com.shmup.hiscores.controllers;

import com.shmup.hiscores.ContainerDatabaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.IMAGE_PNG;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PicturesControllerIntegrationTest extends ContainerDatabaseTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void get_medals_picture() throws Exception {
        this.mvc.perform(get("/player/1/medals.png"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(IMAGE_PNG));
    }

    @Test
    void get_signature_picture() throws Exception {
        this.mvc.perform(get("/player/1/signature.png"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(IMAGE_PNG));
    }

    @Test
    void get_versus_picture() throws Exception {
        this.mvc.perform(get("/player/1/versus.png"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(IMAGE_PNG));
    }

    @Test
    void get_ranking_picture() throws Exception {
        this.mvc.perform(get("/game/1/ranking.png"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(IMAGE_PNG));
    }

}
