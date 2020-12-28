package com.shmup.hiscores.drawer;

import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Versus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
public class VersusPictureTest {

    private static final boolean SAVE_SNAPSHOTS = false;
    private final VersusPicture versusPicture = new VersusPicture();

    @Test
    public void should_create_versus_picture() throws IOException {
        Versus versus = new Versus(player1(), player1());
        BufferedImage actual = versusPicture.createVersusPicture(player1(), versus);
        File expected = new File("src/test/resources/versus.png");
        if (SAVE_SNAPSHOTS) {
            Images.saveSnasphot(actual, expected);
        }
        assertThat(Images.compareImages(actual, ImageIO.read(expected))).isTrue();
    }

    private Player player1() {
        Player player = new Player("Player1");
        Game game = new Game();
        game.setTitle("Gradius");
        return player;
    }

}
