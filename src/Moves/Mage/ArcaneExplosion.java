package Moves.Mage;

import Entities.Characters.Mage;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

import java.util.Random;

public class ArcaneExplosion extends Move {
    Random rand = new Random();

    public ArcaneExplosion(){
        super("Arcane Explosion", 20, TargetType.ALL_ENEMIES);
        hasUnlocked = true;
        description = "Deal 110% of ATK to all enemies";
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
                double damage = totalATK * 1.1;
                double actualDamage = enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());

                setDamageDealt(actualDamage);
                setMessage(mage.getName() + " used " + this.name + " and dealt damage to all enemies!");
            } else {
                setDamageDealt(0);
                setMessage(mage.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}