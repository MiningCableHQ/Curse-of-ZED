package Items.Weapons.Mage;

import Items.Weapons.Weapon;
import Entities.Entity;
import Moves.Move;
import java.util.Random;

public class AnkhStaff extends Weapon {
    private static final Random random = new Random();
    // Tier 1: 4%, Tier 2: 8%, Tier 3: 12%, Tier 4: 16%, Tier 5: 20%
    private static final double[] HEAL_PERCENTAGES = {0.04, 0.08, 0.12, 0.16, 0.20};

    public AnkhStaff() {
        super("Ankh Staff", "30% Chance to heal after using a move", 10, "/items/mage_weapon/ankh_staff.png", 225);
        loadImage("/items/mage_weapon/ankh_staff.png");
    }

    public AnkhStaff(double tier) {
        super("Ankh Staff", "30% Chance to heal after using a move", 10, "/items/mage_weapon/ankh_staff.png", 225);
        this.tier = Math.max(1.0, Math.min(5.0, tier));
        loadImage("/items/mage_weapon/ankh_staff.png");
    }

    @Override
    public void onAfterMove(Entity user, Move move, Entity target) {
        // 30% chance to heal after any move
        if (random.nextDouble() <= 0.30) {
            int tierIndex = getTierIndex();
            double healPercent = HEAL_PERCENTAGES[tierIndex];
            double healAmount = user.getMaxHp() * healPercent;
            user.heal(healAmount);

            setLastMessage(user.getName() + "'s Ankh Staff activated! Healed " +
                    (int)healAmount + " HP (" + (int)(healPercent * 100) + "% of max HP)! (Tier " + (int)tier + ")");
        } else {
            setLastMessage("");
        }
    }

    @Override
    public String getPassiveDescription() {
        int tierIndex = getTierIndex();
        return "30% Chance to heal " + (int)(HEAL_PERCENTAGES[tierIndex] * 100) + "% of max HP after using a move";
    }

    @Override
    public <T> void activatePassive(T Entity){
        // Handled by onAfterMove
    }

    @Override
    public <T> void useItem(T Entity) {

    }
}