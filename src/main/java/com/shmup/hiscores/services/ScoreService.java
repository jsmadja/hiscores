package com.shmup.hiscores.services;

import com.shmup.hiscores.models.*;
import com.shmup.hiscores.repositories.ScoreCustomRepository;
import com.shmup.hiscores.repositories.ScoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Deprecated
@AllArgsConstructor
@Service
public class ScoreService {

    private ScoreCustomRepository scoreCustomRepository;
    private ScoreRepository scoreRepository;

    public List<Score> getLastScores() {
        return scoreCustomRepository.getLastScores();
    }

    public Optional<Score> getBestScoreFor(Player player, Game game, Mode mode, Difficulty difficulty) {
        return scoreCustomRepository.getBestScoreFor(player, game, mode, difficulty);
    }

    public void save(Score score) {
        scoreRepository.save(score);
    }

    public Score refresh(Score score) {
        return scoreRepository.findById(score.getId()).get();
    }

    public void update(Score score) {
        scoreRepository.save(score);
    }

    public long getScoresCount() {
        return scoreRepository.count();
    }
}
