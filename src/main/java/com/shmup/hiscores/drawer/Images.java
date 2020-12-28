package com.shmup.hiscores.drawer;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Component
public class Images {

    @Deprecated
    public static byte[] toBytes(BufferedImage image) throws IOException {
        byte[] bytes;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", stream);
        stream.flush();
        bytes = stream.toByteArray();
        stream.close();
        return bytes;
    }

    public byte[] toBytesC(BufferedImage image) throws IOException {
        return Images.toBytes(image);
    }

    public static boolean compareImages(BufferedImage originalScreenshot, BufferedImage currentScreenshot) throws IOException {
        byte[] imageInByteOriginal = Images.toBytes(originalScreenshot);
        byte[] imageInByteCurrent = Images.toBytes(currentScreenshot);
        return Arrays.equals(imageInByteOriginal, imageInByteCurrent);
    }

    public static void saveSnasphot(BufferedImage actual, File expected) throws IOException {
        ImageIO.write(actual, "png", expected);
    }

}
