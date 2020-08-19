package com.shmup.hiscores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Deprecated
@AllArgsConstructor
@Data
public class GameDTO {
    private final String cover;
    private final Long id;
    private final String title;
    private final List<String> platforms;
    private final int players;
    private final int scores;
    private final int oneccs;
}
