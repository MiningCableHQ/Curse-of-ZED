package Items.Weapons;

import Items.Item;
import Entities.Entity;
import Moves.Move;

public abstract class Weapon extends Item {
    protected String imagePath;
    protected double attack;
    protected int price;
    protected double tier; // 1.0, 2.0, 3.0, 4.0, 5.0
    protected String lastMessage = "";

    // Constructor with price
    public Weapon(String name, String description, double attack, String imagePath, int price){
        super(name, description, price);
        this.attack = attack;
        this.imagePath = imagePath;
        this.tier = 1.0;
    }

    public Weapon(String name, String description, double attack, String imagePath){
        super(name, description);
        this.attack = attack;
        this.imagePath = imagePath;
        this.tier = 1.0;
    }

    public <T> void activatePassive(T Entity){}

    // Tier management
    public double getTier() {
        return tier;
    }

    public void setTier(double tier) {
        this.tier = Math.max(1.0, Math.min(5.0, tier));
    }

    public int getTierIndex() {
        return (int)(tier - 1); // Returns 0 for tier 1, 1 for tier 2, etc.
    }

    // Passive methods - override in specific weapons
    public void onAfterDamage(Entity user, Move move, Entity target, double damageDealt) {}

    public void onAfterMove(Entity user, Move move, Entity target) {}

    public void onEquip(Entity user) {}

    public void onUnequip(Entity user) {}

    public String getPassiveDescription() {
        return "No passive effect";
    }

    protected String getLastMessage() {
        return lastMessage;
    }

    protected void setLastMessage(String message) {
        this.lastMessage = message;
    }

    // Getters
    public double getAttack(){
        return attack;
    }

    public String getImagePath(){
        return imagePath;
    }

    public int getPrice() {
        return price;
    }
}