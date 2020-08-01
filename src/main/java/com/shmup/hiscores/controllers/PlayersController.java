package com.shmup.hiscores.controllers;

import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import com.shmup.hiscores.services.PlayerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class PlayersController {

    private PlayerService playerService;

    @Data
    @AllArgsConstructor
    private static class PlayerDto {
        private Long id;
        private String name;
        private int firstRankCount;
        private int secondRankCount;
        private int thirdRankCount;
        private int oneCreditCount;
        private int scores;
    }

    @RequestMapping("/players")
    public List<PlayerDto> findAll() {
        return this.playerService
                .findAll()
                .stream()
                .filter(player -> !player.getScores().isEmpty())
                .map(this::toPlayerDto)
                .sorted(Comparator.comparingInt(o -> -o.firstRankCount))
                .collect(Collectors.toList());
    }

    private PlayerDto toPlayerDto(Player player) {
        int oneCreditCount = 0;
        int firstRankCount = 0;
        int secondRankCount = 0;
        int thirdRankCount = 0;
        List<Score> scores = player.getScores();
        for (Score score : scores) {
            if (score.isOnecc()) {
                oneCreditCount++;
            }
            Integer rank = score.getRank();
            if (rank == 1) {
                firstRankCount++;
            } else if (score.getRank() == 2) {
                secondRankCount++;
            } else if (score.getRank() == 3) {
                thirdRankCount++;
            }
        }
        return new PlayerDto(player.getId(), player.getName(), firstRankCount, secondRankCount, thirdRankCount, oneCreditCount, player.getScores().size());
    }

}
