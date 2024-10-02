package main;

import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Key_Sad;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;


    }

    public void setObjects() {

        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 19 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 25 * gp.tileSize;
        gp.obj[1].worldY = 20 * gp.tileSize;

        gp.obj[2] = new OBJ_Key_Sad();
        gp.obj[2].worldX = 30 * gp.tileSize;
        gp.obj[2].worldY = 20 * gp.tileSize;

        gp.obj[3] = new OBJ_Key_Sad();
        gp.obj[3].worldX = 30 * gp.tileSize;
        gp.obj[3].worldY = 30 * gp.tileSize;

        gp.obj[4] = new OBJ_Door();
        gp.obj[4].worldX = 40 * gp.tileSize;
        gp.obj[4].worldY = 18 * gp.tileSize;
        gp.obj[4].collision = true;

        gp.obj[5] = new OBJ_Chest();
        gp.obj[5].worldX = 40 * gp.tileSize;
        gp.obj[5].worldY = 16 * gp.tileSize;
    }
}
