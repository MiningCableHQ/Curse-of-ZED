package Entities.Characters;

import Items.Weapons.Mage.*;
import Main.*;
import Moves.Mage.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Mage extends Player {
    private int empowerStacks = 0;
    private double flatAttackBonus = 0;

    public Mage(GamePanel gp, KeyHandler keyH){
        super(gp, keyH);
        name = "Mage";
        hp = 800;
        maxHp = hp;
        attack = 335;
        maxAttack = attack;
        defense = 10;
        maxDefense = defense;
        speed = 35;
        dmgResistance = 0.13;
        loadMoves();
    }

    @Override
    public void getPlayerImage(){
        // Load walking animations
        try{
            left1 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_leftt1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_leftt2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_leftt3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_leftt4.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_right3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/mage/mage_walking/walking_right4.png"));
        }catch(IOException e){
            e.printStackTrace();
            createWalkingFallback();
        }

        // Load idle animations
        boolean idleLoaded = false;
        for (int i = 0; i < 5; i++) {
            try {
                idleLeft[i] = ImageIO.read(getClass().getResourceAsStream("/mage/mage_idle/idle_left" + (i + 1) + ".png"));
                idleRight[i] = ImageIO.read(getClass().getResourceAsStream("/mage/mage_idle/idle_right" + (i + 1) + ".png"));
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
        Color mageColor = new Color(80, 120, 220); // Blue for Mage
        for (int i = 1; i <= 4; i++) {
            left1 = createPlaceholderImage(mageColor);
            left2 = createPlaceholderImage(mageColor);
            left3 = createPlaceholderImage(mageColor);
            left4 = createPlaceholderImage(mageColor);
            right1 = createPlaceholderImage(mageColor);
            right2 = createPlaceholderImage(mageColor);
            right3 = createPlaceholderImage(mageColor);
            right4 = createPlaceholderImage(mageColor);
        }
    }

    private void createIdlePlaceholders() {
        Color mageColor = new Color(80, 120, 220); // Blue for Mage
        for (int i = 0; i < 5; i++) {
            idleLeft[i] = createIdlePlaceholderImage(mageColor, false);
            idleRight[i] = createIdlePlaceholderImage(mageColor, true);
        }
    }

    private BufferedImage createPlaceholderImage(Color color) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(color);
        g2.fillRect(0, 0, 48, 48);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("M", 18, 30);
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
            g2.drawString("M→", 18, 30);
        } else {
            g2.drawString("←M", 18, 30);
        }
        g2.dispose();
        return img;
    }

    @Override
    public void loadMoves(){
        moveset.add(new ArcaneBolt());
        moveset.add(new ArcaneExplosion());
        moveset.add(new Empower());
        moveset.add(new Revitalize());
        moveset.add(new LifeLeech());
        moveset.add(new Roulette());
        moveset.add(new ChillingGamble());

        moves.add(new ArcaneBolt());
        moves.add(new ArcaneExplosion());
        moves.add(new Empower());
        //moves.add(new Revitalize());
        moves.add(new ChillingGamble()); //Added ult for playtest purposes
    }

    // --- For move: Empower -------------------------------------------------------------------------------------------
    public int getEmpowerStacks() {
        return empowerStacks;
    }

    public boolean canUseEmpower() {
        return empowerStacks < 3;
    }

    public void addEmpowerStack() {
        if (empowerStacks < 3) {
            empowerStacks++;
            // Increase attack by flat 25 per stack
            flatAttackBonus = 25;
            recalculateAttack();
        }
    }

    public void resetBattleBuffs() {
        empowerStacks = 0;
        flatAttackBonus = 0;
        recalculateAttack();
    }

    public void setInBattle(boolean inBattle) {
        if (!inBattle) {
            resetBattleBuffs();
        }
    }

    // Method to add a flat attack bonus
    public void addFlatAttackBonus(double bonus) {
        flatAttackBonus += bonus;
        recalculateAttack();
    }

    // Method to remove a flat attack bonus (if needed)
    public void removeFlatAttackBonus(double bonus) {
        flatAttackBonus -= bonus;
        if (flatAttackBonus < 0) flatAttackBonus = 0;
        recalculateAttack();
    }

    // Recalculate total attack based on original attack + flat bonuses
    private void recalculateAttack() {
        this.attack = attack + flatAttackBonus;
    }

    @Override
    public double getAttack() {
        return attack;
    }
}