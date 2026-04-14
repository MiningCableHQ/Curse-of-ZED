package Moves.Enemy1;

import Entities.Enemies.Masklet;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("Glimmerweave", 0);
    }

    @Override
    public <T> void execute(T Entity) {
        if (Entity instanceof Masklet) {
            Masklet masklet = (Masklet) Entity;

            // Store HP before healing
            double beforeHp = masklet.getHp();
            masklet.heal(70);

            // Calculate actual healed amount (capped by max HP)
            double afterHp = masklet.getHp();
            double actualHeal = afterHp - beforeHp;

            setHealAmount(actualHeal);
            if (actualHeal > 0) {
                setMessage(masklet.getName() + " used " + this.name + " and healed " +
                        String.format("%.0f", actualHeal) + " HP!");
            } else {
                setMessage(masklet.getName() + " used " + this.name + " but was already at full health!");
            }
        }
    }
}