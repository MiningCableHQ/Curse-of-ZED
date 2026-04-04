package Moves.Mage;

import Entities.Characters.Mage;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

import java.lang.annotation.Target;

public class ArcaneBolt extends Move {
    public ArcaneBolt() {
        super("Arcane Bolt", 20, TargetType.ENEMY);
        hasUnlocked = true;
        description = "Deal 145% of ATK to a single target";
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
            double damage = totalATK * 1.45;
            double actualDamage = enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());
        }
    }
}
