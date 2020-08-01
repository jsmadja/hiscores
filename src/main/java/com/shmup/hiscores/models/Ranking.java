package com.shmup.hiscores.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class Ranking {

    private Difficulty difficulty;

    private Mode mode;

    private List<Score> scores;

    private String difficultyName;

    private String modeName;

    private boolean general;

    public Ranking(Collection<Score> scores) {
        this.scores = new ArrayList<>();
        int rank = 1;
        for (Score score : scores) {
            if (score.isVip()) {
                if (score.getRank() == null) {
                    score.setRank(rank);
                }
                this.scores.add(score);
                rank++;
            }
        }
    }

    public Ranking(Collection<Score> scores, Difficulty difficulty) {
        this(scores);
        this.difficulty = difficulty;
        this.difficultyName = difficulty.getName();
    }

    public Ranking(Collection<Score> scores, Mode mode) {
        this(scores);
        this.mode = mode;
        this.modeName = mode.getName();
    }

    public Ranking(Collection<Score> scores, Difficulty difficulty, Mode mode) {
        this(scores);
        this.difficulty = difficulty;
        this.difficultyName = difficulty.getName();
        this.mode = mode;
        this.modeName = mode.getName();
    }

}
