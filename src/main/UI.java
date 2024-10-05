package main;

import entity.Player;
import object.OBJ_Key;

import javax.imageio.spi.ImageInputStreamSpi;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Font arial_40, arial_80b;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80b = new Font("Arial", Font.BOLD, 80);

        OBJ_Key key = new OBJ_Key();
        keyImage = key.image; //récuperer l'image de la classe
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){

        if(gameFinished == true){

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "You win!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //return la longueur du texte
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - gp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Your time is : " + dFormat.format(playTime) +"!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //return la longueur du texte
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + gp.tileSize*3;
            g2.drawString(text, x, y);

            g2.setFont(arial_80b);
            g2.setColor(Color.yellow);
            text = "Congratulations!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //return la longueur du texte
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + gp.tileSize*2;
            g2.drawString(text, x, y);

            gp.gameThread = null;

        }
        else{
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null); //division pour positionnement au milieu
            g2.drawString("x = " + gp.player.numKey , 90, 85); //fonctionne différement: y correspond aux coo du bas du texte et non du haut

            //TIME
            playTime += (double) 1/60;
            g2.drawString("Time : " + dFormat.format(playTime), gp.tileSize*9, 65);


            //DRAW MESSAGE
            if(messageOn ==true){

                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize/2, gp.tileSize*5);

                messageCounter ++;

                if(messageCounter > 120){ //2seconds
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
