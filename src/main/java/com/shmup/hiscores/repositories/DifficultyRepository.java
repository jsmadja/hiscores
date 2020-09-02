package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Difficulty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Deprecated
@Repository
public interface DifficultyRepository extends CrudRepository<Difficulty, Long> {

    @Query(value = "SELECT d.* " +
            "FROM difficulty d" +
            "       JOIN game g ON g.id = d.game_id " +
            "WHERE d.game_id IN (" +
            "  SELECT DISTINCT s.game_id" +
            "  FROM score s" +
            "  WHERE s.player_id = ?1" +
            "    AND s.rank IS NOT NULL)" +
            "  AND d.id NOT IN (" +
            "  SELECT DISTINCT difficulty_id" +
            "  FROM score s" +
            "  WHERE player_id = ?1" +
            "    AND difficulty_id IS NOT NULL" +
            "    AND s.rank IS NOT NULL)", nativeQuery = true)
    List<Difficulty> findByPlayerNot(Long playerId);
}
