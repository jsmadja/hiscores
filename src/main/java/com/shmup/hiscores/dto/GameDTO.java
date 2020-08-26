package com.shmup.hiscores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GameDTO {
    private final String cover;
    private final Long id;
    private final String title;
}
