package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_ShopRed extends SuperObject {
    public OBJ_ShopRed() {
        name = "ShopRed";
        try {
            // Path matches your sidebar: /map2assets/tree_64x96.png
            image = ImageIO.read(getClass().getResourceAsStream("/map1assets/shopred_128x128.png"));
        } catch (IOException e) {
            System.out.println("Could not find the tree image!");
            e.printStackTrace();
        }

        collision = true;

        int scale = 2; // Or gp.scale if you pass it in
        solidArea = new Rectangle(16 * scale, 16 * scale, 96 * scale, 96 * scale);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }
}