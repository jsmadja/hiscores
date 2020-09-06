package com.shmup.hiscores.dto;

import com.shmup.hiscores.models.Score;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Deprecated
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

    public static ScoreDTO toScoreDTO(Score score) {
        return new ScoreDTO(
                score.getRank(),
                score.getValue(),
                new PlayerDTO(
                        score.getPlayer().getId(),
                        score.getPlayer().getName()
                ),
                score.is1CC(),
                score.getPhoto(),
                score.getInp(),
                score.getReplay(),
                score.getStageName(),
                score.getId(),
                score.getComment()
        );
    }

}
