package Moves.Swordsman;

import Entities.Characters.Swordsman;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

import java.util.Random;

public class SacrificialBlade extends Move {
    Random rand = new Random();

    public SacrificialBlade() {
        super("Sacrificial Blade", 70);
        hasUnlocked = false;
        description = "Deal 500% of ATK as dmg to a single target and uses up 50% of CurrentHP";
    }

    public SacrificialBlade(boolean hasUnlocked) {
        super("Sacrificial Blade", 70);
        hasUnlocked = this.hasUnlocked;
        description = "Deal 500% of ATK as dmg to a single target and uses up 50% of CurrentHP";
    }

    @Override
    public <T> void execute(T Entity) {
        if (Entity instanceof Swordsman && Move.currentTarget != null) {
            Swordsman swordsman = (Swordsman) Entity;
            Entity enemy = Move.currentTarget;

            if(rand.nextDouble() <= swordsman.getAccuracy()){
                // Store values before execution for message
                double beforePlayerHp = swordsman.getHp();

                // Calculate sacrifice amount (50% of current HP)
                double sacrificeAmount = beforePlayerHp * 0.50;
                swordsman.sacrifice(sacrificeAmount);

                // Calculate total ATK for damage
                double totalATK = swordsman.getAttack();

                if (swordsman.getWeapon() != null) {
                    if (swordsman.getWeapon() instanceof Items.Weapons.Weapon) {
                        Weapon equippedWeapon = swordsman.getWeapon();
                        totalATK += equippedWeapon.getAttack();
                    }
                }

                totalATK += this.attack; // this move's atk

                double damage = totalATK * 5;

                // Apply damage to enemy
                double actualDamage = enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());

                // Calculate actual HP lost
                double afterPlayerHp = swordsman.getHp();
                double hpLost = beforePlayerHp - afterPlayerHp;

                setDamageDealt(actualDamage);
                setMessage(swordsman.getName() + " used " + this.name + " on " + enemy.getName() +
                        " and dealt " + String.format("%d", (int)actualDamage) + " damage, sacrificing " +
                        String.format("%d", (int)hpLost) + " HP!");
            } else {
                setDamageDealt(0);
                setMessage(swordsman.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}