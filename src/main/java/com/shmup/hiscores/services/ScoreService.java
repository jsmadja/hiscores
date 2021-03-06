package com.shmup.hiscores.services;

import com.shmup.hiscores.models.*;
import com.shmup.hiscores.repositories.ScoreCustomRepository;
import com.shmup.hiscores.repositories.ScoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
public class ScoreService {

    private final ScoreCustomRepository scoreCustomRepository;
    private final ScoreRepository scoreRepository;

    @Deprecated
    public List<Score> getLastScores() {
        return scoreCustomRepository.getLastScores();
    }

    @Deprecated
    public Optional<Score> getBestScoreFor(Player player, Game game, Mode mode, Difficulty difficulty) {
        return scoreCustomRepository.getBestScoreFor(player, game, mode, difficulty);
    }

    @Deprecated
    public void save(Score score) {
        scoreRepository.save(score);
    }

    @Deprecated
    public Score refresh(Score score) {
        return scoreRepository.findById(score.getId()).get();
    }

    @Deprecated
    public void update(Score score) {
        scoreRepository.save(score);
    }

    @Deprecated
    public List<Score> getLastScoresOf(Player player) {
        return scoreCustomRepository.getLastScoresOf(player);
    }

    @Deprecated
    public Score findById(Long id) {
        return scoreRepository.findById(id).get();
    }

    @Deprecated
    public Score findOldestScoreOf(Player player) {
        return scoreRepository.findFirstByPlayerAndRankIsNotNullOrderByCreatedAtAsc(player);
    }

    @Deprecated
    public Score findLatestScoreOf(Player player) {
        return scoreRepository.findFirstByPlayerAndRankIsNotNullOrderByCreatedAtDesc(player);
    }

    @Deprecated
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

    @Deprecated
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

    @Deprecated
    public List<KillListItem> getKillListOf(Player player) {
        return scoreRepository.findByPlayerAndRankIsNotNull(player)
                .stream()
                .map((Score score) -> new KillListItem(score, scoreCustomRepository.getPreviousScore(score)))
                .sorted(comparing(KillListItem::getRatio))
                .collect(toList());
    }

    public List<Score> getPlayerLastScoresOfGame(Player player, Game game) {
        return scoreRepository.findByPlayerAndRankIsNotNullAndGameOrderByCreatedAtDesc(player, game);
    }
}
