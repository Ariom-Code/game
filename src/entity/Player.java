package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    //public int numKey = 0;
    int standCounter = 0;

    // Tableaux pour stocker les frames d'animation
    //private BufferedImage[] upFrames, downFrames, leftFrames, rightFrames;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);//appeler le constructeur de la superclass

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 20; //commence au n'e pixel du sprite à gauche
        solidArea.y = 16;   //commence au 16 pixel
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gp.tileSize - 2*solidArea.x;
        solidArea.height = gp.tileSize - solidArea.y;

        setDefaultValues(); // Définit les variables de la classe Entity
        loadAllSpriteSheets(); // Charge les frames depuis le sprite sheet
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 22; // Position sur la map au lancement = spawnpoint
        worldY = gp.tileSize * 18;
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        maxLife = 6;
        life = maxLife;
    }

    public void loadAllSpriteSheets() {

            // Charger chaque sprite sheet pour chaque direction
            //BufferedImage upSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/resources/player/char1_down_walk.png"));
            //BufferedImage downSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/resources/player/char1_down_walk.png"));
            //BufferedImage leftSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/resources/player/char1_down_walk.png"));
            //BufferedImage rightSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/resources/player/char1_down_walk.png"));

        upSpriteSheet = UtilityTool.setup("/resources/player/char1_down_walk", 8, gp.tileSize);
        downSpriteSheet = UtilityTool.setup("/resources/player/char1_down_walk", 8, gp.tileSize);
        leftSpriteSheet = UtilityTool.setup("/resources/player/char1_down_walk", 8, gp.tileSize);
        rightSpriteSheet = UtilityTool.setup("/resources/player/char1_down_walk",8, gp.tileSize);

        // Découper les frames depuis chaque sprite sheet
        upFrames = UtilityTool.extractFrames(upSpriteSheet, 8, gp.tileSize);
        downFrames = UtilityTool.extractFrames(downSpriteSheet, 8, gp.tileSize);
        leftFrames = UtilityTool.extractFrames(leftSpriteSheet, 8, gp.tileSize);
        rightFrames = UtilityTool.extractFrames(rightSpriteSheet, 8, gp.tileSize);

    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {

            if (keyH.upPressed) {
                direction = "up";
            }
            if (keyH.downPressed) {
                direction = "down";
            }
            if (keyH.leftPressed) {
                direction = "left";
            }
            if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true); // Return index
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc); // Return index
            interactNPC(npcIndex);

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
        }else spriteCounter++;

        if (keyH.spacePressed) {
            speed = 7;

        }else speed = 4;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Sélection de la bonne frame selon la direction et la frame actuelle
        if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {
            standCounter = 0;
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
        } else {
            // Utiliser la première frame par défaut quand le joueur ne bouge pas
            standCounter++;
            if(standCounter < 10){


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

            }else {
                switch (direction) {
                    case "up":
                        image = upFrames[3];
                        break;
                    case "down":
                        image = downFrames[3];
                        break;
                    case "left":
                        image = leftFrames[3];
                        break;
                    case "right":
                        image = rightFrames[3];
                        break;

                }
            }

        }

        g2.drawImage(image, screenX, screenY, null);

        //HITBOX
        g2.setColor(Color.red);
        //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width , solidArea.height);

    }


    public void pickUpObject(int i){

        if(i != 999){


        }
    }

    public void interactNPC(int i){
        if(i != 999){

            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();

            }
        }
        gp.keyH.enterPressed = false;
    }
}




/*
//pickup items : --------------------------------------
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

 */
