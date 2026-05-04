package Items.Weapons.Ranger;

import Items.Weapons.Weapon;
import Entities.Entity;
import Moves.Move;
import Combat.StatusEffects.*;
import java.util.Random;

public class Slowstring extends Weapon {
    private static final Random random = new Random();
    // Tier 1: 20%, Tier 2: 25%, Tier 3: 30%, Tier 4: 35%, Tier 5: 40%
    private static final double[] DEBUFF_CHANCES = {0.20, 0.25, 0.30, 0.35, 0.40};
    private static final Class<?>[] DEBUFFS = {Slow.class, Frozen.class};
    private static final String[] DEBUFF_NAMES = {"Slow", "Frozen"};

    public Slowstring() {
        super("Slowstring", "Chance to apply Slow or Frozen debuff on an enemy", 10, "/items/archer_weapon/slowstring.png", 225);
        loadImage("/items/archer_weapon/slowstring.png");
    }

    public Slowstring(double tier) {
        super("Slowstring", "Chance to apply Slow or Frozen debuff on an enemy", 10, "/items/archer_weapon/slowstring.png", 225);
        this.tier = Math.max(1.0, Math.min(5.0, tier));
        loadImage("/items/archer_weapon/slowstring.png");
    }

    @Override
    public void onAfterDamage(Entity user, Move move, Entity target, double damageDealt) {
        // Only affects single-target damage moves that hit an enemy
        if (target != null && move.getTargetType() == Move.TargetType.ENEMY && damageDealt > 0) {
            int tierIndex = getTierIndex();
            double chance = DEBUFF_CHANCES[tierIndex];

            if (random.nextDouble() <= chance) {
                int debuffIndex = random.nextInt(DEBUFFS.length);
                applyDebuff(target, debuffIndex);

                setLastMessage(user.getName() + "'s Slowstring activated! (" +
                        (int)(chance * 100) + "% chance) " +
                        target.getName() + " is now " + DEBUFF_NAMES[debuffIndex].toLowerCase() + "ed! (Tier " + (int)tier + ")");
            } else {
                setLastMessage("");
            }
        }
    }

    private void applyDebuff(Entity target, int debuffIndex) {
        switch (debuffIndex) {
            case 0:
                target.addStatusEffect(new Slow(1));
                System.out.println(target.getName() + " is Slowed! Will skip next turn.");
                break;
            case 1:
                Move.currentBattle.applyFrozen(target);
                System.out.println(target.getName() + " is Frozen! Cannot act this cycle.");
                break;
        }
    }

    @Override
    public String getPassiveDescription() {
        int tierIndex = getTierIndex();
        return (int)(DEBUFF_CHANCES[tierIndex] * 100) + "% Chance to apply Slow or Frozen on enemies";
    }

    @Override
    public <T> void activatePassive(T Entity){
        // Handled by onAfterDamage
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}