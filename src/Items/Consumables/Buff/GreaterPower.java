package Items.Consumables.Buff;

import Entities.Entity;
import Entities.Characters.Player;
import Items.Consumables.Consumable;

public class GreaterPower extends Consumable {
    protected double attackBuffAmount;

    public GreaterPower() {
        super("Greater Power Potion", "Increases ATK by 150", 360);
        attackBuffAmount = 150;
        useMessage = "Used Greater Power Potion!";

        loadImage("/items/buff_potions/greater_power_potion.png");
    }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Player) {
            Player target = (Player) Entity;
            double beforeAttack = target.getAttack();

            target.buffAttack(attackBuffAmount);
            double afterAttack = target.getAttack();
            double actualBuff = afterAttack - beforeAttack;

            useMessage = "Used Greater Power Potion on " + target.getName() + "! ATK increased by " +
                    String.format("%.0f", actualBuff) + ".";
        }
    }
}