package Items.Weapons.Swordsman;

import Items.Weapons.Weapon;

public class RazorEdge extends Weapon{
    public RazorEdge(){
        super("RazorEdge", "30% chance to deal multiplied damage", 20, "/items/warrior_weapon/razoredge_sword.png");
        loadImage("/items/warrior_weapon/razoredge_sword.png");
    }

    @Override
    public <T> void activatePassive(T Entity) {
        //TODO 30% chance to deal 120% | 140% | 160% | 180% | 200% the dmg
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}