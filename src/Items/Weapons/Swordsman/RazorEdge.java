package Items.Weapons.Swordsman;

import Items.Weapons.Weapon;

public class RazorEdge extends Weapon{
    public RazorEdge(){
        super("RazorEdge", "Description pls", 20);
    }

    @Override
    public <T> void activatePassive(T Entity) {
        //TODO 30% chance to deal 120% | 140% | 160% | 180% | 200% the dmg
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}
