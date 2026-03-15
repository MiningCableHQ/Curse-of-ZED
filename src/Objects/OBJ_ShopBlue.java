package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_ShopBlue extends SuperObject {
    public OBJ_ShopBlue() {
        name = "ShopBlue";
        try {
            // Path matches your sidebar: /map2assets/tree_64x96.png
            image = ImageIO.read(getClass().getResourceAsStream("/map1assets/shopblue_128x128.png"));
        } catch (IOException e) {
            System.out.println("Could not find the shopblue image!");
            e.printStackTrace();
        }

        collision = true;

        int scale = 2; // Or gp.scale if you pass it in
        solidArea = new Rectangle(16 * scale, 64 * scale, 32 * scale, 32 * scale);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }
}