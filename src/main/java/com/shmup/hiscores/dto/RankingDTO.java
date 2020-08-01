package com.shmup.hiscores.dto;

import com.shmup.hiscores.models.Difficulty;
import com.shmup.hiscores.models.Mode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class RankingDTO {
    private final Mode mode;
    private final Difficulty difficulty;
    private final List<ScoreDTO> scores;

}
