package com.shmup.hiscores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Deprecated
@AllArgsConstructor
@Data
public class PlayerDTO {
    private final Long id;
    private final String name;
}
