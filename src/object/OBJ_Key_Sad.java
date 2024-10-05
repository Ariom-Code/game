package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key_Sad extends OBJ_Key{

    public OBJ_Key_Sad(){
        name = "Key_blue";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/resources/objects/key_sad.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
