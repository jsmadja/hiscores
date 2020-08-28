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
}
