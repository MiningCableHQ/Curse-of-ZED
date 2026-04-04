package Moves.Mage;

import Entities.Characters.Mage;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;
import java.util.*;

public class Roulette extends Move {
    Random rand = new Random();

    public Roulette() {
        super("Roulette", 35, TargetType.ALL_ENEMIES);
        hasUnlocked = false;
        description = "Deal 15% damage to all enemies 3-10x";
    }

    @Override
    public <T> void execute(T Entity) {
        if(Entity instanceof Mage && Move.currentTarget != null){
            Mage mage = (Mage) Entity;
            Entity enemy = Move.currentTarget;

            //all 3 needed ATK stats
            double totalATK = mage.getAttack(); //mage atk
            if (mage.getWeapon() != null) {
                if (mage.getWeapon() instanceof Items.Weapons.Weapon) {
                    Weapon equippedWeapon = mage.getWeapon();
                    totalATK += equippedWeapon.getAttack();
                }
            }
            totalATK += this.attack; //this move's atk

            //multiply sum to dmg multiplier
            double damage = totalATK * 0.15 * rand.nextDouble(3, 11);
            double actualDamage = enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());
        }
    }
}
