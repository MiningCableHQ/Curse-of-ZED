package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_SmallRock extends SuperObject {
    public OBJ_SmallRock() {
        name = "SmallRock";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/rocks_small_64x96.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;

        this.solidArea = new Rectangle(16, 80, 96, 112);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}