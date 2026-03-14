package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_BigRock extends SuperObject {
    public OBJ_BigRock() {
        name = "BigRock";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/rocks_64x96.png"));
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