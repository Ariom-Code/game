package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key_Sad extends SuperObject{

    GamePanel gp;

    public OBJ_Key_Sad(GamePanel gp){

        this.gp = gp;
        name = "Key_blue";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/resources/objects/key_sad.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
