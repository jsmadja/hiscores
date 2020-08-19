package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Difficulty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository
public interface DifficultyRepository extends CrudRepository<Difficulty, Long> {
}
