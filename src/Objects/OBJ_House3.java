package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_House3 extends SuperObject {
    public OBJ_House3() {
        name = "House3";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/house_gold_128x128.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;

        solidArea = new Rectangle(2, 2, 12, 12);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}