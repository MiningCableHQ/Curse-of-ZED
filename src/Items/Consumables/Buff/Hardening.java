package Items.Consumables.Buff;

import Entities.Entity;
import Entities.Characters.Player;
import Items.Consumables.Consumable;

public class Hardening extends Consumable {
    protected double defenseBuffAmount;

    public Hardening(){
        super("Hardening Potion", "Increases DEF by 80", 60);
        defenseBuffAmount = 80;
        useMessage = "Used Hardening Potion!";

        loadImage("/items/buff_potions/hardening_potion.png");
    }

    @Override
    public <T> void useItem(T Entity){
        if (Entity instanceof Player) {
            Player target = (Player) Entity;
            double beforeDefense = target.getDefense();

            target.buffDefense(defenseBuffAmount);
            double afterDefense = target.getDefense();
            double actualBuff = afterDefense - beforeDefense;

            useMessage = "Used Hardening Potion on " + target.getName() + "! DEF increased by " +
                    String.format("%.0f", actualBuff) + ".";
        }
    }
}