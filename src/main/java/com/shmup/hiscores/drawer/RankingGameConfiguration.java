package com.shmup.hiscores.drawer;

import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Score;
import static java.lang.Math.max;

import java.awt.*;
import java.util.List;

public class RankingGameConfiguration {

    static final int fontHeight = 12;
    static final int width = 652;
    static final Color COLOR_SHMUP_TITLE = new Color(46, 13, 34);
    public static final Color COLOR_SHMUP_GREY = new Color(240, 243, 244);
    static final Color COLOR_SHMUP_TEXT = new Color(102, 102, 102);
    static final Color COLOR_SCORE_TEXT = new Color(25, 83, 136);

    int maxStageLength;
    int maxPlatformLength;

    public RankingGameConfiguration(Game game) {
        List<Score> scores = game.getScores();
        for (Score score : scores) {
            if (score.getStage() != null) {
                maxStageLength = max(score.getStage().getName().length(), maxStageLength);
            }
            if (score.getPlatform() != null) {
                maxPlatformLength = max(score.getPlatform().getName().length(), maxPlatformLength);
            }
        }
    }
}
