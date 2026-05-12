package Entities.Characters;

import Items.Consumables.Heal.Healing;
import Main.*;
import Moves.Swordsman.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Swordsman extends Player {
    private int ironStanceStacks = 0;
    private double flatDefenseBonus = 0;

    public Swordsman(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH);
        name = "Swordsman";
        hp = 1000;
        maxHp = hp;
        attack = 205;
        maxAttack = attack;
        defense = 40;
        maxDefense = defense;
        speed = 28;
        dmgResistance = 0.05;
        loadMoves();
    }

    @Override
    public void getPlayerImage(){
        // Load walking animations
        try{
            left1 = ImageIO.read(getClass().getResourceAsStream("/swordsman/swordsman_walking/walking_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/swordsman/swordsman_walking/walking_left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/swordsman/swordsman_walking/walking_left3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/swordsman/swordsman_walking/walking_left4.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/swordsman/swordsman_walking/walking_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/swordsman/swordsman_walking/walking_right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/swordsman/swordsman_walking/walking_right3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/swordsman/swordsman_walking/walking_right4.png"));
        }catch(IOException e){
            e.printStackTrace();
            createWalkingFallback();
        }

        // Load idle animations
        boolean idleLoaded = false;
        for (int i = 0; i < 5; i++) {
            try {
                idleLeft[i] = ImageIO.read(getClass().getResourceAsStream("/swordsman/swordsman_idle/idle_leftt" + (i + 1) + ".png"));
                idleRight[i] = ImageIO.read(getClass().getResourceAsStream("/swordsman/swordsman_idle/idle_right" + (i + 1) + ".png"));
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
        Color armorColor = new Color(180, 100, 80); // Reddish-brown for Swordsman
        for (int i = 1; i <= 4; i++) {
            left1 = createPlaceholderImage(armorColor);
            left2 = createPlaceholderImage(armorColor);
            left3 = createPlaceholderImage(armorColor);
            left4 = createPlaceholderImage(armorColor);
            right1 = createPlaceholderImage(armorColor);
            right2 = createPlaceholderImage(armorColor);
            right3 = createPlaceholderImage(armorColor);
            right4 = createPlaceholderImage(armorColor);
        }
    }

    private void createIdlePlaceholders() {
        Color armorColor = new Color(180, 100, 80); // Reddish-brown for Swordsman
        for (int i = 0; i < 5; i++) {
            idleLeft[i] = createIdlePlaceholderImage(armorColor, false);
            idleRight[i] = createIdlePlaceholderImage(armorColor, true);
        }
    }

    private BufferedImage createPlaceholderImage(Color color) {
        BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(color);
        g2.fillRect(0, 0, 48, 48);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("S", 18, 30);
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
            g2.drawString("S→", 18, 30);
        } else {
            g2.drawString("←S", 18, 30);
        }
        g2.dispose();
        return img;
    }

    @Override
    public void loadMoves(){
        moveset.add(new HeroicSlash());
        moveset.add(new SweepingStrike());
        moveset.add(new IronStance());
        moveset.add(new GuideToAfterlife());
        moveset.add(new MultiStrike());
        moveset.add(new Preservation());
        moveset.add(new SacrificialBlade());

        moves.add(new HeroicSlash());
        moves.add(new SweepingStrike());
        moves.add(new IronStance());
        moves.add(new GuideToAfterlife());
    }

    // --- For move: Iron stance ---------------------------------------------------------------------------------------
    public int getIronStanceStacks() {
        return ironStanceStacks;
    }

    public boolean canUseIronStance() {
        return ironStanceStacks < 3;
    }

    public void addIronStanceStack() {
        if (ironStanceStacks < 3) {
            ironStanceStacks++;
            // Increase defense by flat 30 per stack
            flatDefenseBonus = 30;
            recalculateDefense();
        }
    }

    public void resetBattleBuffs() {
        ironStanceStacks = 0;
        flatDefenseBonus = 0;
        recalculateDefense();
    }

    public void setInBattle(boolean inBattle) {
        if (!inBattle) {
            resetBattleBuffs();
        }
    }

    // Method to add a flat defense bonus (from potions)
    public void addFlatDefenseBonus(double bonus) {
        flatDefenseBonus += bonus;
        recalculateDefense();
    }

    // Method to remove a flat defense bonus (if needed)
    public void removeFlatDefenseBonus(double bonus) {
        flatDefenseBonus -= bonus;
        if (flatDefenseBonus < 0) flatDefenseBonus = 0;
        recalculateDefense();
    }

    // Recalculate total defense based on original defense + flat bonuses
    private void recalculateDefense() {
        this.defense = defense + flatDefenseBonus;
    }

    //For RazorEgde weapon passive
    private double flatAttackBonus = 0;

    public void addFlatAttackBonus(double bonus) {
        flatAttackBonus += bonus;
        recalculateAttack();
    }

    public void removeFlatAttackBonus(double bonus) {
        flatAttackBonus -= bonus;
        if (flatAttackBonus < 0) flatAttackBonus = 0;
        recalculateAttack();
    }

    private void recalculateAttack() {
        this.attack = attack + flatAttackBonus;
    }

    //For Unyielding weapon passive
    private double flatResistanceBonus = 0;

    public void addDamageResistance(double bonus) {
        flatResistanceBonus += bonus;
        recalculateResistance();
    }

    public void removeDamageResistance(double bonus) {
        flatResistanceBonus -= bonus;
        if (flatResistanceBonus < 0) flatResistanceBonus = 0;
        recalculateResistance();
    }

    private void recalculateResistance() {
        this.dmgResistance = dmgResistance + flatResistanceBonus;
    }

    @Override
    public double getDefense() {
        return defense;
    }
}