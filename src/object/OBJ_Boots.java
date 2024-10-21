package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends Entity {

    public OBJ_Boots(GamePanel gp){
        super(gp);

        name = "Boots";

        // Utilisation de UtilityTool pour charger et redimensionner l'image
        imageSheet = UtilityTool.setup("/resources/objects/boots", 1, gp.tileSize);
        imageFrames = UtilityTool.extractFrames(imageSheet, 1, gp.tileSize);

        spriteNumber = 0;
    }
}
