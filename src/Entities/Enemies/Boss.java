package Entities.Enemies;

import java.awt.image.BufferedImage;

public abstract class Boss extends Enemy {
    protected BufferedImage[] idleFrames = new BufferedImage[5];
    protected int currentFrame = 0;
    protected int frameCounter = 0;
    protected static final int ANIMATION_SPEED = 2;

    public Boss() {
        super();
    }

    @Override
    public void loadSprite() {
        String bossName = getSpriteFolderName();
        for (int i = 0; i < 5; i++) {
            try {
                idleFrames[i] = javax.imageio.ImageIO.read(
                        getClass().getResourceAsStream("/boss/" + bossName + "_idle/idle_left" + (i + 1) + ".png"));
            } catch (Exception e) {
                idleFrames[i] = null;
            }
        }
    }

    @Override
    protected String getSpriteFolderName() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public void updateAnimation() {
        frameCounter++;
        if (frameCounter >= ANIMATION_SPEED) {
            currentFrame = (currentFrame + 1) % 5;
            frameCounter = 0;
        }
    }

    @Override
    public BufferedImage getCurrentFrame() {
        if (idleFrames[currentFrame] != null) {
            return idleFrames[currentFrame];
        }
        return null;
    }

    @Override
    public boolean hasSprite() {
        return idleFrames[0] != null;
    }
}