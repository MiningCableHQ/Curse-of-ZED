package Objects; 

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_SmallFish extends SuperObject {
    public OBJ_SmallFish () {
        name = "SmallFish";
        try {
            // Ensure this folder exists in your resources!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/dead_fish.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;

        solidArea = new Rectangle(12, 10, 40, 12);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}