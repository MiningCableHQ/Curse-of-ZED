package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_WoodFence extends SuperObject {
    public OBJ_WoodFence () {
        name = "WoodFence";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/woodfence.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;

        this.solidArea = new Rectangle(0, 52, 64, 8);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}