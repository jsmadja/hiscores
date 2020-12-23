package com.shmup.hiscores.models;

import com.shmup.hiscores.formatters.ScoreFormatter;
import lombok.*;
import org.ocpsoft.prettytime.PrettyTime;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

@Deprecated
@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Score extends BaseModel<Score> implements Comparable<Score> {

    @ManyToOne
    private Game game;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Stage stage;

    @ManyToOne
    private Mode mode;

    @ManyToOne
    private Difficulty difficulty;

    @ManyToOne
    private Ship ship;

    @ManyToOne
    private Platform platform;

    @Lob
    private String comment;

    private String photo;

    private String inp;

    private String replay;

    private BigDecimal value;

    private boolean onecc;

    private Integer progression;

    private Integer rank;

    public boolean isVip() {
        return player.isVip();
    }

    @Transient
    private Long gapWithPreviousScore;

    public Score(Game game, Player player, Stage stage, Ship ship, Mode mode, Difficulty difficulty, String comment, Platform platform, BigDecimal value, String replay, String photo) {
        this.game = game;
        this.player = player;
        this.stage = stage;
        this.mode = mode;
        this.ship = ship;
        this.difficulty = difficulty;
        this.comment = comment;
        this.platform = platform;
        this.value = value;
        this.replay = replay;
        this.photo = photo;
        this.onecc = this.is1CC();
    }

    public Score(Date createdAt, Date updatedAt, Long id, Game game, Player player, Stage stage, Ship ship, Mode mode, Difficulty difficulty, String comment, Platform platform, BigDecimal value, String replay, String photo, Integer rank) {
        this(game, player, stage, ship, mode, difficulty, comment, platform, value, replay, photo);
        this.rank = rank;
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Score(Game game, Player player, Stage stage, Ship ship, Mode mode, Difficulty difficulty, String comment, Platform platform, BigDecimal value) {
        this.game = game;
        this.player = player;
        this.stage = stage;
        this.ship = ship;
        this.mode = mode;
        this.difficulty = difficulty;
        this.comment = comment;
        this.platform = platform;
        this.value = value;
    }

    public boolean is1CC() {
        if (this.stage != null) {
            String stageName = this.stage.getName().toLowerCase();
            return stageName.contains("all")
                    || stageName.startsWith("2-") || stageName.toLowerCase().startsWith("boss 2-")
                    || stageName.startsWith("3-") || stageName.toLowerCase().startsWith("boss 3-")
                    || stageName.startsWith("4-") || stageName.toLowerCase().startsWith("boss 4-")
                    || stageName.startsWith("5-") || stageName.toLowerCase().startsWith("boss 5-")
                    || stageName.startsWith("6-") || stageName.toLowerCase().startsWith("boss 6-")
                    || stageName.toUpperCase().contains("ENDLESS");
        }
        return false;
    }

    public boolean hasMode(Mode mode) {
        if (mode == null && this.mode == null) {
            return true;
        }
        if (mode == null) {
            return false;
        }
        if (this.mode == null) {
            return false;
        }
        return this.mode.equals(mode);
    }

    public boolean hasDifficulty(Difficulty difficulty) {
        if (difficulty == null && this.difficulty == null) {
            return true;
        }
        if (difficulty == null) {
            return false;
        }
        if (this.difficulty == null) {
            return false;
        }
        return this.difficulty.equals(difficulty);
    }

    public String difficultyName() {
        return difficulty == null ? "" : difficulty.getName();
    }

    public String modeName() {
        return mode == null ? "" : mode.getName();
    }

    public String shipName() {
        return ship == null ? "" : ship.getName();
    }

    boolean isWorstThan(Score score) {
        if (isComparableWith(score)) {
            if (isNumericScore(score)) {
                return score.compareTo(this) < 0;
            }
            return score.compareTo(this) > 0;
        }
        return false;
    }

    @Override
    public int compareTo(Score score) {
        int i = score.value.compareTo(this.value);
        if (i == 0) {
            if (score.stage != null && this.stage != null) {
                return score.stage.id.compareTo(this.stage.id);
            }
        }
        return i;
    }

    private boolean isNumericScore(Score score) {
        return score.mode == null || !score.mode.isTimerScore();
    }

    private boolean isComparableWith(Score score) {
        return hasDifficulty(score.difficulty) && hasMode(score.mode) && hasGame(score.game);
    }

    private boolean hasGame(Game game) {
        return this.game.equals(game);
    }

    public boolean isTimeScore() {
        return this.mode != null && this.mode.isTimerScore();
    }

    public String formattedValue() {
        if (mode != null && mode.isTimerScore()) {
            return ScoreFormatter.formatAsTime(value);
        }
        return ScoreFormatter.format(value);
    }

    public String formattedRank() {
        Integer value = rank;
        if (value == null) {
            value = 0;
        }
        int hundredRemainder = value % 100;
        int tenRemainder = value % 10;
        if (hundredRemainder - tenRemainder == 10) {
            return value + "th";
        }
        switch (tenRemainder) {
            case 1:
                return value + "st";
            case 2:
                return value + "nd";
            case 3:
                return value + "rd";
            default:
                return value + "th";
        }
    }

    public String formattedDateInFrench() {
        return getCreatedSinceInFrench();
    }

    public String getCreatedSinceInFrench() {
        if (createdAt == null) {
            return "";
        }
        return new PrettyTime(Locale.FRENCH).format(createdAt);
    }

    public String getGameTitle() {
        String title = game.getTitle();
        if (mode != null) {
            title += " " + mode.getName();
        }
        if (difficulty != null) {
            title += " " + difficulty.getName();
        }
        return title;
    }

    public String getStageName() {
        return this.stage == null ? "" : this.stage.getName();
    }

    public boolean isPlayedBy(Player player) {
        return this.player.id.equals(player.id);
    }
}
