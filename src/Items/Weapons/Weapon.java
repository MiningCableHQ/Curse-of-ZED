package Items.Weapons;

import Items.Item;

public abstract class Weapon extends Item {
    protected String imagePath;
    protected double attack;

    // Constructor with imagePath
    public Weapon(String name, String description, double attack, String imagePath){
        super(name, description);
        this.attack = attack;
        this.imagePath = imagePath;
        tier = 1.0;
    }

    // Constructor without imagePath (for backward compatibility)
    public Weapon(String name, String description, double attack){
        super(name, description);
        this.attack = attack;
        tier = 1.0;
    }

    public abstract <T> void activatePassive(T Entity);

    // Getters
    public double getAttack(){
        return attack;
    }

    public String getImagePath(){
        return imagePath;
    }
}