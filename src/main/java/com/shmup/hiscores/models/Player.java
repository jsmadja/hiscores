package com.shmup.hiscores.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Player extends BaseModel<Player> {

    public static Player guest = new Player(0L, "guest");

    private String name;

    private Long shmupUserId;

    private boolean hideMedals;

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    @Where(clause = "rank > 0")
    private List<Score> scores = new ArrayList<Score>();

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    private List<Score> allScores = new ArrayList<Score>();

    private boolean vip;

    public Player(String name) {
        super();
        this.name = name;
    }

    public Player(Long id, String name) {
        this(name);
        this.id = id;
    }

    public void renewUpdateAt() {
        updatedAt = new Date();
    }

    @JsonIgnore
    @Deprecated
    public Optional<Score> getLastScore() {
        List<Score> scores = new ArrayList<Score>(this.scores);
        Collections.sort(scores, (o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        if (scores.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(scores.get(0));
    }

    @Deprecated
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
        return !this.getId().equals(0L);
    }

    public boolean isAdministrator() {
        return id == 1          // anzymus
                || id == 42     // mickey
                || id == 137    // trizeal
                || id == 705    // Vzurkr
                || id == 191    // lerebours
                || id == 7      // SL
                || id == 231    // MKNIGHT
                || id == 269    // Yami
                || id == 30     // shadow gallery
                || id == 116    // Doudinou
                || id == 150    // Undef
                || id == 779    // Kat
                || id == 159    // Cormano
                || id == 226    // Radigo
                || id == 57     // Akaimakai
                || id == 223    // Guts
                ;
    }

    public boolean isSuperAdministrator() {
        return id == 1;
    }

}
