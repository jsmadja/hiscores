package com.shmup.hiscores.drawer;

import java.awt.*;

import static com.shmup.hiscores.drawer.RankingGameConfiguration.*;

@Deprecated
public class BreakLine implements PictureLine {

    @Override
    public void draw(Graphics2D graphics, int y, RankingGameConfiguration rankingGameConfiguration) {
        graphics.setColor(COLOR_SHMUP_GREY);
        graphics.fillRect(0, y - fontHeight, width, y);
        graphics.drawString("", 0, y);
    }
}