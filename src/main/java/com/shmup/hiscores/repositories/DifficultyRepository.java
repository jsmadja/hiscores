package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Difficulty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DifficultyRepository extends CrudRepository<Difficulty, Long> {

    String FIND_UNPLAYED_MODES_BY_PLAYER = "SELECT d.* FROM difficulty d WHERE d.game_id IN (" +
            "  SELECT DISTINCT s.game_id FROM score s WHERE s.player_id = ?1 AND s.rank IS NOT NULL)" +
            "  AND d.id NOT IN (" +
            "  SELECT DISTINCT difficulty_id FROM score s WHERE player_id = ?1 AND difficulty_id IS NOT NULL AND s.rank IS NOT NULL" +
            ")";

    @Query(value = FIND_UNPLAYED_MODES_BY_PLAYER, nativeQuery = true)
    List<Difficulty> findUnplayedDifficultiesByPlayer(Long playerId);
}
