package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_Water_Sand extends SuperObject {
    public OBJ_Water_Sand() {
        name = "WaterSand";
        try {
            // Path matches your sidebar: /map2assets/tree_64x96.png
            image = ImageIO.read(getClass().getResourceAsStream("/map1tiles/water_sand_32x32.png"));
        } catch (IOException e) {
            System.out.println("Could not find the map1fence image!");
            e.printStackTrace();
        }

        collision = true;

        int scale = 1; // Or gp.scale if you pass it in
        solidArea = new Rectangle(1 * scale, 1 * scale, 24 * scale, 24 * scale);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }
}