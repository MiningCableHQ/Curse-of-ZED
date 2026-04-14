package Combat.StatusEffects;

import Entities.Entity;
import java.util.Random;

public class Burn extends StatusEffect {
    Random random = new Random();

    public Burn() {
        super("Burn", false);
    }

    public Burn(int duration) {
        super("Burn", false);
        this.duration = duration;
    }

    @Override
    public <T> void executeEffect(T Entity) {
        if (Entity instanceof Entity) {
            Entity target = (Entity) Entity;

            // Calculate random damage between 100 and 300
            int damage = 100 + random.nextInt(201);
            double actualDamage = target.takeTrueDamage(damage);

            System.out.println(target.getName() + " is burned and takes " + (int)actualDamage +
                    " damage! (" + duration + " turn" + (duration != 1 ? "s" : "") + " remaining)");
        }
    }
}