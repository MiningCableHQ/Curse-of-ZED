package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_Stump extends SuperObject {
    public OBJ_Stump () {
        name = "Stump";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/tree_stump.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;

        solidArea = new Rectangle(6, 12, 36, 16);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}