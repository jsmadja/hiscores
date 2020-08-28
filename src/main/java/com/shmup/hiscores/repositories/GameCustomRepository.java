package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Difficulty;
import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Mode;
import com.shmup.hiscores.models.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Deprecated
@Repository
public class GameCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Score> findBestScoresByVIPPlayers(Game game) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Score> cq = cb.createQuery(Score.class);
        Root<Score> score = cq.from(Score.class);
        cq.select(score).where(cb.equal(score.get("game"), game))
                .orderBy(cb.desc(score.get("value")));
        return entityManager.createQuery(cq).getResultList();
    }

    public List<Score> findBestScoresByVIPPlayers(Game game, Difficulty difficulty, Mode mode) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Score> cq = cb.createQuery(Score.class);
        Root<Score> score = cq.from(Score.class);
        //score.fetch("mode");
        //score.fetch("difficulty");
        //score.fetch("player");
        Predicate gameClause = cb.equal(score.get("game"), game);
        Predicate difficultyClause = cb.equal(score.get("difficulty"), difficulty);
        Predicate modeClause = cb.equal(score.get("mode"), mode);

        List<Predicate> clauses = new ArrayList<>();
        clauses.add(gameClause);
        if (difficulty != null) {
            clauses.add(difficultyClause);
        }
        if (mode != null) {
            clauses.add(modeClause);
        }
        cq
                .select(score)
                .where(cb.and(clauses.toArray(new Predicate[0])))
                .orderBy((mode == null || !mode.isTimerScore()) ? cb.desc(score.get("value")) : cb.asc(score.get("value")));
        return entityManager.createQuery(cq).getResultList();
    }

}
