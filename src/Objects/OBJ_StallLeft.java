package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_StallLeft extends SuperObject {
    public OBJ_StallLeft () {
        name = "StallLeft";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/stall_left.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;

        solidArea = new Rectangle(12, 64, 104, 56);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}