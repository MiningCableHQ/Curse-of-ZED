package Moves.Swordsman;

import Entities.Characters.Swordsman;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;
import java.util.Random;

public class MultiStrike extends Move {
    Random rand = new Random();

    public MultiStrike() {
        super("Multi Strike", 40, TargetType.ALL_ENEMIES, 3);
        hasUnlocked = false;
        description = "Deals 20% of ATK to all enemies 5-10x";
    }

    public MultiStrike(boolean hasUnlocked) {
        super("Multi Strike", 40, TargetType.ALL_ENEMIES, 7);
        this.hasUnlocked = hasUnlocked;
        description = "Deals 20% of ATK to all enemies 5-10x";
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
                int hits = rand.nextInt(6) + 5; // 5 to 10 inclusive
                double damagePerHit = totalATK * 0.20;
                double totalDamage = damagePerHit * hits;
                double actualDamage = enemy.takeDamage(totalDamage, enemy.getDefense(), enemy.getDmgResistance());

                setDamageDealt(actualDamage);
                setMessage(swordsman.getName() + " used " + this.name + " and dealt damage to all enemies!" +
                        " damage (" + hits + " hits)!");
            } else {
                setDamageDealt(0);
                setMessage(swordsman.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}
