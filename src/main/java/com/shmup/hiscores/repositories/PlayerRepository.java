package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Deprecated
public interface PlayerRepository extends CrudRepository<Player, Long> {
    Player findByShmupUserId(Long shmupUserId);

    Player findByName(String name);

    List<Player> findByOrderByNameAsc();

}
