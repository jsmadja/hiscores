package com.shmup.hiscores.controllers;

import com.shmup.hiscores.services.PlayerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Deprecated
@AllArgsConstructor
@RestController
public class PlayersController {

    private final PlayerService playerService;

    @Data
    @AllArgsConstructor
    private static class PlayerDto {
        private Long id;
        private String name;
    }

    @RequestMapping("/players")
    public List<PlayerDto> findAll() {
        return this.playerService
                .findAll()
                .stream()
                .map(player -> new PlayerDto(player.getId(), player.getName()))
                .collect(toList());
    }

}
