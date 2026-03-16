package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_SmallGreyRock extends SuperObject {
    public OBJ_SmallGreyRock () {
        name = "GreyRock";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/rocks_16x16.png"));
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