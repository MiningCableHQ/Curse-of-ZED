package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_Crops extends SuperObject {
    public OBJ_Crops() {
        name = "Crops";
        try {
            // Path matches your sidebar: /map2assets/tree_64x96.png
            image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/crops_32x32.png"));
        } catch (IOException e) {
            System.out.println("Could not find the crops image!");
            e.printStackTrace();
        }

        collision = true;

        int scale = 2; // Or gp.scale if you pass it in
        solidArea = new Rectangle(1 * scale, 1 * scale, 24 * scale, 24 * scale);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }
}