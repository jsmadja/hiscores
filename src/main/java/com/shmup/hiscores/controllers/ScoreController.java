package com.shmup.hiscores.controllers;

import com.shmup.hiscores.models.Score;
import com.shmup.hiscores.services.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class ScoreController {

    private final ScoreService scoreService;

    @RequestMapping("/scores")
    public List<Score> findScores() {
        return scoreService.getLastScores();
    }

}
