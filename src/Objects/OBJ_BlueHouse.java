package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_BlueHouse extends SuperObject {
    public OBJ_BlueHouse() {
        name = "BlueHouse";
        try {
            // Path matches your sidebar: /map2assets/tree_64x96.png
            image = ImageIO.read(getClass().getResourceAsStream("/map1assets/bluehouse_128x128.png"));
        } catch (IOException e) {
            System.out.println("Could not find the bluehouse image!");
            e.printStackTrace();
        }

        collision = true;

        int scale = 2; // Or gp.scale if you pass it in
        solidArea = new Rectangle(16 * scale, 64 * scale, 32 * scale, 32 * scale);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }
}