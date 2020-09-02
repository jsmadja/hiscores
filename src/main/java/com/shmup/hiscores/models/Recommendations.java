package com.shmup.hiscores.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Recommendations {
    private final Recommendation unplayedMode;
    private final Recommendation unplayedDifficulty;
    private final Recommendation unplayedGame;
    private final Recommendation oldestScoredGame;
    private final Recommendation latestScoredGame;
    private final Recommendation nearestScoredGame;
    private final Recommendation farestScoredGame;
}
