package Moves.Swordsman;

import Entities.Characters.Swordsman;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

import java.util.Random;

public class SweepingStrike extends Move {
    Random rand = new Random();

    public SweepingStrike(){
        super("Sweeping Strike", 20, TargetType.ALL_ENEMIES);
        hasUnlocked = true;
        description = "Deal 80% of ATK to all enemies";
    }

    @Override
    public <T> void execute(T Entity) {
        if(Entity instanceof Swordsman && Move.currentTarget != null){
            Swordsman swordsman = (Swordsman) Entity;
            Entity enemy = Move.currentTarget;

            if(rand.nextDouble() <= swordsman.getAccuracy()){
                //all 3 needed ATK stats
                double totalATK = swordsman.getAttack(); //swordsman atk
                if (swordsman.getWeapon() != null) {
                    if (swordsman.getWeapon() instanceof Items.Weapons.Weapon) {
                        Weapon equippedWeapon = swordsman.getWeapon();
                        totalATK += equippedWeapon.getAttack();
                    }
                }

                totalATK += this.attack; //this move's atk

                //multiply sum to dmg multiplier
                double damage = totalATK * 0.80;
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
