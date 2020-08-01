package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Player;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    Player findByShmupUserId(Long shmupUserId);

    Player findByName(String name);

    @Cacheable("players")
    List<Player> findAll();

}
