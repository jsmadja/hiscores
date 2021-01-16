package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Deprecated
@Repository
public class PlayerCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public int getRankCount(Player player, int rank) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Score> score = cq.from(Score.class);
        cq
                .select(cb.count(score))
                .where(
                        cb.equal(score.get("player"), player),
                        cb.equal(score.get("rank"), rank)
                );
        return entityManager.createQuery(cq).getSingleResult().intValue();
    }

    public int findOneCreditCount(Player player) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Score> score = cq.from(Score.class);
        cq
                .select(cb.count(score))
                .where(
                        cb.equal(score.get("player"), player),
                        cb.isNotNull(score.get("rank")),
                        cb.equal(score.get("onecc"), true)
                );
        return entityManager.createQuery(cq).getSingleResult().intValue();
    }

    public int getGameCount(Player player) {
        return entityManager
                .createNativeQuery("select distinct game_id from score where player_id=:player_id")
                .setParameter("player_id", player.getId())
                .getResultList()
                .size();
    }

}
