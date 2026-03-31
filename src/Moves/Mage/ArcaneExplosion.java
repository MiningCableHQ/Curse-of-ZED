package Moves.Mage;

import Entities.Characters.Mage;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

public class ArcaneExplosion extends Move {
    public ArcaneExplosion(){
        super("Arcane Explosion", 20, TargetType.ALL_ENEMIES);
        hasUnlocked = true;
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
            double dmgMultiplier = 0.48;
            double damage = totalATK * dmgMultiplier;
            double actualDamage = enemy.takeDamage(damage, (int)enemy.getDefense(), enemy.getDmgResistance());
            //TODO Add AOE dmg type
        }
    }
}
