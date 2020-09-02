package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Deprecated
@Repository
public class ScoreCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Score> getLastScores() {
        return this.getLastScoresOf(null);
    }


    public List<Score> getLastScoresOf(Player player) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Score> cq = cb.createQuery(Score.class);
        Root<Score> score = cq.from(Score.class);
        score.join("game");
        score.join("player");
        score.join("platform");
        score.join("stage", JoinType.LEFT);
        score.join("mode", JoinType.LEFT);
        score.join("difficulty", JoinType.LEFT);
        score.join("ship", JoinType.LEFT);

        List<Predicate> wherePred = new ArrayList<>();
        wherePred.add(cb.isNotNull(score.get("rank")));
        if (player != null) {
            wherePred.add(cb.equal(score.get("player"), player));
        }
        Predicate clause = cb.and(wherePred.toArray(new Predicate[0]));
        cq
                .select(score)
                .where(clause)
                .orderBy(cb.desc(score.get("createdAt")));
        return entityManager.createQuery(cq).setMaxResults(6).getResultList();
    }

    public Optional<Score> getBestScoreFor(Player player, Game game, Mode mode, Difficulty difficulty) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Score> cq = cb.createQuery(Score.class);
        Root<Score> score = cq.from(Score.class);
        cq
                .select(score)
                .where(
                        cb.equal(score.get("player"), player),
                        cb.equal(score.get("game"), game),
                        cb.equal(score.get("mode"), mode),
                        cb.equal(score.get("difficulty"), difficulty)
                )
                .orderBy(game.hasTimerScores() ? cb.asc(score.get("value")) : cb.desc(score.get("value")));
        TypedQuery<Score> scoreTypedQuery = entityManager.createQuery(cq).setMaxResults(1);
        List<Score> scores = scoreTypedQuery.getResultList();
        return scores.isEmpty() ? empty() : of(scores.get(0));
    }


    public Optional<Score> getPreviousScore(Score score) {
        String query = "SELECT score.* " +
                "        FROM score " +
                "JOIN game ON game.id = score.game_id " +
                "JOIN player ON player.id = score.player_id " +
                "LEFT JOIN platform ON platform.id = score.platform_id " +
                "LEFT JOIN mode ON mode.id = score.mode_id " +
                "LEFT JOIN difficulty ON difficulty.id = score.difficulty_id " +
                "        WHERE rank = " + (score.getRank() - 1) +
                " AND score.game_id = " + score.getGame().getId() +
                " AND mode_id " + (score.getMode() != null ? "= " + score.getMode().getId() : "IS NULL") +
                " AND difficulty_id " + (score.getDifficulty() != null ? " = " + score.getDifficulty().getId() : "IS NULL");
        return entityManager.createNativeQuery(query, Score.class).getResultList().stream().findFirst();
    }

}
