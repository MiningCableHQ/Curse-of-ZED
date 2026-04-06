package Moves.Swordsman;

import Entities.Characters.Swordsman;
import Moves.Move;
import java.util.*;

public class Preservation extends Move {
    Random rand = new Random();

    public Preservation() {
        super("Preservation", 0, TargetType.SELF);
        hasUnlocked = false;
        description = "Heals the character by 300-800 HP";
    }

    @Override
    public <T> void execute(T Entity) {
        if (Entity instanceof Swordsman) {
            Swordsman swordsman = (Swordsman) Entity;

            //Random heal amount between 300 and 800
            double healAmount = 300 + rand.nextInt(501);

            swordsman.heal(healAmount);
        }
    }
}
