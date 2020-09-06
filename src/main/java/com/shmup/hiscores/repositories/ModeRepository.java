package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Mode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeRepository extends CrudRepository<Mode, Long> {

    String FIND_UNPLAYED_MODES_BY_PLAYER = "SELECT m.* FROM mode m WHERE m.game_id IN (" +
            "  SELECT DISTINCT s.game_id FROM score s WHERE s.player_id = ?1 AND s.rank IS NOT NULL) " +
            "  AND m.id NOT IN (" +
            "  SELECT DISTINCT mode_id FROM score s WHERE player_id = ?1 AND mode_id IS NOT NULL AND s.rank IS NOT NULL" +
            ")";

    @Query(value = FIND_UNPLAYED_MODES_BY_PLAYER, nativeQuery = true)
    List<Mode> findUnplayedModesByPlayer(Long playerId);
}
