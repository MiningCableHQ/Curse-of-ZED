package Combat.StatusEffects;

import Entities.Entity;

public class Frozen extends StatusEffect {

    public Frozen() {
        super("Frozen", true);
    }

    @Override
    public <T> void executeEffect(T Entity) {
        if (Entity instanceof Entity) {
            Entity target = (Entity) Entity;

            System.out.println(target.getName() + " is Frozen and cannot act this cycle!");
        }
    }
}