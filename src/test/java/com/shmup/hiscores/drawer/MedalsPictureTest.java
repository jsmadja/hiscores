package com.shmup.hiscores.drawer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class MedalsPictureTest {

    private static final boolean SAVE_SNAPSHOTS = false;

    private final MedalsPicture medalsPicture = new MedalsPicture();

    @DisabledIfEnvironmentVariable(named = "CI", matches = "true")
    @Test
    public void should_create_medals_picture() throws IOException {
        BufferedImage actual = medalsPicture.createMedalsPicture(1, 2, 3, 4, 5);
        File expected = new File("src/test/resources/medals.png");
        if (SAVE_SNAPSHOTS) {
            Images.saveSnasphot(actual, expected);
        }
        assertThat(Images.compareImages(actual, ImageIO.read(expected))).isTrue();
    }

}
