package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.*;

@Deprecated
@Repository
public class ScoreCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Score> getLastScores() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Score> cq = cb.createQuery(Score.class);
        Root<Score> score = cq.from(Score.class);
        score.fetch("game");
        score.fetch("stage");
        score.fetch("ship");
        score.fetch("platform");
        score.fetch("player");
        score.fetch("mode");
        score.fetch("difficulty");
        cq
                .select(score)
                .where(cb.isNotNull(score.get("rank")))
                .orderBy(cb.desc(score.get("createdAt")));
        return entityManager.createQuery(cq).setMaxResults(10).getResultList();
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
}
