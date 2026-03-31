package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_VillageSide extends SuperObject {
    public OBJ_VillageSide () {
        name = "VillageSide";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/village_house_left.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;

        solidArea = new Rectangle(12, 64, 104, 60);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}