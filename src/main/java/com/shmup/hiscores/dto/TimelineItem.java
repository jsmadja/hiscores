package com.shmup.hiscores.dto;

import com.shmup.hiscores.models.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TimelineItem {

    private Score score;

    public Game getGame() {
        return score.getGame();
    }

    public Difficulty getDifficulty() {
        return score.getDifficulty();
    }

    public Platform getPlatform() { return score.getPlatform(); }

    public Stage getStage() { return score.getStage(); }

    public Mode getMode() {
        return score.getMode();
    }

    public Ship getShip() {
        return score.getShip();
    }

    public Player getPlayer() {
        return score.getPlayer();
    }
}
