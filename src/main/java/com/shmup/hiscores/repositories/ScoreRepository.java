package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScoreRepository extends CrudRepository<Score, Long> {
    @Deprecated
    Score findFirstByPlayerAndRankIsNotNullOrderByCreatedAtAsc(Player player);

    @Deprecated
    Score findFirstByPlayerAndRankIsNotNullOrderByCreatedAtDesc(Player player);

    @Deprecated
    List<Score> findByPlayerAndRankIsNotNull(Player player);

    List<Score> findByPlayerAndRankIsNotNullAndGameOrderByCreatedAtDesc(Player player, Game game);
}
