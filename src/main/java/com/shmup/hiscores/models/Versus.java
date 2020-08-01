package com.shmup.hiscores.models;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;

public class Versus {

    public Player player1;
    public Player player2;

    public List<Comparison> comparisons = new ArrayList<Comparison>();

    public Versus(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void add(Comparison comparison) {
        this.comparisons.add(comparison);
    }

    public long loseCount() {
        return comparisons.stream().filter(Comparison::isLostByPlayer1).count();
    }

    public long winCount() {
        return comparisons.stream().filter(Comparison::isWonByPlayer1).count();
    }

    public static class Comparison {
        public final Game game;
        public final Difficulty difficulty;
        public final Mode mode;
        public final Score score1;
        public final Score score2;

        public Comparison(Game game, Difficulty difficulty, Mode mode, Score score1, Score score2) {
            this.game = game;
            this.difficulty = difficulty;
            this.mode = mode;
            this.score1 = score1;
            this.score2 = score2;
        }

        public int scoreGap() {
            Score scoreA, scoreB;
            if (score1.isTimeScore()) {
                scoreA = score2;
                scoreB = score1;
                DateTime dateTimeA = new DateTime(scoreA.getValue().longValue());
                DateTime dateTimeB = new DateTime(scoreB.getValue().longValue());
                double a = dateTimeA.getMinuteOfDay() * 60000 + dateTimeA.getSecondOfMinute() * 1000 + dateTimeA.getMillisOfSecond();
                double b = dateTimeB.getMinuteOfDay() * 60000 + dateTimeB.getSecondOfMinute() * 1000 + dateTimeB.getMillisOfSecond();
                return (int) ((a * 100D) / b) - 100;
            } else {
                scoreA = score1;
                scoreB = score2;
            }
            return scoreA.getValue().multiply(BigDecimal.valueOf(100)).divide(scoreB.getValue(), HALF_UP).intValue() - 100;
        }

        public boolean isLostByPlayer1() {
            return score1.isWorstThan(score2);
        }

        public boolean isWonByPlayer1() {
            return score2.isWorstThan(score1);
        }
    }
}
