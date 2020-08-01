package com.shmup.hiscores.drawer;

import com.shmup.hiscores.models.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.Font.BOLD;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.apache.commons.lang3.StringUtils.leftPad;

public class MedalsPicture {

    private final static Font gameFont = new Font("Verdana", BOLD, 11);
    private final static Font scoreFont = new Font("Verdana", BOLD, 14);

    public static byte[] createMedalsPicture(int firstRankCount, int secondRankCount, int thirdRankCount, int oneCreditCount, int gameCount) {
        try {
            BufferedImage bi = ImageIO.read(new File("public/images/medailles.png"));
            Graphics2D graphics = bi.createGraphics();
            graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
            int space = 3;
            draw(graphics, firstRankCount, 25 + space);
            draw(graphics, secondRankCount, 60 + space);
            draw(graphics, thirdRankCount, 95 + space);
            draw(graphics, oneCreditCount, 140 + space);
            drawBelow(graphics, gameCount + " jeux scorés");
            return Images.toBytes(bi);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void draw(Graphics2D graphics, Integer count, int i) {
        graphics.setColor(RankingGameConfiguration.COLOR_SHMUP_TEXT);
        graphics.setFont(gameFont);
        graphics.drawString(pad(count), i, 25);
    }

    private static void drawBelow(Graphics2D graphics, String text) {
        graphics.setColor(RankingGameConfiguration.COLOR_SCORE_TEXT);
        graphics.setFont(scoreFont);
        graphics.drawString(text, 10, 45);
    }

    private static String pad(Integer value) {
        return leftPad(value.toString(), 2);
    }

    public static byte[] blankImage;

    static {
        try {
            blankImage = Images.toBytes(new BufferedImage(1, 1, TYPE_INT_ARGB));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
