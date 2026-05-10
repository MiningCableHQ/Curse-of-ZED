package Entities.Characters;

import Items.Consumables.Heal.Healing;
import Items.Weapons.Ranger.*;
import Main.*;
import Moves.Ranger.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ranger extends Player {
    private int harmonyStacks = 0;
    private double flatAttackBonus = 0;
    private double flatDefenseBonus = 0;

    public Ranger(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH);
        name = "Archer";
        hp = 880;
        maxHp = hp;
        attack = 240;
        maxAttack = attack;
        defense = 20;
        maxDefense = defense;
        speed = 50;
        dmgResistance = 0.10;
        loadMoves();
        //setWeapon(new Swiftwind(5));
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
        moveset.add(new Harmony());
        moveset.add(new BounceShot());
        moveset.add(new FlurryShot());
        moveset.add(new LifedrainArrow());
        moveset.add(new SnipersGamble());

        moves.add(new PreciseShot());
        moves.add(new Scattershot());
        moves.add(new Harmony());
        moves.add(new BounceShot());
    }

    // --- For move: Harmony ------------------------------------------------------------------------------------------
    public int getHarmonyStacks() {
        return harmonyStacks;
    }

    public boolean canUseHarmony() {
        return harmonyStacks < 3;
    }

    public void addHarmonyStack() {
        if (harmonyStacks < 3) {
            harmonyStacks++;
            // Increase attack by 12 per stack
            flatAttackBonus = 12 * harmonyStacks;
            // Increase defense by 12 per stack
            flatDefenseBonus = 12 * harmonyStacks;
            recalculateStats();
        }
    }

    public void resetBattleBuffs() {
        harmonyStacks = 0;
        flatAttackBonus = 0;
        flatDefenseBonus = 0;
        recalculateStats();
    }

    public void setInBattle(boolean inBattle) {
        if (!inBattle) {
            resetBattleBuffs();
        }
    }

    // Methods to add flat bonuses
    public void addFlatAttackBonus(double bonus) {
        flatAttackBonus += bonus;
        recalculateStats();
    }

    public void addFlatDefenseBonus(double bonus) {
        flatDefenseBonus += bonus;
        recalculateStats();
    }

    // Methods to remove flat bonuses (if needed)
    public void removeFlatAttackBonus(double bonus) {
        flatAttackBonus -= bonus;
        if (flatAttackBonus < 0) flatAttackBonus = 0;
        recalculateStats();
    }

    public void removeFlatDefenseBonus(double bonus) {
        flatDefenseBonus -= bonus;
        if (flatDefenseBonus < 0) flatDefenseBonus = 0;
        recalculateStats();
    }

    // Recalculate total stats based on original stats + flat bonuses
    private void recalculateStats() {
        // Recalculate attack
        this.attack = attack + flatAttackBonus;

        // Recalculate defense
        this.defense = defense + flatDefenseBonus;
    }

    //For mistwood weapon passive
    private double flatSpeedBonus = 0;

    public void addFlatSpeedBonus(double bonus) {
        flatSpeedBonus += bonus;
        recalculateSpeed();
    }

    public void removeFlatSpeedBonus(double bonus) {
        flatSpeedBonus -= bonus;
        if (flatSpeedBonus < 0) flatSpeedBonus = 0;
        recalculateSpeed();
    }

    private void recalculateSpeed() {
        this.speed = speed + flatSpeedBonus;
    }

    // Getters
    @Override
    public double getAttack() {
        return attack;
    }

    @Override
    public double getDefense() {
        return defense;
    }
}