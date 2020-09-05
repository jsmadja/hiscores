package com.shmup.hiscores.drawer;

import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Versus;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;

import static com.shmup.hiscores.drawer.RankingGameConfiguration.COLOR_SHMUP_TEXT;
import static java.awt.Font.PLAIN;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

@Deprecated
public class VersusPicture {
    public static final int WIDTH = 724;
    private final static Font gameFont = new Font("Lucida", PLAIN, 11);

    public static BufferedImage createVersusPicture(Player player, Versus versus) {
        BufferedImage bi = new BufferedImage(WIDTH, 15, TYPE_INT_ARGB);
        Graphics2D graphics = bi.createGraphics();
        FontMetrics fontMetrics = graphics.getFontMetrics();
        graphics.setColor(COLOR_SHMUP_TEXT);
        graphics.setFont(gameFont);
        graphics.drawString(message(player, versus), 0, fontMetrics.getAscent());
        return bi;
    }

    private static String message(Player player, Versus versus) {
        return MessageFormat.format("Adversaire privilégié : {0} (ratio: {1}/{2})", versus.player2.getName(), versus.winCount(), versus.loseCount());
    }
}
