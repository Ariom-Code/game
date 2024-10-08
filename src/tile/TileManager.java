package tile;

import main.GamePanel;
import main.MapGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class TileManager {
    GamePanel gp;


    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[10]; // number of tiles we need
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();

    }

    public void getTileImage() { //charge les tiles
        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/water.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/brick.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/sand.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/tree.png"));
            tile[4].collision = true;

        } catch (IOException e){
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

            //gp.player.worldX = position du joueur dans le monde
            //gp.player.screenX = ses coo affichage sur l'écran
            //maptileNum charge le type de tile des coordonnées de la tile
            //worldX = coordonnées de la tuile dans le monde
            //screenX est la ou la tuile va etre affichée

            int worldX = worldCol * gp.tileSize; //position on the map
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX; //where on the screen to draw it, the +screnx is to set the offset
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            //definit les limites du chargement du monde pour les perfs
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
            && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
            && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
            && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }

            worldCol++;


            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;

                worldRow++;

            }
        }

    }
}
