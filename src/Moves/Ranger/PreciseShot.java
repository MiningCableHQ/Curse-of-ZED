package Moves.Ranger;

import Combat.StatusEffects.*;
import Entities.Characters.Ranger;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

import java.util.Random;

public class PreciseShot extends Move {
    Random rand = new Random();

    public PreciseShot() {
        super("Precise Shot", 20, TargetType.ENEMY);
        hasUnlocked = true;
        description = "Deal 150% of ATK to a single target";
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Ranger && Move.currentTarget != null){
            Ranger ranger = (Ranger) Entity;
            Entity enemy = Move.currentTarget;

            if(rand.nextDouble() <= ranger.getAccuracy()){
                // All 3 needed ATK stats
                double totalATK = ranger.getAttack(); // ranger atk
                if (ranger.getWeapon() != null) {
                    if (ranger.getWeapon() instanceof Items.Weapons.Weapon) {
                        Weapon equippedWeapon = ranger.getWeapon();
                        totalATK += equippedWeapon.getAttack();
                    }
                }
                totalATK += this.attack; // this move's atk

                // Multiply sum to damage multiplier
                double damage = totalATK * 1.50;//1.50
                double actualDamage = enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());

                //--- TEST ---
                //enemy.addStatusEffect(new Poison(5));
                //enemy.addStatusEffect(new Burn(1));
                //Move.currentBattle.applyFrozen(enemy);
                //Move.currentBattle.applyStun(enemy);
                //Move.currentBattle.applySlow(enemy);

                setDamageDealt(actualDamage);
                setMessage(ranger.getName() + " used " + this.name + " on " + enemy.getName() +
                        " and dealt " + (int)actualDamage + " damage!");
            } else {
                setDamageDealt(0);
                setMessage(ranger.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}