package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key_Sad extends SuperObject {

    public OBJ_Key_Sad(){
        name = "Key_sad";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/resources/objects/key_sad.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
