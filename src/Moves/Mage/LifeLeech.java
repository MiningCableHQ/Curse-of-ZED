package Moves.Mage;

import Entities.Characters.Mage;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

import java.util.Random;

public class LifeLeech extends Move {
    Random rand = new Random();

    public LifeLeech() {
        super("Life Leech", 30, TargetType.ENEMY, 3);
        hasUnlocked = false;
        description = "Deals 130% of ATK as damage to a single target and heals 5-15% of MaxHP";
    }

    public LifeLeech(boolean hasUnlocked) {
        super("Life Leech", 30, TargetType.ENEMY, 3);
        this.hasUnlocked = hasUnlocked;
        description = "Deals 130% of ATK as damage to a single target and heals 5-15% of MaxHP";
    }

    @Override
    public <T> void execute(T Entity) {
        if(Entity instanceof Mage && Move.currentTarget != null){
            Mage mage = (Mage) Entity;
            Entity enemy = Move.currentTarget;

            if(rand.nextDouble() <= mage.getAccuracy()){
                double beforeMageHp = mage.getHp();

                // --- Damage Part -----------------------------------------------------------------------------------------
                // All 3 needed ATK stats
                double totalATK = mage.getAttack(); // mage atk
                if (mage.getWeapon() != null) {
                    if (mage.getWeapon() instanceof Items.Weapons.Weapon) {
                        Weapon equippedWeapon = mage.getWeapon();
                        totalATK += equippedWeapon.getAttack();
                    }
                }
                totalATK += this.attack; // this move's atk

                // Multiply sum to damage multiplier
                double damage = totalATK * 1.50;
                double actualDamage = enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());

                // --- Self Heal Part --------------------------------------------------------------------------------------
                double maxHp = mage.getMaxHp();
                double healAmount = maxHp * (0.05 + (Math.random() * 0.10));
                mage.heal(healAmount);

                // Calculate actual heal amount
                double afterMageHp = mage.getHp();
                double actualHeal = afterMageHp - beforeMageHp;

                // Set message for battle display
                setDamageDealt(actualDamage);
                setHealAmount(actualHeal);

                String message = mage.getName() + " used " + this.name + " on " + enemy.getName()
                        + " and dealt " + (int)actualDamage + " damage";

                if (actualHeal > 1) {
                    message += ", and healed " + (int)actualHeal + " HP!";
                } else {
                    message += ", but was already at full health!";
                }

                setMessage(message);
            } else {
                setDamageDealt(0);
                setMessage(mage.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}