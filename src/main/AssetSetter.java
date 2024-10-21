package main;

import entity.NPC_1;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;


    }

    public void setObjects() {

        gp.obj[0] = new OBJ_Door(gp);
        gp.obj[0].worldX = 20 * gp.tileSize;
        gp.obj[0].worldY = 14 * gp.tileSize;

        gp.obj[1] = new OBJ_Door(gp);
        gp.obj[1].worldX = 20 * gp.tileSize;
        gp.obj[1].worldY = 16 * gp.tileSize;

        gp.obj[2] = new OBJ_Chest(gp);
        gp.obj[2].worldX = 20 * gp.tileSize;
        gp.obj[2].worldY = 18 * gp.tileSize;

        gp.obj[3] = new OBJ_Key(gp);
        gp.obj[3].worldX = 20 * gp.tileSize;
        gp.obj[3].worldY = 20 * gp.tileSize;
    }

    public void setNpc(){

        gp.npc[0] = new NPC_1(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;

        gp.npc[1] = new NPC_1(gp);
        gp.npc[1].worldX = gp.tileSize*21;
        gp.npc[1].worldY = gp.tileSize*23;

        gp.npc[2] = new NPC_1(gp);
        gp.npc[2].worldX = gp.tileSize*21;
        gp.npc[2].worldY = gp.tileSize*22;

        gp.npc[3] = new NPC_1(gp);
        gp.npc[3].worldX = gp.tileSize*21;
        gp.npc[3].worldY = gp.tileSize*24;

        gp.npc[4] = new NPC_1(gp);
        gp.npc[4].worldX = gp.tileSize*21;
        gp.npc[4].worldY = gp.tileSize*25;

        gp.npc[5] = new NPC_1(gp);
        gp.npc[5].worldX = gp.tileSize*21;
        gp.npc[5].worldY = gp.tileSize*26;

        gp.npc[6] = new NPC_1(gp);
        gp.npc[6].worldX = gp.tileSize*21;
        gp.npc[6].worldY = gp.tileSize*27;

        gp.npc[7] = new NPC_1(gp);
        gp.npc[7].worldX = gp.tileSize*21;
        gp.npc[7].worldY = gp.tileSize*28;

    }
}
