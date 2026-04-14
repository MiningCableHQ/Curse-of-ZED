package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class OBJ_BlueFlower extends SuperObject {
    public OBJ_BlueFlower() {
        name = "BlueFlower";
        try {

            image = ImageIO.read(getClass().getResourceAsStream("/map1assets/blueflower_32x32.png"));
        } catch (IOException e) {
            System.out.println("Could not find the blueflower image!");
            e.printStackTrace();
        }

        collision = true;

        int scale = 2; // Or gp.scale if you pass it in
        solidArea = new Rectangle(1 * scale, 1 * scale, 24 * scale, 24 * scale);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }
}

