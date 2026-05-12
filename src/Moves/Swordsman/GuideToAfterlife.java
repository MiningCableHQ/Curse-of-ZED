package Moves.Swordsman;

import Entities.Characters.Swordsman;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

import java.util.Random;

public class GuideToAfterlife extends Move {
    Random rand = new Random();

    public GuideToAfterlife() {
        super("Guide to Afterlife", 20, TargetType.ALL_ENEMIES);
        hasUnlocked = true;
        description = "Deal 150% of ATK to all enemies and uses up 10% of CurrentHP for every enemy hit";
    }

    @Override
    public <T> void execute(T Entity) {
        if (Entity instanceof Swordsman && Move.currentTarget != null) {
            Swordsman swordsman = (Swordsman) Entity;
            Entity enemy = Move.currentTarget;

            if(rand.nextDouble() <= swordsman.getAccuracy()){
                double currentHp = swordsman.getHp();
                double sacrificeAmount = currentHp * 0.10; //10% sacrifice amount per enemy hit

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
                double damage = totalATK * 1.50;
                double actualDamage = enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());

                setDamageDealt(actualDamage);
                setMessage(swordsman.getName() + " used " + this.name + " and dealt damage to all enemies!");
            } else {
                setDamageDealt(0);
                setMessage(swordsman.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}