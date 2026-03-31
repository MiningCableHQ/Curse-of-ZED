package Objects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;


public class OBJ_Torch extends SuperObject {

    public BufferedImage[] frames = new BufferedImage[3]; // Store 3 frames
    public int frameCounter = 0;
    public int spriteNum = 0;

    public OBJ_Torch() {
        name = "Torch";
        try {
            frames[0] = ImageIO.read(getClass().getResourceAsStream("/map2assets/torch_left_16x32.png"));
            frames[1] = ImageIO.read(getClass().getResourceAsStream("/map2assets/torch_16x32.png"));
            frames[2] = ImageIO.read(getClass().getResourceAsStream("/map2assets/torch_right_16x32.png"));

            // Start with the first frame
            image = frames[0];
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;

    this.solidArea = new Rectangle(10, 32, 12, 32);

    this.solidAreaDefaultX = solidArea.x;
    this.solidAreaDefaultY = solidArea.y;
    }
    // We will call this to animate the torch
    public void updateAnimation() {
        frameCounter++;
        if (frameCounter > 12) { // Adjust speed: lower number = faster flickering
            if (spriteNum == 0) spriteNum = 1;
            else if (spriteNum == 1) spriteNum = 2;
            else if (spriteNum == 2) spriteNum = 0;

            image = frames[spriteNum];
            frameCounter = 0;
        }
    }
}