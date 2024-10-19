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
    public String currentDialogue;


    //DecimalFormat dFormat = new DecimalFormat("#0.00");

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

        //PLAY STATE
        if(gp.gameState == gp.playState){
            //later
        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        //DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }

    }
    public void drawPauseScreen(){

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
            String text = "PAUSED";
            int x = getXForCenter(text);
            int y = gp.screenHeight/2;

            g2.drawString(text, x, y);
    }

    public void drawDialogueScreen(){
        //WINDOW
        int x = gp.tileSize;
        int y = gp.tileSize/4;
        int width = gp.screenWidth - (gp.tileSize*2);
        int height = gp.tileSize * 3;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize/2;
        y += 40;

        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }

    }
    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0,0,0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width, height, 35,35); //bords arrondis

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));//définit l'épaisseur des bords des éléments de g2
        g2.drawRoundRect(x+5,y+5,width-10, height-10, 22,22);

    }

    public int getXForCenter(String text){

        int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - lenght/2;

        return x;
    }
}
