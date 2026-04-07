package Items.Consumables.Buff;

import Entities.Entity;
import Entities.Characters.Player;
import Items.Consumables.Consumable;

public class GreaterHardening extends Consumable {
    protected double defenseBuffAmount;

    public GreaterHardening(){
        super("Greater Hardening Potion", "Increases DEF by 120");
        defenseBuffAmount = 120;
        useMessage = "Used Greater Hardening Potion!";

        loadImage("/items/buff_potions/greater_hardening_potion.png");
    }

    @Override
    public <T> void useItem(T Entity){
        if (Entity instanceof Player) {
            Player target = (Player) Entity;
            double beforeDefense = target.getDefense();

            target.buffDefense(defenseBuffAmount);
            double afterDefense = target.getDefense();
            double actualBuff = afterDefense - beforeDefense;

            useMessage = "Used Greater Hardening Potion on " + target.getName() + "! DEF increased by " +
                    String.format("%.1f", actualBuff) + ".";
        } else if (Entity instanceof Entity) {
            Entity target = (Entity) Entity;
            double beforeDefense = target.getDefense();

            target.buffDefense(defenseBuffAmount);
            double afterDefense = target.getDefense();
            double actualBuff = afterDefense - beforeDefense;

            useMessage = "Used Greater Hardening Potion on " + target.getName() + "! DEF increased by " +
                    String.format("%.1f", actualBuff) + ".";
        }
    }
}