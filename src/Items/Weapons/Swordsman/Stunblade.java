package Items.Weapons.Swordsman;

import Items.Weapons.Weapon;
import Entities.Entity;
import Moves.Move;
import Combat.StatusEffects.*;
import java.util.Random;

public class Stunblade extends Weapon {
    private static final Random random = new Random();
    // Tier 1: 20%, Tier 2: 25%, Tier 3: 30%, Tier 4: 35%, Tier 5: 40%
    private static final double[] DEBUFF_CHANCES = {0.20, 0.25, 0.30, 0.35, 0.40};
    private static final Class<?>[] DEBUFFS = {Stun.class, Frozen.class};
    private static final String[] DEBUFF_NAMES = {"Stun", "Frozen"};

    public Stunblade() {
        super("Stunblade", "Chance to apply Stun or Frozen debuff on an enemy", 15, "/items/warrior_weapon/stunblade_sword.png", 265);
        loadImage("/items/warrior_weapon/stunblade_sword.png");
    }

    public Stunblade(double tier) {
        super("Stunblade", "Chance to apply Stun or Frozen debuff on an enemy", 15, "/items/warrior_weapon/stunblade_sword.png", 265);
        this.tier = Math.max(1.0, Math.min(5.0, tier));
        loadImage("/items/warrior_weapon/stunblade_sword.png");
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

                setLastMessage(user.getName() + "'s Stunblade activated! (" +
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
                Move.currentBattle.applyStun(target);
                System.out.println(target.getName() + " is Stunned! Accuracy reduced for this cycle.");
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
        return (int)(DEBUFF_CHANCES[tierIndex] * 100) + "% Chance to apply Stun or Frozen on enemies";
    }

    @Override
    public <T> void activatePassive(T Entity){
        // Handled by onAfterDamage
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}