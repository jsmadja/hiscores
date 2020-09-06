package com.shmup.hiscores.controllers;

import com.shmup.hiscores.models.Difficulty;
import com.shmup.hiscores.models.Mode;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import com.shmup.hiscores.services.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@RestController
public class ScoreController {

    private final ScoreService scoreService;

    @GetMapping("/scores")
    public List<Score> findScores() {
        return scoreService.getLastScores();
    }

    @Deprecated
    @GetMapping("/scores/{id}")
    public Score getById(@PathVariable("id") Long id) {
        return scoreService.findById(id);
    }

    @GetMapping("/scores/{id}/history")
    public List<Score> getHistory(@PathVariable("id") Score score) {
        Player player = score.getPlayer();
        List<Score> allScores = player.getAllScores();
        List<Score> scores = allScores.stream().filter(
                _score -> {
                    Mode mode = score.getMode();
                    Mode mode1 = _score.getMode();
                    Difficulty difficulty = score.getDifficulty();
                    Difficulty difficulty1 = _score.getDifficulty();
                    boolean isSameGame = score.getGame().equals(_score.getGame());
                    boolean isSameMode = mode == mode1 || ((mode != null && mode1 != null) && mode.equals(mode1));
                    boolean isSameDifficulty = difficulty == difficulty1 || ((difficulty != null && difficulty1 != null) && difficulty.equals(difficulty1));
                    return isSameGame && isSameMode && isSameDifficulty;
                }
        ).collect(toList());
        if (scores.size() > 1) {
            for (int i = 1; i < scores.size(); i++) {
                Score previous = scores.get(i - 1);
                Score current = scores.get(i);
                BigDecimal gap = current.getValue().subtract(previous.getValue());
                if (previous.getValue().equals(BigDecimal.ZERO)) {
                    current.setGapWithPreviousScore(0L);
                } else {
                    current.setGapWithPreviousScore(Math.abs(gap.multiply(BigDecimal.valueOf(100)).divide(previous.getValue(), HALF_UP).longValue()));
                }
            }
        }
        return scores;
    }

}
