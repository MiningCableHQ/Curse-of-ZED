package Items.Weapons.Swordsman;

import Items.Weapons.Weapon;
import Entities.Entity;
import Entities.Characters.Swordsman;
import Moves.Move;

public class RazorEdge extends Weapon {
    // Tier 1: +20 ATK, Tier 2: +40 ATK, Tier 3: +60 ATK, Tier 4: +80 ATK, Tier 5: +100 ATK
    private static final double[] ATK_BONUSES = {20, 40, 60, 80, 100};
    private double currentAtkBonus = 20;

    public RazorEdge() {
        super("Razor Edge", "Increases base ATK by an additional 20", 20, "/items/warrior_weapon/razoredge_sword.png", 300);
        loadImage("/items/warrior_weapon/razoredge_sword.png");
        applyAtkBonus();
    }

    public RazorEdge(double tier) {
        super("Razor Edge", "Increases base ATK by an additional 20", 20, "/items/warrior_weapon/razoredge_sword.png", 300);
        this.tier = Math.max(1.0, Math.min(5.0, tier));
        loadImage("/items/warrior_weapon/razoredge_sword.png");
        applyAtkBonus();
    }

    private void applyAtkBonus() {
        int tierIndex = getTierIndex();
        currentAtkBonus = ATK_BONUSES[tierIndex];
    }

    @Override
    public void onEquip(Entity user) {
        if (user instanceof Swordsman) {
            Swordsman swordsman = (Swordsman) user;
            swordsman.addFlatAttackBonus(currentAtkBonus);
            System.out.println(swordsman.getName() + " equipped Razor Edge! ATK increased by " +
                    (int)currentAtkBonus + " (Tier " + (int)tier + ")");
        }
    }

    @Override
    public void onUnequip(Entity user) {
        if (user instanceof Swordsman) {
            Swordsman swordsman = (Swordsman) user;
            swordsman.removeFlatAttackBonus(currentAtkBonus);
            System.out.println(swordsman.getName() + " unequipped Razor Edge! ATK decreased by " +
                    (int)currentAtkBonus);
        }
    }

    @Override
    public String getPassiveDescription() {
        int tierIndex = getTierIndex();
        return "Increases ATK by an additional " + (int)ATK_BONUSES[tierIndex];
    }

    public double getCurrentAtkBonus() {
        return currentAtkBonus;
    }

    @Override
    public <T> void activatePassive(T Entity){
        // Handled by onEquip
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}