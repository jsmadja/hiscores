package com.shmup.hiscores.services;

import com.shmup.hiscores.models.Stage;
import com.shmup.hiscores.repositories.StageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Deprecated
@Service
@AllArgsConstructor
public class StageService {

    private StageRepository stageRepository;

    public Stage findById(Long stageId) {
        return stageRepository.findById(stageId).get();
    }
}
