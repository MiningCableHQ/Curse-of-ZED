package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_TreeOrange extends SuperObject {
    public OBJ_TreeOrange() {
        name = "TreeOrange";
        try {
            // Path matches your sidebar: /map2assets/tree_64x96.png
            image = ImageIO.read(getClass().getResourceAsStream("/map1assets/treeorange_64x96.png"));
        } catch (IOException e) {
            System.out.println("Could not find the treeorange image!");
            e.printStackTrace();
        }

        collision = true;

        int scale = 2; // Or gp.scale if you pass it in
        solidArea = new Rectangle(8 * scale, 12 * scale, 48 * scale, 72 * scale);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }
}