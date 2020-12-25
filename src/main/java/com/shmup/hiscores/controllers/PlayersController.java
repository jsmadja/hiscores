package com.shmup.hiscores.controllers;

import com.shmup.hiscores.dto.PlayerDTO;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import com.shmup.hiscores.models.Versus;
import com.shmup.hiscores.services.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@RestController
public class PlayersController {

    private final PlayerService playerService;

    @GetMapping("/players")
    public List<PlayerDTO> findAll() {
        return this.playerService
                .findAll()
                .stream()
                .map(player -> new PlayerDTO(player.getId(), player.getName()))
                .collect(toList());
    }

    @GetMapping("/players/{player1}/versus/{player2}")
    public Versus getVersus(@PathVariable("player1") Player player1, @PathVariable("player2") Player player2) {
        return player1.getComparisonWith(player2);
    }

    @GetMapping("/players/{player}/scores")
    public List<Score> getPlayerScores(@PathVariable("player") Player player) {
        return player.getScores();
    }

}
