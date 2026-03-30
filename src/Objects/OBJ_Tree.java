package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_Tree extends SuperObject {
    public OBJ_Tree() {
        name = "Tree";
        try {
            // Path matches your sidebar: /map2assets/tree_64x96.png
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/tree_nobg2_70x100.png"));
        } catch (IOException e) {
            System.out.println("Could not find the tree image!");
            e.printStackTrace();
        }

        collision = true;

        int scale = 2; // Or gp.scale if you pass it in
        solidArea = new Rectangle();
        solidArea.x = 25 * scale;      // Center it
        solidArea.y = 75 * scale;      // Start near the bottom of the tree
        solidArea.width = 20 * scale;  // Width of the trunk
        solidArea.height = 20 * scale; // Height of the base/roots

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}