package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Game;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Deprecated
public interface GameRepository extends CrudRepository<Game, Long> {
    @Cacheable("games")
    List<Game> findByOrderByTitleAsc();
}
