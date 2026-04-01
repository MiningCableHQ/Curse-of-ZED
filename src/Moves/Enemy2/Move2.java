package Moves.Enemy2;

import Entities.Enemies.Zenzilla;
import Moves.Move;

public class Move2 extends Move {
    public Move2(){
        super("Inner Focus", 0);
    }

    @Override
    public <T> void execute(T Entity) {
        if (Entity instanceof Zenzilla) {
            Zenzilla zenzilla = (Zenzilla) Entity;

            double beforeAttack = zenzilla.getAttack();

            // Increase attack by 10
            zenzilla.buffAttack(10);

            double afterAttack = zenzilla.getAttack();
            double buffAmount = afterAttack - beforeAttack; //TODO FRANK Display on UI during enemy turn
        }
    }
}
