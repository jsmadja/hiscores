package com.shmup.hiscores.drawer;

import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

import static com.shmup.hiscores.drawer.RankingGameConfiguration.COLOR_SHMUP_TEXT;
import static java.awt.Font.PLAIN;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

@Deprecated
public class SignaturePicture {
    public static final int WIDTH = 724;
    private final static Font gameFont = new Font("Lucida", PLAIN, 11);

    public static BufferedImage createSignaturePicture(Player player) {
        BufferedImage bi = new BufferedImage(WIDTH, 15, TYPE_INT_ARGB);
        Graphics2D graphics = bi.createGraphics();
        FontMetrics fontMetrics = graphics.getFontMetrics();
        graphics.setColor(COLOR_SHMUP_TEXT);
        graphics.setFont(gameFont);
        Optional<Score> optionalLastScore = player.getLastScore();
        if(optionalLastScore.isPresent()) {
            Score lastScore = optionalLastScore.get();
            graphics.drawString(message(lastScore), 0, fontMetrics.getAscent());
        }
        return bi;
    }

    private static String message(Score lastScore) {
        return "Dernier score réalisé " + lastScore.formattedDateInFrench() + " sur " + lastScore.getGame().getTitle() + " (" + lastScore.formattedValue() + "pts - " + lastScore.formattedRank() + ")";
    }
}
