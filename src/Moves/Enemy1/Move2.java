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

            // Store current HP to calculate actual heal amount
            double beforeHp = masklet.getHp();

            // Heal by 70 HP
            masklet.heal(70);

            // Calculate actual heal amount
            double afterHp = masklet.getHp();
            double healAmount = afterHp - beforeHp; //TODO FRANK display message in UI
        }
    }
}
