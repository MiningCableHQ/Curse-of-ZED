package Entities.Enemies;

import Entities.Entity;
import Moves.Move;
import java.awt.image.BufferedImage;
import java.util.*;

import java.util.ArrayList;

public abstract class Enemy extends Entity {
    protected int expYield;
    protected int goldYield;
    protected ArrayList<Move> moveset;
    protected ArrayList<Move> moves;

    // Enemy sprite animation fields
    protected BufferedImage[] idleFrames = new BufferedImage[4];
    protected int currentFrame = 0;
    protected int frameCounter = 0;
    protected static final int ANIMATION_SPEED = 2;

    public Enemy(){
        accuracy = 0.85;
        moves = new ArrayList<>();
        moveset = new ArrayList<>();
    }

    public Enemy(String name, double attack){
    }

    public abstract void loadMoves();

    public void loadSprite() {
        String enemyName = getSpriteFolderName();
        for (int i = 0; i < 4; i++) {
            try {
                idleFrames[i] = javax.imageio.ImageIO.read(
                        getClass().getResourceAsStream("/enemies/" + enemyName + "/"+ enemyName +"_idle/idle_left" + (i + 1) + ".png"));
            } catch (Exception e) {
                idleFrames[i] = null;
            }
        }
    }

    protected String getSpriteFolderName() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    public void updateAnimation() {
        frameCounter++;
        if (frameCounter >= ANIMATION_SPEED) {
            currentFrame = (currentFrame + 1) % 4;
            frameCounter = 0;
        }
    }

    public BufferedImage getCurrentFrame() {
        if (idleFrames[currentFrame] != null) {
            return idleFrames[currentFrame];
        }
        return null;
    }

    public boolean hasSprite() {
        return idleFrames[0] != null;
    }

    //Move selection logic
    public abstract Move selectMove();

    //Getters and Setters
    public int getExpYield(){
        return expYield;
    }

    public int getGoldYield(){
        return goldYield;
    }

    public ArrayList<Move> getMoves(){
        return moves;
    }
}