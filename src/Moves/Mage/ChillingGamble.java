package Moves.Mage;

import Entities.Characters.Mage;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

public class ChillingGamble extends Move {
    public ChillingGamble() {
        super("Chilling Gamble", 40);
        hasUnlocked = false;
        description = "Deals 300% of ATK as dmg to a single target, but gains Frozen status";
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
            double damage = totalATK * 3;
            enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());
        }
        //TODO FRANK gains Frozen status after using this skill
    }
}
