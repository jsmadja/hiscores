package com.shmup.hiscores.services;

import com.shmup.hiscores.models.Difficulty;
import com.shmup.hiscores.repositories.DifficultyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Deprecated
@Service
@AllArgsConstructor
public class DifficultyService {

    private DifficultyRepository difficultyRepository;

    public Difficulty findById(Long difficultyId) {
        return difficultyRepository.findById(difficultyId).get();
    }
}
