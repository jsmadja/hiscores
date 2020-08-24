package com.shmup.hiscores.controllers;

import com.shmup.hiscores.dto.TimelineItem;
import com.shmup.hiscores.services.GameService;
import com.shmup.hiscores.services.PlayerService;
import com.shmup.hiscores.services.ScoreService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Deprecated
@AllArgsConstructor
@RestController
public class AppController {

    private ScoreService scoreService;
    private GameService gameService;
    private PlayerService playerService;

    @RequestMapping("/")
    public String getHome() {
        return "OK";
    }

    @RequestMapping("/ui/timeline")
    public List<TimelineItem> getTimeline() {
        return scoreService
                .getLastScores()
                .stream()
                .map(TimelineItem::new)
                .collect(toList());
    }

    @RequestMapping("/counts")
    public Counts getCounts() {
        return new Counts(gameService.getGamesCount(), playerService.getPlayersCount(), scoreService.getScoresCount());
    }

    @Data
    private static class Counts {
        private final long gamesCount;
        private final long playersCount;
        private final long scoresCount;
    }
}
