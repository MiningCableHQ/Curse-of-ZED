package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_Green extends SuperObject {
    public OBJ_Green  () {
        name = "Green";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/green_aura_192x192.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = false;

        this.solidArea = new Rectangle(20, 65, 60, 30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}