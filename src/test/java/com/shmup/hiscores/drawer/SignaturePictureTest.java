package com.shmup.hiscores.drawer;

import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
public class SignaturePictureTest {

    private static final boolean SAVE_SNAPSHOTS = false;

    private final SignaturePicture signaturePicture = new SignaturePicture();
    private final Images images = new Images();

    @Test
    public void should_create_signature_picture() throws IOException {
        BufferedImage actual = signaturePicture.createSignaturePicture(player());
        File expected = new File("src/test/resources/signature.png");
        if (SAVE_SNAPSHOTS) {
            Images.saveSnasphot(actual, expected);
        }
        assertThat(Images.compareImages(actual, ImageIO.read(expected))).isTrue();
    }

    private Player player() {
        Player player = new Player("Player1");
        Score score = new Score();
        score.setCreatedAt(new Date(0));
        score.setValue(BigDecimal.valueOf(123456));
        score.setRank(5);
        Game game = new Game();
        game.setTitle("Gradius");
        score.setGame(game);
        player.setScores(List.of(score));
        return player;
    }

}
