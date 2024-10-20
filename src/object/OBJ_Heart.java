package object;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Heart extends SuperObject{


    GamePanel gp;

    public OBJ_Heart(GamePanel gp){

        this.gp = gp;

        name = "Heart";

        // Utilisation de UtilityTool pour charger et redimensionner l'image
        imageSheet = UtilityTool.setup("/resources/objects/heart", 3, gp.tileSize);
        imageFrames = UtilityTool.extractFrames(imageSheet, 3, gp.tileSize);

        image = imageFrames[0];
        image2 = imageFrames[1];
        image3 = imageFrames[2];
    }
}
