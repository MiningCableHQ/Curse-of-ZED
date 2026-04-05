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

            // Heal by 70 HP
            masklet.heal(70);
        }
    }
}
