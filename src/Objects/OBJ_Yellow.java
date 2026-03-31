package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_Yellow extends SuperObject {
    public OBJ_Yellow  () {
        name = "Yellow";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/torch_glow_192x192.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = false;

        this.solidArea = new Rectangle(20, 65, 60, 30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}