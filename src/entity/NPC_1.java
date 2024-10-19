package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_1 extends Entity{




    public NPC_1(GamePanel gp){

        super(gp);

        direction = "down";
        speed = 1;
        solidArea.x = 20; //commence au n'e pixel du sprite à gauche
        solidArea.y = 16;   //commence au 16 pixel
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gp.tileSize - 2*solidArea.x;
        solidArea.height = gp.tileSize - solidArea.y;


        loadAllSpriteSheets();
        setDialogue();
    }

    public void loadAllSpriteSheets() {

        upSpriteSheet = setup("/resources/npc/char2_down_walk", 8);
        downSpriteSheet = setup("/resources/npc/char2_down_walk", 8);
        leftSpriteSheet = setup("/resources/npc/char2_down_walk", 8);
        rightSpriteSheet = setup("/resources/npc/char2_down_walk",8);

        // Découper les frames depuis chaque sprite sheet
        upFrames = extractFrames(upSpriteSheet, 8);
        downFrames = extractFrames(downSpriteSheet, 8);
        leftFrames = extractFrames(leftSpriteSheet, 8);
        rightFrames = extractFrames(rightSpriteSheet, 8);

    }

    public void setDialogue(){

        dialogues[0] = "Hello, lad.";
        dialogues[1] = "I'm a prostitute and you?.";
        dialogues[2] = "Zob Humide. Zob Humide. Zob Humide. \nZob Humide. Zob Humide. Zob Humide. \nZob Humide. Zob Humide.";
        dialogues[3] = "Mange mon vié.";

    }
    public void setAction(){

        actionLockCounter++;
        if(actionLockCounter >= 100){

            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if(i <= 25){
                direction = "up";
            }
            if(i > 25 && i <= 50){
                direction = "down";
            }
            if(i > 50 && i <= 75){
                direction = "left";
            }
            if(i > 75){
                direction = "right";
            }
            actionLockCounter = 0;
        }


    }
    public void speak(){//non nécéssaire mais permet de customiser la classe speak quand il le faudra

        super.speak();//on appele la méthode speak de la classe entity

    }

}

