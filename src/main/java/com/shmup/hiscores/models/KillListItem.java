package com.shmup.hiscores.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

@Data
@AllArgsConstructor
public class KillListItem {
    private final Score score;
    private final Optional<Score> previousScore;

    public BigDecimal getRatio() {
        if (previousScore.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return score.getValue().divide(previousScore.get().getValue(), MathContext.DECIMAL128).subtract(BigDecimal.ONE);
    }
}
