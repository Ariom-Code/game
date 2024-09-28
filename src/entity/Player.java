package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues(); //définit les variables de la class entity
        getPlayerImage();
    }
    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/boy_right_2.png"));


        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void update() {

        if(keyH.upPressed == true || keyH.downPressed == true || keyH.rightPressed == true || keyH.leftPressed == true) {
            if(keyH.upPressed == true) {
                direction = "up";
                y -= speed;
            }
            if (keyH.downPressed == true) {
                direction = "down";
                y += speed;
            }
            if (keyH.leftPressed == true) {
                direction = "left";
                x -= speed;
            }
            if (keyH.rightPressed == true) {
                direction = "right";
                x += speed;
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
            x = 350;
            y = 250;
        }
    }
    public void draw(Graphics2D g2) {
        //g2.setColor(Color.white);
        //g2.fillRect(x,y, gp.tileSize, gp.tileSize); //access tilesize because it's public

        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNumber == 1){
                    image = up1;
                }
                if (spriteNumber == 2){
                    image = up2;
                }
                break;
            case "down":
                if (spriteNumber == 1){
                    image = down1;
                }
                if (spriteNumber == 2){
                    image = down2;
                }
                break;
            case "left":
                if (spriteNumber == 1){
                    image = left1;
                }
                if (spriteNumber == 2){
                    image = left2;
                }
                break;
            case "right":
                if (spriteNumber == 1){
                    image = right1;
                }
                if (spriteNumber == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
