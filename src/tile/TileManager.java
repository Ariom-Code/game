package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[10]; // number of tiles we need
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadMap("/resources/maps/map.txt");
    }

    public void getTileImage() { //charge les tiles
        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/water.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/brick.png"));


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
    //scan la map mais ne l'affiche pas

        try {
            InputStream is = getClass().getResourceAsStream(filePath); //import text file
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); //read the content

            int col = 0;
            int row = 0;
            while(col < gp.maxScreenCol && row < gp.maxScreenRow) {

                String line = br.readLine();

                while (col < gp.maxScreenCol) {
                    String numbers[] = line.split(" ");

                    int number = Integer.parseInt(numbers[col]); //string to integer

                    mapTileNum[col] [row] = number;
                    col++;
                }
                if(col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch(Exception e) {
        }


    }
    public void draw(Graphics2D g2) {
        //g2.drawImage(tile[0].image, 0,0, gp.tileSize, gp.tileSize, null);

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){

            int tileNum = mapTileNum[col][row]; //sort la valeur 0, 1, 2 etc de la tile

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if(col == gp.maxScreenCol) {
                col = 0;
                x= 0 ;
                row++;
                y += gp.tileSize;
            }
        }

    }
}
