package com.shmup.hiscores.services;

import com.shmup.hiscores.dto.PlatformDTO;
import com.shmup.hiscores.models.Platform;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Deprecated
@Repository
public interface PlatformRepository extends CrudRepository<Platform, Long> {

    @Query("SELECT DISTINCT name FROM Platform ORDER BY name")
    List<String> findDistinctByName();

    @Query("SELECT name AS title, COUNT(id) AS games from Platform GROUP BY name ORDER BY 1")
    List<PlatformDTO> findPlatformsWithGameCount();
}
