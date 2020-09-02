package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Mode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Deprecated
@Repository
public interface ModeRepository extends CrudRepository<Mode, Long> {

    @Query(value = "SELECT m.* " +
            "FROM mode m " +
            "JOIN game g ON g.id = m.game_id " +
            "WHERE m.game_id IN (" +
            "  SELECT DISTINCT s.game_id " +
            "  FROM score s " +
            "  WHERE s.player_id = ?1 " +
            "    AND s.rank IS NOT NULL) " +
            "  AND m.id NOT IN (" +
            "  SELECT DISTINCT mode_id " +
            "  FROM score s " +
            "  WHERE player_id = ?1 " +
            "    AND mode_id IS NOT NULL " +
            "    AND s.rank IS NOT NULL)",
            nativeQuery = true)
    List<Mode> findByPlayerNot(Long playerId);
}
