package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_Broken extends SuperObject {
    public OBJ_Broken() {
        name = "Broken";
        try {
            // Path matches your sidebar: /map2assets/tree_64x96.png
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/bridge_64x96.png"));
        } catch (IOException e) {
            System.out.println("Could not find the tree image!");
            e.printStackTrace();
        }

        collision = true;

        solidArea = new Rectangle(8, 0, 112, 192);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }
}