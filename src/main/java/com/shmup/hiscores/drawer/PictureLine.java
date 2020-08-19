package com.shmup.hiscores.drawer;

import java.awt.*;

@Deprecated
interface PictureLine {
    void draw(Graphics2D graphics, int y, RankingGameConfiguration rankingGameConfiguration);
}