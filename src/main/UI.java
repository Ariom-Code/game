package main;

import entity.Player;
import object.OBJ_Key;

import javax.imageio.spi.ImageInputStreamSpi;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Player player;
    Graphics2D g2;
    Font arial_40, arial_80b;
    //BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp, Player player){
        this.gp = gp;
        this.player = player;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80b = new Font("Arial", Font.BOLD, 80);

        //OBJ_Key key = new OBJ_Key(gp);
        //keyImage = key.image; //récuperer l'image de la classe
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState){
            //later
        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }


    }
    public void drawPauseScreen(){

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
            String text = "PAUSED";
            int x = getXForCenter(text);
            int y = gp.screenHeight/2;

            g2.drawString(text, x, y);
    }

    public int getXForCenter(String text){

        int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - lenght/2;

        return x;
    }
}
