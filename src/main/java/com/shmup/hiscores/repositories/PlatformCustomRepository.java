package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class PlatformCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public List<Game> findGamesByPlatform(String platformTitle) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Game> cq = cb.createQuery(Game.class);
        Root<Game> game = cq.from(Game.class);
        Join<Game, Platform> join = game.join("platforms", JoinType.INNER);
        cq
                .select(game)
                .where(cb.equal(join.get("name"), platformTitle))
                .orderBy(cb.asc(game.get("title")));
        return entityManager.createQuery(cq).getResultList();
    }

}
