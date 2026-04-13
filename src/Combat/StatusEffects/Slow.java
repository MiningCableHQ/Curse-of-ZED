package Combat.StatusEffects;

import Entities.Entity;

public class Slow extends StatusEffect {

    public Slow() {
        super("Slow", false); // Turn-based effect
    }

    public Slow(int duration) {
        super("Slowed", false);
        this.duration = duration;
    }

    @Override
    public <T> void executeEffect(T Entity) {
        if (Entity instanceof Entity) {
            Entity target = (Entity) Entity;

            System.out.println(target.getName() + " is Slowed and cannot move this turn!");
        }
    }
}