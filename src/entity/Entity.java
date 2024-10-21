package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public int speed;

    public BufferedImage image,  image2, image3;
    public BufferedImage imageSheet;
    public BufferedImage imageFrames[];

    public String name;
    public boolean collision = false;

    public BufferedImage upSpriteSheet, downSpriteSheet, leftSpriteSheet, rightSpriteSheet;
    public BufferedImage[] upFrames, downFrames, leftFrames, rightFrames;

    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNumber = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    String dialogues[] = new String[20];
    int dialogueIndex = 0;


    //CHARACTER STATUS
    public int maxLife;
    public int life;


    public Entity(GamePanel gp){ //abstract = pas d'instance
        this.gp = gp;
    }

    public void setAction(){}
    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction){
            case "up":
                direction= "down";
            case "down":
                direction= "up";
            case "left":
                direction= "right";
            case "right":
                direction= "left";
        }
    }
    public void update(){

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);

        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 10) { // Player image change every 10 frames
            spriteNumber = (spriteNumber + 1) % 8; // Passe de 0 à 7 pour les frames
            spriteCounter = 0;
        }


}
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX; //where on the screen to draw it, the +screnx is to set the offset
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                switch (direction) {

                    case "up":
                        image = upFrames[spriteNumber];
                        break;
                    case "down":
                        image = downFrames[spriteNumber];
                        break;
                    case "left":
                        image = leftFrames[spriteNumber];
                        break;
                    case "right":
                        image = rightFrames[spriteNumber];
                        break;
                }


            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            //HITBOX
            g2.setColor(Color.red);
            //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width , solidArea.height);

        }
    }

}
