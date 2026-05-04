package Items.Weapons.Swordsman;

import Items.Weapons.Weapon;
import Entities.Entity;
import Entities.Characters.Swordsman;
import Moves.Move;

public class Unyielding extends Weapon {
    // Tier 1: +3%, Tier 2: +6%, Tier 3: +9%, Tier 4: +12%, Tier 5: +15%
    private static final double[] RESISTANCE_BONUSES = {0.03, 0.06, 0.09, 0.12, 0.15};
    private double currentResistanceBonus = 0.03;

    public Unyielding() {
        super("Unyielding", "Increases damage resistance by 3-15% based on tier", 10, "", 225);
        loadImage("/items/warrior_weapon/unyielding_shield.png");
        applyResistanceBonus();
    }

    public Unyielding(double tier) {
        super("Unyielding", "Increases damage resistance by 3-15% based on tier", 10, "", 225);
        this.tier = Math.max(1.0, Math.min(5.0, tier));
        loadImage("/items/warrior_weapon/unyielding_shield.png");
        applyResistanceBonus();
    }

    private void applyResistanceBonus() {
        int tierIndex = getTierIndex();
        currentResistanceBonus = RESISTANCE_BONUSES[tierIndex];
    }

    @Override
    public void onEquip(Entity user) {
        if (user instanceof Swordsman) {
            Swordsman swordsman = (Swordsman) user;
            swordsman.addDamageResistance(currentResistanceBonus);
            System.out.println(swordsman.getName() + " equipped Unyielding! Damage resistance increased by " +
                    (int)(currentResistanceBonus * 100) + "% (Tier " + (int)tier + ")");
        }
    }

    @Override
    public void onUnequip(Entity user) {
        if (user instanceof Swordsman) {
            Swordsman swordsman = (Swordsman) user;
            swordsman.removeDamageResistance(currentResistanceBonus);
            System.out.println(swordsman.getName() + " unequipped Unyielding! Damage resistance decreased by " +
                    (int)(currentResistanceBonus * 100) + "%");
        }
    }

    @Override
    public String getPassiveDescription() {
        int tierIndex = getTierIndex();
        return "Increases damage resistance by " + (int)(RESISTANCE_BONUSES[tierIndex] * 100) + "%";
    }

    public double getCurrentResistanceBonus() {
        return currentResistanceBonus;
    }

    @Override
    public <T> void activatePassive(T Entity){
        // Handled by onEquip
    }

    @Override
    public <T> void useItem(T Entity) {
        System.out.println("Cannot use weapon as item. Equip it instead.");
    }
}