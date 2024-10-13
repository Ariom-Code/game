package tile;

import jdk.jshell.execution.Util;
import main.GamePanel;
import main.MapGenerator;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TileManager {
    GamePanel gp;


    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[20]; // number of tiles we need
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();

    }

    public void getTileImage() { //charge les tiles

            setup(0, "grass", false);
            setup(1, "water", true);
            setup(2, "brick", true);
            setup(3, "sand", false);
            setup(4, "tree", true);

        setup(5, "water_top", true);
        setup(6, "water_bottom", true);
        setup(7, "water_left", true);
        setup(8, "water_right", true);
        setup(9, "water_corner_tl", true);
        setup(10, "water_corner_tr", true);
        setup(11, "water_corner_bl", true);
        setup(12, "water_corner_br", true);

        setup(13, "water_corner_in_tl", true);
        setup(14, "water_corner_in_tr", true);
        setup(15, "water_corner_in_bl", true);
        setup(16, "water_corner_in_br", true);

    }

    private int getWaterTileType(int col, int row) {
        boolean top = (row > 0 && mapTileNum[col][row - 1] == 0); // 0 représente la terre
        boolean bottom = (row < gp.maxWorldRow - 1 && mapTileNum[col][row + 1] == 0);
        boolean left = (col > 0 && mapTileNum[col - 1][row] == 0);
        boolean right = (col < gp.maxWorldCol - 1 && mapTileNum[col + 1][row] == 0);

        boolean topLeft = (row > 0 && mapTileNum[col - 1][row - 1] == 0); // 0 représente la terre
        boolean topRight = (row < gp.maxWorldRow - 1 && mapTileNum[col + 1][row - 1] == 0);
        boolean bottomLeft = (col > 0 && mapTileNum[col - 1][row + 1] == 0);
        boolean bottomRight = (col < gp.maxWorldCol - 1 && mapTileNum[col + 1][row + 1] == 0);


        if (top && left) return 9;  // water_corner_tl
        if (top && right) return 10; // water_corner_tr
        if (bottom && left) return 11; // water_corner_bl
        if (bottom && right) return 12; // water_corner_br
        if (top) return 5; // water_top
        if (bottom) return 6; // water_bottom
        if (left) return 7; // water_left
        if (right) return 8; // water_right

        if (!top && !right && !left && !bottom && bottomLeft) return 15;
        if (!top && !right && !left && !bottom && topRight) return 14;
        if (!top && !right && !left && !bottom && topLeft) return 13;
        if (!top && !right && !left && !bottom && bottomRight) return 16;


        return 1; // Eau normale
    }




    public void setup(int index, String imageName, boolean collision){

        UtilityTool uTool = new UtilityTool();

        try{

            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath){
    //scan la map mais ne l'affiche pas

        try {
            //InputStream is = getClass().getResourceAsStream(filePath); //import text file
            FileReader fileReader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fileReader); //read the content

            int col = 0;
            int row = 0;
            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int number = Integer.parseInt(numbers[col]); //string to integer

                    mapTileNum[col] [row] = number;
                    col++;
                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch(Exception e) {
            e.printStackTrace();
        }


    }
    public void draw(Graphics2D g2) {
        //g2.drawImage(tile[0].image, 0,0, gp.tileSize, gp.tileSize, null);

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow]; //sort la valeur 0, 1, 2 etc de la tile

            //-----------INFO----------------
            /*gp.player.worldX = position du joueur dans le monde
            gp.player.screenX = ses coo affichage sur l'écran
            maptileNum charge le type de tile des coordonnées de la tile
            worldX = coordonnées de la tuile dans le monde
            screenX est la ou la tuile va etre affichée*/

            int worldX = worldCol * gp.tileSize; //position on the map
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX; //where on the screen to draw it, the +screnx is to set the offset
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            //definit les limites du chargement du monde pour les perfs
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
            && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
            && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
            && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                // Déterminer si la tuile est de l'eau et ajuster son type si nécessaire
                if (tileNum == 1) { // Si c'est une tuile d'eau
                    tileNum = getWaterTileType(worldCol, worldRow);
                }
                g2.drawImage(tile[tileNum].image, screenX, screenY,null);

            }

            worldCol++;


            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;

                worldRow++;

            }
        }

    }
}
