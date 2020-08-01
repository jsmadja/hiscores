package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Difficulty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DifficultyRepository extends CrudRepository<Difficulty, Long> {
}
