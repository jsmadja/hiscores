package com.shmup.hiscores.controllers;

import com.shmup.hiscores.dto.GameDTO;
import com.shmup.hiscores.dto.GameForm;
import com.shmup.hiscores.dto.GameSetting;
import com.shmup.hiscores.dto.RankingDTO;
import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.services.GameService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@AllArgsConstructor
@RestController
public class GameController {

    private final GameService gameService;

    @Deprecated
    @GetMapping("/games")
    public List<GameDTO> all() {
        return gameService
                .findAll()
                .stream()
                .map(this::toGameDTO)
                .collect(toList());
    }

    private GameDTO toGameDTO(Game game) {
        return new GameDTO(
                game.getCover(),
                game.getId(),
                game.getTitle());
    }

    @PostMapping("/games")
    @ApiOperation(value = "create a new game")
    public ResponseEntity<Game> createGame(@RequestAttribute(value = "player") Player player, @ApiParam(value = "The game to create", required = true) @Valid @RequestBody GameForm gameForm) {
        if (player.isAdministrator()) {
            return new ResponseEntity<>(gameService.createGame(gameForm), CREATED);
        }
        return new ResponseEntity<>(UNAUTHORIZED);
    }

    @PostMapping("/games/{id}/modes")
    @ApiOperation(value = "create a new mode")
    public ResponseEntity<Game> createMode(
            @ApiIgnore @RequestAttribute(value = "player") Player player,
            @ApiParam(value = "Game id", required = true) @PathVariable("id") Game game,
            @ApiParam(value = "The mode to create", required = true) @Valid @RequestBody GameSetting gameSetting) {
        if (!player.isAdministrator()) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        game.addNewMode(gameSetting);
        gameService.save(game);
        return new ResponseEntity<>(game, CREATED);
    }

    @PostMapping("/games/{id}/difficulties")
    @ApiOperation(value = "create a new difficulty")
    public ResponseEntity<Game> createDifficulty(
            @ApiIgnore @RequestAttribute(value = "player") Player player,
            @PathVariable("id") Game game,
            @ApiParam(value = "The difficulty to create", required = true) @Valid @RequestBody GameSetting gameSetting) {
        if (!player.isAdministrator()) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        game.addNewDifficulty(gameSetting);
        gameService.save(game);
        return new ResponseEntity<>(game, CREATED);
    }

    @PostMapping("/games/{id}/ships")
    @ApiOperation(value = "create a new ship")
    public ResponseEntity<Game> createShip(
            @ApiIgnore @RequestAttribute(value = "player") Player player,
            @PathVariable("id") Game game,
            @ApiParam(value = "The ship to create", required = true) @Valid @RequestBody GameSetting gameSetting) {
        if (!player.isAdministrator()) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        game.addNewShip(gameSetting);
        gameService.save(game);
        return new ResponseEntity<>(game, CREATED);
    }

    @PostMapping("/games/{id}/stages")
    @ApiOperation(value = "create a new stage")
    public ResponseEntity<Game> createStage(
            @ApiIgnore @RequestAttribute(value = "player") Player player,
            @PathVariable("id") Game game,
            @ApiParam(value = "The stage to create", required = true) @Valid @RequestBody GameSetting gameSetting) {
        if (!player.isAdministrator()) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        game.addNewStage(gameSetting);
        gameService.save(game);
        return new ResponseEntity<>(game, CREATED);
    }

    @PostMapping("/games/{id}/platforms")
    @ApiOperation(value = "create a new platform")
    public ResponseEntity<Game> createPlatform(
            @ApiIgnore @RequestAttribute(value = "player") Player player,
            @PathVariable("id") Game game,
            @ApiParam(value = "The platforms to create", required = true) @RequestBody String[] platforms) {
        if (!player.isAdministrator()) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        game.addNewPlatforms(platforms);
        gameService.save(game);
        return new ResponseEntity<>(game, CREATED);
    }

    @GetMapping("/games/{id}")
    public Game getById(@PathVariable("id") Long id) {
        return gameService.findById(id);
    }

    @GetMapping("/games/{id}/rankings")
    public List<RankingDTO> getRankingsById(@PathVariable("id") Game game) {
        return gameService
                .getRankingsOf(game)
                .stream()
                .map(RankingDTO::toRankingDTO)
                .collect(toList());
    }
}
