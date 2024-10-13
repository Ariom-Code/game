package main;

import entity.NPC_1;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;


    }

    public void setObjects() {


        /*
        gp.obj[6] = new OBJ_Boots(gp);
        gp.obj[6].worldX = 20 * gp.tileSize;
        gp.obj[6].worldY = 16 * gp.tileSize;*/
    }

    public void setNpc(){

        gp.npc[0] = new NPC_1(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
    }
}
