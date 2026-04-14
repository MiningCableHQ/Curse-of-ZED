package Items.Weapons;

import Items.Item;

public abstract class Weapon extends Item {
    protected String imagePath;
    protected double attack;

    public Weapon(String name, String description, double attack, String imagePath){
        super(name, description);
        this.attack = attack;
        tier = 1.0;

        this.imagePath = imagePath;
    }

    public Weapon(String name, String description, double attack, String imagePath, int price){
        super(name, description, price);
        this.attack = attack;
        tier = 1.0;

        this.imagePath = imagePath;
    }

    public Weapon(String name, String description, double attack){
        super(name, description);
        this.attack = attack;
        tier = 1.0;
    }

    public Weapon(String name, String description, double attack, int price){
        super(name, description, price);
        this.attack = attack;
        tier = 1.0;
    }

    public abstract <T> void activatePassive(T Entity);

    //getters
    public double getAttack(){
        return attack;
    }
    public String getImagePath(){
        return imagePath;
    }
}