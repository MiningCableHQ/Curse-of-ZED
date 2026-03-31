package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_Stone extends SuperObject {
    public OBJ_Stone () {
        name = "Stone";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/square_stone_tile_30x30.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = false;

        solidArea = new Rectangle(4, 4, 52, 52);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}