package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_Trunk extends SuperObject {
    public OBJ_Trunk() {
        name = "Trunk";
        try {
            // Path matches your sidebar: /map2assets/tree_64x96.png
            image = ImageIO.read(getClass().getResourceAsStream("/map1assets/trunk_64x32.png"));
        } catch (IOException e) {
            System.out.println("Could not find the trunk image!");
            e.printStackTrace();
        }

        collision = true;

        int scale = 2; // Or gp.scale if you pass it in
        solidArea = new Rectangle(1 * scale, 1 * scale, 48 * scale, 24 * scale);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }
}
