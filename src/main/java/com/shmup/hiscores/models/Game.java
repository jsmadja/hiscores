package com.shmup.hiscores.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.map.MultiKeyMap;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Deprecated
@EqualsAndHashCode(callSuper = true)
@Table(name = "game")
@Entity
@Data
public class Game extends BaseModel<Game> {

    private String thread;

    private String cover;

    private String title;

    @JsonIgnore
    private boolean generalRanking;

    @JsonIgnore
    @OneToMany(mappedBy = "game")
    @Where(clause = "rank > 0")
    private List<Score> scores;

    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<Score> allScores;

    @JsonIgnore
    @OneToMany(mappedBy = "game")
    @Fetch(value = FetchMode.SUBSELECT)
    @Where(clause = "onecc = true")
    private List<Score> oneccs;

    @OrderBy("name")
    @OneToMany(mappedBy = "game", cascade = PERSIST)
    private List<Platform> platforms;

    @OrderBy("sortOrder")
    @OneToMany(mappedBy = "game", cascade = PERSIST)
    private List<Difficulty> difficulties;

    @OrderBy("sortOrder")
    @OneToMany(mappedBy = "game", cascade = PERSIST)
    private List<Mode> modes;

    @OrderBy("sortOrder")
    @OneToMany(mappedBy = "game", cascade = PERSIST)
    private List<Ship> ships;

    @OrderBy("sortOrder")
    @OneToMany(mappedBy = "game", cascade = PERSIST)
    private List<Stage> stages;

    @JsonIgnore
    @OneToOne(mappedBy = "game", cascade = ALL)
    private Event event;

    public String post() {
        return thread.replace("viewtopic.php?", "posting.php?mode=reply&f=20&");
    }

    @Override
    public String toString() {
        return title;
    }

    @JsonIgnore
    public String getEscapedTitle() {
        String s = title.replaceAll("[^a-zA-Z0-9]", "_");
        s = s.replaceAll("_(_)*", "_");
        if (s.endsWith("_")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    @JsonIgnore
    public String getCoverType() {
        if (cover.endsWith("jpg") || cover.endsWith("jpeg")) {
            return "image/jpeg";
        }
        if (cover.endsWith("png")) {
            return "image/png";
        }
        return "image/gif";
    }

    @JsonIgnore
    public Collection<Player> getPlayers() {
        Set<Player> players = new HashSet<Player>();
        for (Score score : scores) {
            players.add(score.getPlayer());
        }
        return players;
    }

    public boolean hasShip() {
        return ships != null && !ships.isEmpty();
    }

    public boolean hasDifficulties() {
        return difficulties != null && !difficulties.isEmpty();
    }

    public boolean hasModes() {
        return modes != null && !modes.isEmpty();
    }

    @JsonIgnore
    public int getOneCreditCount() {
        Collection<Score> oneCreditScores = allScores.stream().filter(Score::isOnecc).collect(Collectors.toList());
        Set<String> uniqueOneCreditScores = new HashSet<String>();
        for (Score oneCreditScore : oneCreditScores) {
            String player = oneCreditScore.getPlayer().getName();
            String mode = oneCreditScore.modeName();
            String difficulty = oneCreditScore.difficultyName();
            uniqueOneCreditScores.add(player + mode + difficulty);
        }
        return uniqueOneCreditScores.size();
    }

    public boolean hasTimerScores() {
        if (this.modes == null || this.modes.isEmpty()) {
            return false;
        }
        for (Mode mode : modes) {
            if (mode.isTimerScore()) {
                return true;
            }
        }
        return false;
    }

    @JsonIgnore
    public Collection<Score> getAllOneCCS() {
        MultiKeyMap map = new MultiKeyMap();
        for (Score onecc : this.oneccs) {
            Score score = (Score) map.get(onecc.getPlayer(), onecc.getMode(), onecc.getDifficulty());
            if (score == null) {
                map.put(onecc.getPlayer(), onecc.getMode(), onecc.getDifficulty(), onecc);
            } else if (score.isWorstThan(onecc)) {
                map.put(onecc.getPlayer(), onecc.getMode(), onecc.getDifficulty(), onecc);
            }
        }
        List<Score> scores = new ArrayList<Score>(map.values());
        scores.sort((o1, o2) -> o1.getPlayer().getName().compareToIgnoreCase(o2.getPlayer().getName()));
        return scores;
    }

    public boolean hasStages() {
        return stages != null && !stages.isEmpty();
    }

    public boolean hasPlatforms() {
        return platforms != null && !platforms.isEmpty();
    }
}
