package com.shmup.hiscores.dto;

import com.shmup.hiscores.models.Difficulty;
import com.shmup.hiscores.models.Mode;
import com.shmup.hiscores.models.Ranking;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Deprecated
@AllArgsConstructor
@Data
public class RankingDTO {
    private final Mode mode;
    private final Difficulty difficulty;
    private final List<ScoreDTO> scores;

    public static RankingDTO toRankingDTO(Ranking ranking) {
        List<ScoreDTO> scores = ranking
                .getScores()
                .stream()
                .map(ScoreDTO::toScoreDTO)
                .collect(toList());
        return new RankingDTO(
                ranking.getMode(),
                ranking.getDifficulty(),
                scores
        );
    }

}
