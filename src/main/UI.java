package main;

import entity.Player;
import object.OBJ_Heart;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    GamePanel gp;
    Player player;
    Graphics2D g2;
    Font Noto_40;
    //BufferedImage keyImage;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue;
    public int commandNum = 0;
    public int titleScreenState = 0; //0 first screen, 1 second screen


    //DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp, Player player){
        this.gp = gp;
        this.player = player;

        try {
            InputStream is = getClass().getResourceAsStream("/resources/fonts/Noto.ttf");
            Noto_40 = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //OBJ_Key key = new OBJ_Key(gp);
        //keyImage = key.image; //récuperer l'image de la classe

        //HUD OBJECT
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){

        this.g2 = g2;

        g2.setFont(Noto_40);
        g2.setColor(Color.white);

        //TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        //PLAY STATE
        if(gp.gameState == gp.playState){
            drawPlayerLife();
        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
        //DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }

    }
    public void drawTitleScreen(){

        //background
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if(titleScreenState == 0){

            //title name
            g2.setFont(g2.getFont().deriveFont(96F));
            String text = "Zob Destructor";
            int x = getXForCenter(text);
            int y = gp.tileSize*2;

            g2.setColor(Color.darkGray);
            g2.drawString(text, x+5, y+5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            //image
            x = gp.screenWidth/2 - gp.tileSize;
            y += gp.tileSize/2;
            g2.drawImage(gp.player.rightFrames[3], x, y, gp.tileSize*2, gp.tileSize*2, null);

            //menu
            g2.setFont(g2.getFont().deriveFont(48F));

            text = "NEW GAME";
            x = getXForCenter(text);
            y += gp.tileSize*3;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-gp.tileSize/2, y);
            }

            text = "LOAD GAME";
            x = getXForCenter(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-gp.tileSize/2, y);
            }

            text = "QUIT";
            x = getXForCenter(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-gp.tileSize/2, y);
            }
        } else if (titleScreenState == 1) {

            //Class selection screen
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(60F));

            String text = "Select Your class";
            int x = getXForCenter(text);
            int y = gp.tileSize*2;
            g2.drawString(text, x,y);

            g2.setFont(g2.getFont().deriveFont(42F));
            text = "Fighter";
            x = getXForCenter(text);
            y += gp.tileSize;
            g2.drawString(text, x,y);
            if(commandNum == 0){
                g2.drawString(">", x-gp.tileSize/2, y);
            }

            text = "Thief";
            x = getXForCenter(text);
            y += gp.tileSize;
            g2.drawString(text, x,y);
            if(commandNum == 1){
                g2.drawString(">", x-gp.tileSize/2, y);
            }

            text = "Mage";
            x = getXForCenter(text);
            y += gp.tileSize;
            g2.drawString(text, x,y);
            if(commandNum == 2){
                g2.drawString(">", x-gp.tileSize/2, y);
            }

            text = "Cancel";
            x = getXForCenter(text);
            y += gp.tileSize*2;
            g2.drawString(text, x,y);
            if(commandNum == 3){
                g2.drawString(">", x-gp.tileSize/2, y);
            }


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

    public void drawPlayerLife(){

        gp.player.life= 20;

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        //draw max life
        while (i < gp.player.maxLife/2){ //Nombre de coeurs à afficher : 2 vies = un coeur
            g2.drawImage(heart_blank, x, y, gp.tileSize/2, gp.tileSize/2, null);
            i++;
            x += gp.tileSize/2;
        }

        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //draw current life
        while (i < gp.player.life){
            //draw un coeur a moitié
            g2.drawImage(heart_half, x, y, gp.tileSize/2, gp.tileSize/2, null);
            i++;
            //si le coeur a moitié peut etre encore rempli alors coeur plein
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, gp.tileSize/2, gp.tileSize/2, null);
            }
            i++;
            x+= gp.tileSize/2;
        }


    }

    public int getXForCenter(String text){

        int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - lenght/2;

        return x;
    }
}
