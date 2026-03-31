package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_Tower extends SuperObject {
    public OBJ_Tower () {
        name = "Tower";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/tower_96x160.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;


        this.solidArea = new Rectangle(20, 40, 152, 260);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}