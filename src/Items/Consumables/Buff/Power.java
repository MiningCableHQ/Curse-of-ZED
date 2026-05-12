package Items.Consumables.Buff;

import Entities.Entity;
import Entities.Characters.Player;
import Items.Consumables.Consumable;

public class Power extends Consumable {
    protected double attackBuffAmount;

    public Power() {
        super("Power Potion", "Increases ATK by 30", 80);
        attackBuffAmount = 30;
        useMessage = "Used Power Potion!";

        loadImage("/items/buff_potions/power_potion.png");
    }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Player) {
            Player target = (Player) Entity;
            double beforeAttack = target.getAttack();

            target.buffAttack(attackBuffAmount);
            double afterAttack = target.getAttack();
            double actualBuff = afterAttack - beforeAttack;

            useMessage = "Used Power Potion on " + target.getName() + "! ATK increased by " +
                    String.format("%.0f", actualBuff) + ".";
        }
    }
}