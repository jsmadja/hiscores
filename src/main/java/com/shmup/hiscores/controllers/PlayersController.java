package com.shmup.hiscores.controllers;

import com.shmup.hiscores.dto.GameForm;
import com.shmup.hiscores.dto.PlayerDTO;
import com.shmup.hiscores.dto.PlayerForm;
import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import com.shmup.hiscores.models.Versus;
import com.shmup.hiscores.services.PlayerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

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

    @PostMapping("/players")
    @ApiOperation(value = "create a new player")
    public ResponseEntity<PlayerDTO> createPlayer(@RequestAttribute(value = "player") Player player, @ApiParam(value = "The player to create", required = true) @Valid @RequestBody PlayerForm playerForm) {
        if (player.isAdministrator()) {
            Player createdPlayer = playerService.findOrCreatePlayer(playerForm.getName(), playerForm.getShmupUserId());
            return new ResponseEntity<>(new PlayerDTO(createdPlayer.getId(), createdPlayer.getName()), CREATED);
        }
        return new ResponseEntity<>(UNAUTHORIZED);
    }
}
