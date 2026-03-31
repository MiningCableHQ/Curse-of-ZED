package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;


public class OBJ_Stall extends SuperObject {
    public OBJ_Stall () {
        name = "Stall";
        try {
            // Make sure the filename matches your sidebar exactly!
            image = ImageIO.read(getClass().getResourceAsStream("/map2assets/stall_64x64.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;

        this.solidArea = new Rectangle(8, 50, 112, 74);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}