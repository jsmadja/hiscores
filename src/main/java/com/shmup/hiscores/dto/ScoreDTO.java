package com.shmup.hiscores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ScoreDTO {
    private final int rank;
    private final BigDecimal value;
    private final PlayerDTO player;
    private final boolean isOneCC;
    private final String photo;
    private final String inp;
    private final String replay;
    private final String stage;
    private final Long id;
    private final String comment;

}
