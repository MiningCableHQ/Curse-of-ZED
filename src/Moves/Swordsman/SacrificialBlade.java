package Moves.Swordsman;

import Entities.Characters.Swordsman;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

public class SacrificialBlade extends Move {
    public SacrificialBlade() {
        super("Sacrificial Blade", 70);
        hasUnlocked = false;
        description = "Deal 500% of ATK as dmg to a single target and uses up 50% of CurrentHP";
    }

    @Override
    public <T> void execute(T Entity) {
        if (Entity instanceof Swordsman && Move.currentTarget != null) {
            Swordsman swordsman = (Swordsman) Entity;
            Entity enemy = Move.currentTarget;

            // Calculate sacrifice amount (50% of current HP)
            double currentHp = swordsman.getHp();
            double sacrificeAmount = currentHp * 0.50; //50% sacrifice amount

            swordsman.sacrifice(sacrificeAmount);

            // Calculate total ATK for damage
            double totalATK = swordsman.getAttack();

            // Add weapon attack if equipped
            if (swordsman.getWeapon() != null) {
                if (swordsman.getWeapon() instanceof Items.Weapons.Weapon) {
                    Weapon equippedWeapon = swordsman.getWeapon();
                    totalATK += equippedWeapon.getAttack();
                }
            }

            totalATK += this.attack; //this move's atk

            //multiply sum to dmg multiplier
            double damage = totalATK * 5;
            enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());
        }
    }
}
