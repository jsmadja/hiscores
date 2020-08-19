package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Stage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository
public interface StageRepository extends CrudRepository<Stage, Long> {
}
