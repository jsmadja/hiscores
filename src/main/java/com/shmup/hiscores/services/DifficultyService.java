package com.shmup.hiscores.services;

import com.shmup.hiscores.models.Difficulty;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.repositories.DifficultyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Deprecated
@Service
@AllArgsConstructor
public class DifficultyService {

    private final DifficultyRepository difficultyRepository;

    public Difficulty findById(Long difficultyId) {
        return difficultyRepository.findById(difficultyId).get();
    }

    public List<Difficulty> getUnplayedDifficultiesBy(Player player) {
        return difficultyRepository.findUnplayedDifficultiesByPlayer(player.getId());
    }
}
