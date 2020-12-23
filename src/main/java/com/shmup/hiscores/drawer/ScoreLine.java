package com.shmup.hiscores.drawer;

import com.google.common.base.Strings;
import com.shmup.hiscores.models.Score;

import java.awt.*;

import static com.shmup.hiscores.drawer.RankingGameConfiguration.COLOR_SHMUP_GREY;
import static java.awt.Color.*;
import static java.awt.Font.*;
import static org.apache.commons.lang3.StringUtils.*;

public class ScoreLine implements PictureLine {

    private static final int fontWidth = 12;

    private final static Font normalFont = new Font("Liberation Mono", PLAIN, 12);
    private final static Font parameterFont = new Font("Liberation Mono", PLAIN, 14);
    private final static Font playerFont = new Font("Liberation Mono", BOLD, 12);
    private final static Font scoreFont = new Font("Liberation Mono", BOLD, 14);
    private final static Font stageFont = new Font("Liberation Mono", ITALIC, 14);

    private static final int SCORE_X = 140;
    private static final int STAGE_PLATFORM_X = SCORE_X + 220;

    private static final Color COLOR_SHMUP_PLAYER = new Color(247, 127, 74);
    private static final Color COLOR_SHMUP_SCORE = new Color(245, 70, 52);
    private static final Color COLOR_SHMUP_COMMENT = new Color(193, 193, 193);

    private final Score score;

    public ScoreLine(Score score) {
        this.score = score;
    }

    @Override
    public void draw(Graphics2D graphics, int y, RankingGameConfiguration rankingGameConfiguration) {
        pyjama(graphics, y);
        rank(graphics, y);
        player(graphics, y);
        score(graphics, y);
        stage(graphics, y, rankingGameConfiguration);
        platformAndComment(graphics, y, rankingGameConfiguration);
    }

    private void platformAndComment(Graphics2D graphics, int y, RankingGameConfiguration rankingGameConfiguration) {
        String platform = score.getPlatform() == null ? "" : leftPad(score.getPlatform().getName(), rankingGameConfiguration.maxPlatformLength);
        if (score.getGame().getPlatforms().isEmpty() || score.getGame().getPlatforms().size() == 1) {
            platform = "";
        }
        graphics.setColor(COLOR_SHMUP_COMMENT);
        graphics.setFont(parameterFont);
        graphics.drawString(platform + "   " + score.shipName() + " " + defaultString(score.getComment()).trim(), STAGE_PLATFORM_X + (rankingGameConfiguration.maxStageLength * fontWidth), y);
    }

    private void stage(Graphics2D graphics, int y, RankingGameConfiguration rankingGameConfiguration) {
        graphics.setColor(DARK_GRAY);
        graphics.setFont(parameterFont);
        String stage = score.getStage() == null ? "" : rightPad(score.getStage().getName(), rankingGameConfiguration.maxStageLength);
        graphics.setFont(stageFont);
        graphics.drawString(stage + "   ", STAGE_PLATFORM_X, y);
    }

    private void score(Graphics2D graphics, int y) {
        graphics.setColor(COLOR_SHMUP_SCORE);
        graphics.setFont(scoreFont);
        graphics.drawString(Strings.padStart(score.formattedValue(), "14607555540022222222".length() + 6, ' '), SCORE_X, y);
    }

    private void player(Graphics2D graphics, int y) {
        graphics.setFont(playerFont);
        graphics.setColor(COLOR_SHMUP_PLAYER);
        graphics.drawString(score.getPlayer().getName(), 30, y);
    }

    private void rank(Graphics2D graphics, int y) {
        graphics.setColor(GRAY);
        graphics.setFont(normalFont);
        String rank = leftPad(score.getRank() + ".", 3);
        graphics.drawString(rank, 0, y);
    }

    private void pyjama(Graphics2D graphics, int y) {
        if (score.getRank() % 2 == 1) {
            graphics.setColor(WHITE);
        } else {
            graphics.setColor(COLOR_SHMUP_GREY);
        }
        graphics.fillRect(0, y - RankingGameConfiguration.fontHeight, RankingGameConfiguration.width, y);
    }
}

