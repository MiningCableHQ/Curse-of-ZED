package Items.Consumables.Buff;

import Entities.Entity;
import Entities.Characters.Player;
import Items.Consumables.Consumable;

public class LesserHardening extends Consumable {
    protected double defenseBuffAmount;

    public LesserHardening(){
        super("Lesser Hardening Potion", "Increases DEF by 40", 30);
        defenseBuffAmount = 40;
        useMessage = "Used Lesser Hardening Potion!";

        loadImage("/items/buff_potions/lesser_hardening_potion.png");
    }

    @Override
    public <T> void useItem(T Entity){
        if (Entity instanceof Player) {
            Player target = (Player) Entity;
            double beforeDefense = target.getDefense();

            target.buffDefense(defenseBuffAmount);
            double afterDefense = target.getDefense();
            double actualBuff = afterDefense - beforeDefense;

            useMessage = "Used Lesser Hardening Potion on " + target.getName() + "! DEF increased by " +
                    String.format("%.0f", actualBuff) + ".";
        }
    }
}