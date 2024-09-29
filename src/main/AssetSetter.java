package main;

import object.OBJ_Key;

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

        gp.obj[2] = new OBJ_Key();
        gp.obj[2].worldX = 30 * gp.tileSize;
        gp.obj[2].worldY = 20 * gp.tileSize;
    }
}
