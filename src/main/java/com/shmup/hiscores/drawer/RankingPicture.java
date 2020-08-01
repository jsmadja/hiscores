package com.shmup.hiscores.drawer;

import com.shmup.hiscores.models.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.awt.RenderingHints.*;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class RankingPicture {

    public static BufferedImage createRankingPicture(Game game, List<Ranking> rankings) {
        Collection<Difficulty> difficulties = new ArrayList<Difficulty>(game.getDifficulties());
        Collection<Mode> modes = new ArrayList<Mode>(game.getModes());
        if (difficulties.isEmpty()) {
            difficulties.add(null);
        }
        if (modes.isEmpty()) {
            modes.add(null);
        }
        List<PictureLine> pictureLines = new ArrayList<PictureLine>();
        for (Ranking ranking : rankings) {
            if (!ranking.getScores().isEmpty()) {
                pictureLines.add(new BreakLine());
                pictureLines.add(new GameLine(ranking));
                pictureLines.add(new BreakLine());
                for (Score score : ranking.getScores()) {
                    pictureLines.add(new ScoreLine(score));
                }
                pictureLines.add(new BreakLine());
            }
        }
        return computeRanking(pictureLines, new RankingGameConfiguration(game));
    }

    private static BufferedImage computeRanking(List<PictureLine> pictureLines, RankingGameConfiguration rankingGameConfiguration) {
        int height = (RankingGameConfiguration.fontHeight + 5) * (pictureLines.size());
        if (height == 0) {
            height = 1;
        }
        BufferedImage bi = new BufferedImage(RankingGameConfiguration.width, height, TYPE_INT_ARGB);
        Graphics2D graphics = bi.createGraphics();
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int stringHeight = fontMetrics.getAscent() + 5;
        for (int i = 0; i < pictureLines.size(); i++) {
            pictureLines.get(i).draw(graphics, 10 + (i * stringHeight), rankingGameConfiguration);
        }
        return bi;
    }

}
