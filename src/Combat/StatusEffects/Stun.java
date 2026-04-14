package Combat.StatusEffects;

import Entities.Entity;

public class Stun extends StatusEffect {
    public static final double ACCURACY_REDUCTION = 0.10; // 10% reduction

    public Stun() {
        super("Stunned", true); // Cycle-based effect
    }

    @Override
    public <T> void executeEffect(T Entity) {
        if (Entity instanceof Entity) {
            Entity target = (Entity) Entity;

            System.out.println(target.getName() + " is Stunned! Accuracy reduced by 10% for this cycle!");
        }
    }
}