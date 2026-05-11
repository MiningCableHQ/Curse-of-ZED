package Moves.Mage;

import Entities.Characters.Mage;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;
import java.util.*;

public class Roulette extends Move {
    Random rand = new Random();

    public Roulette() {
        super("Roulette", 35, TargetType.ALL_ENEMIES, 7);
        hasUnlocked = false;
        description = "Deal 15% damage to all enemies 3-10x";
    }

    public Roulette(boolean hasUnlocked) {
        super("Roulette", 35, TargetType.ALL_ENEMIES, 7);
        this.hasUnlocked = hasUnlocked;
        description = "Deal 15% damage to all enemies 3-10x";
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

                // Random number of hits between 3 and 10
                int hits = rand.nextInt(8) + 3; // 3 to 10 inclusive
                double damagePerHit = totalATK * 0.15;
                double totalDamage = damagePerHit * hits;

                // Apply the damage
                double actualDamage = enemy.takeDamage(totalDamage, enemy.getDefense(), enemy.getDmgResistance());

                // Set message for battle display
                setDamageDealt(actualDamage);
                setMessage(mage.getName() + " used " + this.name + " on " + enemy.getName() +
                        " and dealt " + (int)actualDamage + " damage (" + hits + " hits)!");
            } else {
                setDamageDealt(0);
                setMessage(mage.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}