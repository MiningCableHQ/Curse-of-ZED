package Moves.Frankenstein;

import Entities.Enemies.Frankenstein;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("NAOLNAOLNAOL", 0);
    }

    @Override
    public <T> void execute(T Entity){
        if (Entity instanceof Frankenstein) {
            Frankenstein frankenstein = (Frankenstein) Entity;

            double beforeHp = frankenstein.getHp();
            frankenstein.heal(150);

            double afterHp = frankenstein.getHp();
            double actualHeal = afterHp - beforeHp;

            setHealAmount(actualHeal);
            if (actualHeal > 0) {
                setMessage(frankenstein.getName() + " used " + this.name + " and healed " +
                        (int)actualHeal + " HP!");
            } else {
                setMessage(frankenstein.getName() + " used " + this.name + " but was already at full health!");
            }
        }
    }
}