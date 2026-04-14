package Moves.Mage;

import Entities.Characters.Mage;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

import java.util.Random;

public class ChillingGamble extends Move {
    Random rand = new Random();

    public ChillingGamble() {
        super("Chilling Gamble", 40, TargetType.ENEMY);
        hasUnlocked = false;
        description = "Deals 300% of ATK as dmg to a single target, but gains Frozen status";
    }

    public ChillingGamble(boolean hasUnlocked) {
        super("Chilling Gamble", 40);
        this.hasUnlocked = hasUnlocked;
        description = "Deals 300% of ATK as dmg to a single target, but gains Frozen status";
    }

    @Override
    public <T> void execute(T Entity) {
        if(Entity instanceof Mage && Move.currentTarget != null){
            Mage mage = (Mage) Entity;
            Entity enemy = Move.currentTarget;

            if(rand.nextDouble() <= mage.getAccuracy()){
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
                double damage = totalATK * 3;
                double actualDamage = enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());

                // TODO FRANK Apply Frozen status to the Mage

                // Set message for battle display
                setDamageDealt(actualDamage);
                setMessage(mage.getName() + " used " + this.name + " on " + enemy.getName() +
                        " and dealt " + (int)actualDamage + " damage, but gained Frozen status!");
            } else {
                setDamageDealt(0);
                setMessage(mage.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}