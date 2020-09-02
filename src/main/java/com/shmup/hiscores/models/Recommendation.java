package com.shmup.hiscores.models;

import lombok.Getter;

@Getter
public class Recommendation {
    private final Game game;
    private Mode mode;
    private Difficulty difficulty;

    public Recommendation(Mode mode) {
        this.mode = mode;
        this.game = mode.getGame();
    }

    public Recommendation(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.game = difficulty.getGame();
    }

    public Recommendation(Game game) {
        this.game = game;
    }
}
