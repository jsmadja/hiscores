package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Score;
import org.springframework.data.repository.CrudRepository;

@Deprecated
public interface ScoreRepository extends CrudRepository<Score, Long> {
}
