package com.shmup.hiscores.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.*;

@Deprecated
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Player extends BaseModel<Player> {

    public static Player guest = new Player(0L, "guest");

    private String name;

    private Long shmupUserId;

    private boolean hideMedals;

    @JsonIgnore
    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    @Where(clause = "rank > 0")
    private List<Score> scores = new ArrayList<Score>();

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    private List<Score> allScores = new ArrayList<Score>();

    private boolean vip;

    public Player(String name) {
        super();
        setName(name);
    }

    public Player(Long id, String name) {
        this(name);
        setId(id);
    }

    public void renewUpdateAt() {
        updatedAt = new Date();
    }

    @Deprecated
    public Optional<Score> getLastScore() {
        List<Score> scores = new ArrayList<Score>(this.scores);
        Collections.sort(scores, (o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        if(scores.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(scores.get(0));
    }

    public Versus getComparisonWith(Player p) {
        Versus versus = new Versus(this, p);
        List<Score> scores = this.scores;
        for (Score score : scores) {
            Score comparisonScore = p.getEquivalentScore(score);
            if (comparisonScore != null) {
                Versus.Comparison comparison = new Versus.Comparison(score.getGame(), score.getDifficulty(), score.getMode(), score, comparisonScore);
                versus.add(comparison);
            }
        }
        return versus;
    }

    private Score getEquivalentScore(Score reference) {
        for (Score score : scores) {
            if (score.getGame().equals(reference.getGame())) {
                if (score.hasMode(reference.getMode()) && score.hasDifficulty(reference.getDifficulty())) {
                    return score;
                }
            }
        }
        return null;
    }

    public boolean isAuthenticated() {
        return !this.equals(guest);
    }
}
