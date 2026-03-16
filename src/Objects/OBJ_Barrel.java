package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_Barrel extends SuperObject {
    public OBJ_Barrel () {
        name = "Barrel";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/barrel_32x32.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;

        // Since rocks are usually shorter, the hitbox can cover almost the whole tile
        this.solidArea = new Rectangle(8, 32, 48, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}