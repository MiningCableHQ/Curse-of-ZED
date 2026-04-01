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

            // Store current HP to calculate actual heal amount
            double beforeHp = frankenstein.getHp();

            // Heal by 150 HP
            frankenstein.heal(150);

            // Calculate actual heal amount
            double afterHp = frankenstein.getHp();
            double healAmount = afterHp - beforeHp; //TODO FRANK display message in UI
        }
    }
}
