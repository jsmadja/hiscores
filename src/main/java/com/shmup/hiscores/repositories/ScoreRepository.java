package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Deprecated
public interface ScoreRepository extends CrudRepository<Score, Long> {
    Score findFirstByPlayerAndRankIsNotNullOrderByCreatedAtAsc(Player player);

    Score findFirstByPlayerAndRankIsNotNullOrderByCreatedAtDesc(Player player);

    List<Score> findByPlayerAndRankIsNotNull(Player player);
}
