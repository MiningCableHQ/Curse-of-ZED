package Moves.Boss1;

import Entities.Enemies.Thorncrusher;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("Root Recovery", 0);
    }

    @Override
    public <T> void execute(T Entity){
        if (Entity instanceof Thorncrusher) {
            Thorncrusher thorncrusher = (Thorncrusher) Entity;

            double beforeHp = thorncrusher.getHp();
            thorncrusher.heal(100);

            double afterHp = thorncrusher.getHp();
            double actualHeal = afterHp - beforeHp;

            setHealAmount(actualHeal);
            if (actualHeal > 0) {
                setMessage(thorncrusher.getName() + " used " + this.name + " and healed " +
                        (int)actualHeal + " HP!");
            } else {
                setMessage(thorncrusher.getName() + " used " + this.name + " but was already at full health!");
            }
        }
    }
}