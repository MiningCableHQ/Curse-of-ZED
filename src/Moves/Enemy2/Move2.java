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

            // Store attack before buff
            double beforeAttack = zenzilla.getAttack();

            zenzilla.buffAttack(10);

            // Calculate actual attack increase
            double afterAttack = zenzilla.getAttack();
            double attackIncreased = afterAttack - beforeAttack;

            setBuffAmount(attackIncreased, "ATK");
            setMessage(zenzilla.getName() + " used " + this.name + " and increased attack by " + (int)attackIncreased);
        }
    }
}