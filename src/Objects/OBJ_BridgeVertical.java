package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_BridgeVertical extends SuperObject {
    public OBJ_BridgeVertical () {
        name = "BridgeVertical";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/woodbridgever.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = false;

        solidArea = new Rectangle(0, 0, 0, 0);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}