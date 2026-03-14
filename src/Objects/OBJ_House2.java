package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_House2 extends SuperObject {
    public OBJ_House2 () {
        name = "House2";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/house_green_128x128.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;

        // Since rocks are usually shorter, the hitbox can cover almost the whole tile
        solidArea = new Rectangle(4, 4, 24, 24);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}