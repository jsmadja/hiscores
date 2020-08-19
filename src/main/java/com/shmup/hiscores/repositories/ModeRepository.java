package com.shmup.hiscores.repositories;

import com.shmup.hiscores.models.Mode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository
public interface ModeRepository extends CrudRepository<Mode, Long> {
}
