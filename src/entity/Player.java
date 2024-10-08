package entity;

import main.GamePanel;
import main.KeyHandler;
import main.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{


    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int numKey = 0;
    public int numKeyBlue = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2- (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gp.tileSize - 16;
        solidArea.height = gp.tileSize - 16;


        setDefaultValues(); //définit les variables de la class entity
        getPlayerImage();
    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 22; //position sur la map au lancement = spawnpoint
        worldY = gp.tileSize * 18;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        try {

            down = ImageIO.read(getClass().getResourceAsStream("/resources/player/character1_down.png"));
            up = ImageIO.read(getClass().getResourceAsStream("/resources/player/character1_up.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/resources/player/character1_left.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/resources/player/character1_right.png"));

            up1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/character1_up_walk1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/character1_up_walk2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/character1_down_walk1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/character1_down_walk2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/character1_left.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/character1_left.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/character1_right.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/character1_right.png"));


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update() {

        if(keyH.upPressed == true || keyH.downPressed == true || keyH.rightPressed == true || keyH.leftPressed == true) {

            if(keyH.upPressed == true) {
                direction = "up";
            }
            if (keyH.downPressed == true) {
                direction = "down";
            }
            if (keyH.leftPressed == true) {
                direction = "left";
            }
            if (keyH.rightPressed == true) {
                direction = "right";
            }


            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true); //return index
            pickUpObject(objIndex);

            if(collisionOn == false) {

                switch (direction){
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
            if(spriteCounter > 12){ //player image change every 12 frames
                if(spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber ==2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }

        }

        if (keyH.spacePressed == true) {
            worldX = 350;
            worldY = 250;
        }
    }

    public void pickUpObject(int i){

        if(i != 999){

            String objectName = gp.obj[i].name;

            switch (objectName){

                case "Key":
                    gp.playSE(3);
                    numKey++;
                    gp.obj[i] = null; //delete object
                    gp.ui.showMessage("+1 Key!");
                    break;

                case "Key_blue":
                    gp.playSE(3);
                    numKeyBlue++;
                    gp.obj[i] = null; //delete object
                    gp.ui.showMessage("+1 Key!");
                    break;

                case "Door" :
                    if(numKey > 0){
                        gp.playSE(2);
                        gp.obj[i] = null;
                        numKey--;
                        gp.ui.showMessage("Door opened!");
                    }
                    else{
                        gp.ui.showMessage("You need a key!");
                    }
                    break;

                case "Chest" :
                    if(numKeyBlue > 0){
                        gp.obj[i] = null;
                        numKeyBlue--;
                        gp.ui.gameFinished = true;
                        //gp.stopMusic();
                        gp.playSE(2);
                    }
                    else{
                        gp.ui.showMessage("You need a key!");
                    }
                    break;

                case "Boots" :
                    speed += 1;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Speed up!");
                    break;
            }

        }
    }


    public void draw(Graphics2D g2) {
        //g2.setColor(Color.white);
        //g2.fillRect(x,y, gp.tileSize, gp.tileSize); //access tilesize because it's public
        BufferedImage image = null;
        if(keyH.upPressed == true || keyH.downPressed == true || keyH.rightPressed == true || keyH.leftPressed == true) {

            switch (direction) {
                case "up":
                    if (spriteNumber == 1) {
                        image = up1;
                    }
                    if (spriteNumber == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNumber == 1) {
                        image = down1;
                    }
                    if (spriteNumber == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNumber == 1) {
                        image = left1;
                    }
                    if (spriteNumber == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNumber == 1) {
                        image = right1;
                    }
                    if (spriteNumber == 2) {
                        image = right2;
                    }
                    break;

            }


        }else {

            switch (direction){
                case "up":
                    image = up;
                    break;
                case "down":
                    image = down;
                    break;
                case "left":
                    image = left;
                    break;
                case "right":
                    image = right;
                    break;
            }

        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);








    }
}
