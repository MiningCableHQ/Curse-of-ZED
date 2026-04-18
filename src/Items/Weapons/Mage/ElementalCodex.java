package Items.Weapons.Mage;

import Items.Weapons.Weapon;
import Entities.Entity;
import Moves.Move;
import Combat.StatusEffects.*;
import java.util.Random;

public class ElementalCodex extends Weapon {
    private static final Random random = new Random();
    // Tier 1: 20%, Tier 2: 25%, Tier 3: 30%, Tier 4: 35%, Tier 5: 40%
    private static final double[] DEBUFF_CHANCES = {0.2, 0.25, 0.3, 0.35, 0.4};
    private static final Class<?>[] DEBUFFS = {Poison.class, Burn.class, Frozen.class};
    private static final String[] DEBUFF_NAMES = {"Poison", "Burn", "Frozen"};

    public ElementalCodex(){
        super("Elemental Codex", "Single target moves have a chance to apply poison/burn/frozen debuff on an enemy", 15, "/items/mage_weapon/elemental_codex.png", 265);
        loadImage("/items/mage_weapon/elemental_codex.png");
    }

    public ElementalCodex(double tier){
        super("Elemental Codex", "Single target moves have a chance to apply poison/burn/frozen debuff on an enemy", 15, "/items/mage_weapon/elemental_codex.png", 265);
        this.tier = Math.max(1.0, Math.min(5.0, tier));
        loadImage("/items/mage_weapon/elemental_codex.png");
    }

    @Override
    public void onAfterDamage(Entity user, Move move, Entity target, double damageDealt) {
        // Only affects damage-dealing moves that hit an enemy
        if (target != null && move.getTargetType() == Move.TargetType.ENEMY && damageDealt > 0) {
            int tierIndex = getTierIndex();
            double chance = DEBUFF_CHANCES[tierIndex];

            if (random.nextDouble() <= chance) {
                int debuffIndex = random.nextInt(DEBUFFS.length);
                applyDebuff(target, debuffIndex);

                setLastMessage(user.getName() + "'s Elemental Codex activated! (" +
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
                target.addStatusEffect(new Poison(5));
                System.out.println(target.getName() + " is Poisoned! Will take damage for 5 turns.");
                break;
            case 1:
                target.addStatusEffect(new Burn(1));
                System.out.println(target.getName() + " is Burned! Will take damage next turn.");
                break;
            case 2:
                Move.currentBattle.applyFrozen(target);
                System.out.println(target.getName() + " is Frozen! Cannot act this cycle.");
                break;
        }
    }

    @Override
    public String getPassiveDescription() {
        int tierIndex = getTierIndex();
        return (int)(DEBUFF_CHANCES[tierIndex] * 100) + "% Chance to apply Poison, Burn, or Frozen on enemies";
    }

    @Override
    public <T> void activatePassive(T Entity){
        // Handled by onAfterDamage
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}