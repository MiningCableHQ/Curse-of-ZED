package Items.Weapons.Ranger;

import Items.Weapons.Weapon;

public class Mistwood extends Weapon {
    public Mistwood() {
        super("Mistwood", "Increase speed", 15, "/items/archer_weapon/mistwood.png");
        loadImage("/items/archer_weapon/mistwood.png");
    }

    @Override
    public <T> void activatePassive(T Entity){
        //TODO Increase speed by 5 | 10 | 15 | 20 | 25
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}