package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_House1 extends SuperObject {
    public OBJ_House1 () {
        name = "House1 ";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/house_mysterious_128x128.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;

        this.solidArea = new Rectangle(12, 40, 232, 210);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}