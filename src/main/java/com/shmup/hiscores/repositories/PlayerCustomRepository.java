package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@Deprecated
@Repository
public class PlayerCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Player> findAllJoinScores() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Player> cq = cb.createQuery(Player.class);
        Root<Player> root = cq.from(Player.class);
        root.join("scores", JoinType.LEFT);
        cq.select(root)
                .orderBy(cb.desc(root.get("value")));
        return entityManager.createQuery(cq).getResultList();
    }

    public int getRankCount(Player player, int rank) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Score> cq = cb.createQuery(Score.class);
        Root<Score> score = cq.from(Score.class);
        cq
                .where(
                        cb.equal(score.get("player"), player),
                        cb.equal(score.get("rank"), rank)
                );
        cq.select(score);
        return entityManager.createQuery(cq).getResultList().size();
    }

    public List<Score> findOneCreditScores(Player player) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Score> cq = cb.createQuery(Score.class);
        Root<Score> score = cq.from(Score.class);
        cq
                .select(score)
                .where(
                        cb.equal(score.get("player"), player),
                        cb.equal(score.get("onecc"), true),
                        cb.isNotNull(score.get("rank"))
                );
        return entityManager.createQuery(cq).getResultList();
    }

    public int getGameCount(Player player) {
        return entityManager
                .createNativeQuery("select distinct game_id from score where player_id=:player_id")
                .setParameter("player_id", player.getId())
                .getResultList()
                .size();
    }
}
