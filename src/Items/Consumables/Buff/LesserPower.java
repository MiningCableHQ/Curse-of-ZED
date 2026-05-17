package Items.Consumables.Buff;

import Entities.Entity;
import Entities.Characters.Player;
import Items.Consumables.Consumable;

public class LesserPower extends Consumable {
    protected double attackBuffAmount;

    public LesserPower() {
        super("Lesser Power Potion", "Increases ATK by 50", 120);
        attackBuffAmount = 50;
        useMessage = "Used Lesser Power Potion!";

        loadImage("/items/buff_potions/lesser_power_potion.png");
    }

    @Override
    public <T> void useItem(T Entity) {
        if (Entity instanceof Player) {
            Player target = (Player) Entity;
            double beforeAttack = target.getAttack();

            target.buffAttack(attackBuffAmount);
            double afterAttack = target.getAttack();
            double actualBuff = afterAttack - beforeAttack;

            useMessage = "Used Lesser Power Potion on " + target.getName() + "! ATK increased by " +
                    String.format("%.0f", actualBuff) + ".";
        }
    }
}