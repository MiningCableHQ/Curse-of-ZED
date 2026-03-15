package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_RedHouse extends SuperObject {
    public OBJ_RedHouse() {
        name = "RedHouse";
        try {
            // Path matches your sidebar: /map2assets/tree_64x96.png
            image = ImageIO.read(getClass().getResourceAsStream("/map1assets/redhouse_128x128.png"));
        } catch (IOException e) {
            System.out.println("Could not find the redhouse image!");
            e.printStackTrace();
        }

        collision = true;

        int scale = 2; // Or gp.scale if you pass it in
        solidArea = new Rectangle(16 * scale, 64 * scale, 32 * scale, 32 * scale);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }
}