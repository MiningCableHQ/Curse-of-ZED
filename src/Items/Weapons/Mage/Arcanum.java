package Items.Weapons.Mage;

import Items.Weapons.Weapon;
import Entities.Entity;
import Entities.Characters.Mage;
import Moves.Move;

public class Arcanum extends Weapon {
    // Tier 1: +20 ATK, Tier 2: +40 ATK, Tier 3: +60 ATK, Tier 4: +80 ATK, Tier 5: +100 ATK
    private static final double[] ATK_BONUSES = {20, 40, 60, 80, 100};
    private double currentAtkBonus = 30;

    public Arcanum(){
        super("Arcanum", "Increases base ATK by 20-100 based on tier", 20, "/items/mage_weapon/arcanum_staff.png", 300);
        loadImage("/items/mage_weapon/arcanum_staff.png");
        applyAtkBonus();
    }

    public Arcanum(double tier){
        super("Arcanum", "Increases base ATK by 20-100 based on tier", 20, "/items/mage_weapon/arcanum_staff.png", 300);
        this.tier = Math.max(1.0, Math.min(5.0, tier));
        loadImage("/items/mage_weapon/arcanum_staff.png");
        applyAtkBonus();
    }

    private void applyAtkBonus() {
        int tierIndex = getTierIndex();
        currentAtkBonus = ATK_BONUSES[tierIndex];
    }

    @Override
    public void onEquip(Entity user) {
        if (user instanceof Mage) {
            Mage mage = (Mage) user;
            mage.addFlatAttackBonus(currentAtkBonus);
            System.out.println(mage.getName() + " equipped Arcanum! ATK increased by " +
                    (int)currentAtkBonus + " (Tier " + (int)tier + ")");
        }
    }

    @Override
    public void onUnequip(Entity user) {
        if (user instanceof Mage) {
            Mage mage = (Mage) user;
            mage.removeFlatAttackBonus(currentAtkBonus);
            System.out.println(mage.getName() + " unequipped Arcanum! ATK decreased by " +
                    (int)currentAtkBonus);
        }
    }

    @Override
    public String getPassiveDescription() {
        int tierIndex = getTierIndex();
        return "Increases base ATK by " + (int)ATK_BONUSES[tierIndex];
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