package Entities.Characters;

import Main.*;
import Moves.Ranger.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ranger extends Player {
    private int windstepStacks = 0;
    private double originalSpeed;

    public Ranger(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH);
        name = "Ranger";
        hp = 680;
        maxHp = hp;
        attack = 240;
        maxAttack = attack;
        defense = 20;
        maxDefense = defense;
        speed = 50;
        dmgResistance = 0.10;
        loadMoves();

        originalSpeed = speed;
    }

    @Override
    public void getPlayerImage(){
        // Load walking animations
        try{
            left1 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_left3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_left4.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_right3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/archer/archer_walking/walking_right4.png"));
        }catch(IOException e){
            e.printStackTrace();
            createWalkingFallback();
        }

        // Load idle animations
        boolean idleLoaded = false;
        for (int i = 0; i < 5; i++) {
            try {
                idleLeft[i] = ImageIO.read(getClass().getResourceAsStream("/archer/archer_idle/idlle_left" + (i + 1) + ".png"));
                idleRight[i] = ImageIO.read(getClass().getResourceAsStream("/archer/archer_idle/idle_right" + (i + 1) + ".png"));
                idleLoaded = true;
            } catch (IOException e) {
                idleLeft[i] = null;
                idleRight[i] = null;
            }
        }

        // Create placeholder idle frames if images don't exist
        if (!idleLoaded) {
            createIdlePlaceholders();
        }
    }

    private void createWalkingFallback() {
        // Create colored rectangles as fallback for walking animations
        Color rangerColor = new Color(80, 180, 100); // Green for Ranger
        for (int i = 1; i <= 4; i++) {
            left1 = createPlaceholderImage(rangerColor);
            left2 = createPlaceholderImage(rangerColor);
            left3 = createPlaceholderImage(rangerColor);
            left4 = createPlaceholderImage(rangerColor);
            right1 = createPlaceholderImage(rangerColor);
            right2 = createPlaceholderImage(rangerColor);
            right3 = createPlaceholderImage(rangerColor);
            right4 = createPlaceholderImage(rangerColor);
        }
    }

    private void createIdlePlaceholders() {
        Color rangerColor = new Color(80, 180, 100); // Green for Ranger
        for (int i = 0; i < 5; i++) {
            idleLeft[i] = createIdlePlaceholderImage(rangerColor, false);
            idleRight[i] = createIdlePlaceholderImage(rangerColor, true);
        }
    }

    private BufferedImage createPlaceholderImage(Color color) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(color);
        g2.fillRect(0, 0, 48, 48);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("R", 18, 30);
        g2.dispose();
        return img;
    }

    private BufferedImage createIdlePlaceholderImage(Color color, boolean facingRight) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(color);
        g2.fillRect(0, 0, 48, 48);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        if (facingRight) {
            g2.drawString("R→", 18, 30);
        } else {
            g2.drawString("←R", 18, 30);
        }
        g2.dispose();
        return img;
    }

    @Override
    public void loadMoves(){
        moveset.add(new PreciseShot());
        moveset.add(new Scattershot());
        moveset.add(new Windstep());
        moveset.add(new BounceShot());
        moveset.add(new FlurryShot());
        moveset.add(new ShadowStep());
        moveset.add(new SnipersGamble());

        moves.add(new PreciseShot());
        moves.add(new Scattershot());
        moves.add(new Windstep());
        moves.add(new BounceShot());
    }

    // --- For move: Windstep ------------------------------------------------------------------------------------------
    public int getWindstepStacks() {
        return windstepStacks;
    }
    public boolean canUseWindstep() {
        return windstepStacks < 3;
    }
    public void addWindstepStack() {
        if (windstepStacks < 3) {
            windstepStacks++;
            // Increase SPD by 8%
            speed = originalSpeed + (originalSpeed * 0.08 * windstepStacks);
        }
    }
    public void resetBattleBuffs() {
        windstepStacks = 0;
        speed = originalSpeed;
    }
    public void setInBattle(boolean inBattle) {
        if (!inBattle) {
            resetBattleBuffs();
        }
    }
    @Override
    public double getSpeed() {
        return speed;
    }
}