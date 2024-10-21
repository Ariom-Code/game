package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends Entity {


    public OBJ_Door(GamePanel gp){
        super(gp);

        name = "Door";

        // Utilisation de UtilityTool pour charger et redimensionner l'image
        downSpriteSheet = UtilityTool.setup("/resources/objects/door", 1, gp.tileSize);
        downFrames = UtilityTool.extractFrames(downSpriteSheet, 1, gp.tileSize);

        spriteNumber = 0;

        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize - 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
