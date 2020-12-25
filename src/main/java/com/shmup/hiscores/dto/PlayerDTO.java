package com.shmup.hiscores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PlayerDTO {
    private final Long id;
    private final String name;
}
