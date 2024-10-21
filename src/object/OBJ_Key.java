package object;

import entity.Entity;
import jdk.jshell.execution.Util;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp){

        super(gp);

        name = "Key";

        downSpriteSheet = UtilityTool.setup("/resources/objects/key", 1, gp.tileSize);
        downFrames = UtilityTool.extractFrames(downSpriteSheet, 1, gp.tileSize);

        spriteNumber = 0;
    }
}
