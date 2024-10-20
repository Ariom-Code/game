package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UtilityTool {

    GamePanel gp;

    public BufferedImage scaleImage(BufferedImage original, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null); //dessine l'image sur une image vide, puis enregistre dans scaledImage
        g2.dispose();

        return scaledImage;
    }

    public static BufferedImage setup(String imagePath, int imageNumber, int tileSize) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(UtilityTool.class.getResourceAsStream(imagePath + ".png"));
            if (image == null) {
                throw new IOException("Image not found at path: " + imagePath);
            }
            UtilityTool uTool = new UtilityTool();
            image = uTool.scaleImage(image, tileSize * imageNumber, tileSize);

        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        return image;
    }

    // Méthode pour extraire des frames depuis un sprite sheet
    public static BufferedImage[] extractFrames(BufferedImage spriteSheet, int frameCount, int tileSize) {
        int frameWidth = tileSize;
        int frameHeight = tileSize;
        BufferedImage[] frames = new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }

        return frames;
    }

}
