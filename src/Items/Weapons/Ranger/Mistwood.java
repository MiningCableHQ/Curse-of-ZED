package Items.Weapons.Ranger;

import Items.Weapons.Weapon;
import Entities.Entity;
import Entities.Characters.Ranger;
import Moves.Move;

public class Mistwood extends Weapon {
    // Tier 1: +5 SPD, Tier 2: +10 SPD, Tier 3: +15 SPD, Tier 4: +20 SPD, Tier 5: +25 SPD
    private static final double[] SPD_BONUSES = {5, 10, 15, 20, 25};
    private double currentSpdBonus = 5;

    public Mistwood() {
        super("Mistwood", "Increases speed by 5-25 based on tier", 15, "/items/archer_weapon/mistwood.png", 265);
        loadImage("/items/archer_weapon/mistwood.png");
        applySpdBonus();
    }

    public Mistwood(double tier) {
        super("Mistwood", "Increases speed by 5-25 based on tier", 15, "/items/archer_weapon/mistwood.png", 265);
        this.tier = Math.max(1.0, Math.min(5.0, tier));
        loadImage("/items/archer_weapon/mistwood.png");
        applySpdBonus();
    }

    private void applySpdBonus() {
        int tierIndex = getTierIndex();
        currentSpdBonus = SPD_BONUSES[tierIndex];
    }

    @Override
    public void onEquip(Entity user) {
        if (user instanceof Ranger) {
            Ranger ranger = (Ranger) user;
            ranger.addFlatSpeedBonus(currentSpdBonus);
            System.out.println(ranger.getName() + " equipped Mistwood! Speed increased by " +
                    (int)currentSpdBonus + " (Tier " + (int)tier + ")");
        }
    }

    @Override
    public void onUnequip(Entity user) {
        if (user instanceof Ranger) {
            Ranger ranger = (Ranger) user;
            ranger.removeFlatSpeedBonus(currentSpdBonus);
            System.out.println(ranger.getName() + " unequipped Mistwood! Speed decreased by " +
                    (int)currentSpdBonus);
        }
    }

    @Override
    public String getPassiveDescription() {
        int tierIndex = getTierIndex();
        return "Increases speed by an additional " + (int)SPD_BONUSES[tierIndex];
    }

    public double getCurrentSpdBonus() {
        return currentSpdBonus;
    }

    @Override
    public <T> void activatePassive(T Entity){
        // Handled by onEquip
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}