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

            // Increase attack by 10
            zenzilla.buffAttack(10);
        }
    }
}
