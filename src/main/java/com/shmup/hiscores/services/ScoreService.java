package com.shmup.hiscores.services;

import com.shmup.hiscores.models.*;
import com.shmup.hiscores.repositories.ScoreCustomRepository;
import com.shmup.hiscores.repositories.ScoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

@Deprecated
@AllArgsConstructor
@Service
public class ScoreService {

    private final ScoreCustomRepository scoreCustomRepository;
    private final ScoreRepository scoreRepository;

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

    public List<Score> getLastScoresOf(Player player) {
        return scoreCustomRepository.getLastScoresOf(player);
    }

    public Score findById(Long id) {
        return scoreRepository.findById(id).get();
    }

    public Score findOldestScoreOf(Player player) {
        return scoreRepository.findFirstByPlayerAndRankIsNotNullOrderByCreatedAtAsc(player);
    }

    public Score findLatestScoreOf(Player player) {
        return scoreRepository.findFirstByPlayerAndRankIsNotNullOrderByCreatedAtDesc(player);
    }

    public Score findNearestScoreOf(Player player) {
        Map<Score, Score> previousScore = new HashMap<>();
        Comparator<Score> scoreComparator = (s1, s2) -> {
            Score previousS1 = previousScore.get(s1);
            Score previousS2 = previousScore.get(s2);
            if (previousS1 == null || previousS2 == null) {
                return 0;
            }
            BigDecimal value1 = previousS1.getValue();
            BigDecimal value2 = previousS2.getValue();
            BigDecimal ratio1 = s1.getValue().divide(value1, MathContext.DECIMAL128).subtract(BigDecimal.ONE);
            BigDecimal ratio2 = s2.getValue().divide(value2, MathContext.DECIMAL128).subtract(BigDecimal.ONE);
            return ratio2.compareTo(ratio1);
        };
        return scoreRepository.findByPlayerAndRankIsNotNull(player)
                .stream()
                .map((Score score) -> {
                    previousScore.put(score, scoreCustomRepository.getPreviousScore(score).orElse(null));
                    return score;
                }).min(scoreComparator).orElse(null);
    }

    public Score findFarestScoreOf(Player player) {
        Map<Score, Score> previousScore = new HashMap<>();
        return scoreRepository.findByPlayerAndRankIsNotNull(player)
                .stream()
                .map((Score score) -> {
                    previousScore.put(score, scoreCustomRepository.getPreviousScore(score).orElse(null));
                    return score;
                }).max((s1, s2) -> {
                    Score previousS1 = previousScore.get(s1);
                    Score previousS2 = previousScore.get(s2);
                    if (previousS1 == null || previousS2 == null) {
                        return 0;
                    }
                    BigDecimal ratio1 = s1.getValue().divide(previousS1.getValue(), MathContext.DECIMAL128).subtract(BigDecimal.ONE);
                    BigDecimal ratio2 = s2.getValue().divide(previousS2.getValue(), MathContext.DECIMAL128).subtract(BigDecimal.ONE);
                    return ratio2.compareTo(ratio1);
                }).orElse(null);
    }
}
