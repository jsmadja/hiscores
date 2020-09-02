package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Player;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Deprecated
public interface GameRepository extends CrudRepository<Game, Long> {
    @Cacheable("games")
    List<Game> findByOrderByTitleAsc();

    @Query("SELECT DISTINCT s.game FROM Score s WHERE s.player = :player AND s.rank IS NOT NULL ORDER BY s.game.title")
    List<Game> findByPlayer(Player player);

    @Query(value = "select game.* " +
            "from game " +
            "       join platform pl on pl.game_id = game.id " +
            "where pl.name in (select distinct name " +
            "                  from score " +
            "                         join platform p on p.id = platform_id " +
            "                  where player_id = ?1)" +
            "  and game.id NOT IN (SELECT DISTINCT game_id" +
            "                      FROM score" +
            "                      WHERE player_id = ?1" +
            "                        AND rank IS NOT NULL)" +
            "  and game.id NOT IN (SELECT DISTINCT game_id FROM event)",
            nativeQuery = true)
    List<Game> findByPlayerNot(Long id);
}
