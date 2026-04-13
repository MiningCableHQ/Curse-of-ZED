package Combat.StatusEffects;

import Entities.Entity;

public class Poison extends StatusEffect {
    private static final int DAMAGE_PER_TICK = 60;

    public Poison() {
        super("Poison", false);
    }

    public Poison(int duration) {
        super("Poison", false);
        this.duration = duration;
    }

    @Override
    public <T> void executeEffect(T Entity) {
        if (Entity instanceof Entity) {
            Entity target = (Entity) Entity;

            double trueDamage = DAMAGE_PER_TICK;
            double actualDamage = target.takeTrueDamage(trueDamage);

            System.out.println(target.getName() + " takes " + (int)actualDamage +
                    " poison damage! (" + duration + " turns remaining)");
        }
    }
}