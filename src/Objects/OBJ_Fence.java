package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_Fence extends SuperObject {
    public OBJ_Fence () {
        name = "Fence";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/fence_50x50.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;

        this.solidArea = new Rectangle(0, 60, 100, 40);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}