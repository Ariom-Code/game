package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow){

            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 30;
            eventRect[col][row].y = 30;
            eventRect[col][row].width = 4;
            eventRect[col][row].height = 4;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }

    }

    public void checkEvent(){

        //check if the player is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance); //prend la val lap plus grande des 2
        if (distance > gp.tileSize){
            canTouchEvent = true;
        }

        if(canTouchEvent == true){
            if(hit(27, 17, "any") == true){damagePit(27, 17, gp.dialogueState);}
            if(hit(3, 2, "any") == true){damagePitRepeat(gp.dialogueState);}
        }

        if(hit(27, 23, "any") == true){healingPool(gp.dialogueState);}

    }


    public boolean hit(int col, int row, String reqDirection){

        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false){
            if(gp.player.direction.equals(reqDirection) || reqDirection.equals("any")){
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;

            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void damagePit(int col, int row, int gameState){

        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit!";
        gp.player.life -= 1;

        eventRect[col][row].eventDone = true; //unique
    }

    public void damagePitRepeat(int gameState){

        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit pd!";
        gp.player.life -= 1;

        canTouchEvent = false; //se réactive quand le joueur s'eloigne
    }

    public void healingPool(int gameState){

        if(gp.keyH.enterPressed == true){
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drink the water!\nYour life has been recovered.";
            gp.player.life = gp.player.maxLife;

            canTouchEvent = false;
        }
    }

}
