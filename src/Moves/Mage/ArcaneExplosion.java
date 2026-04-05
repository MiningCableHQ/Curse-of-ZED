package Moves.Mage;

import Entities.Characters.Mage;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

public class ArcaneExplosion extends Move {
    public ArcaneExplosion(){
        super("Arcane Explosion", 20, TargetType.ALL_ENEMIES);
        hasUnlocked = true;
        description = "Deal 48% of ATK to all enemies";
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
            double damage = totalATK * 0.48;
            enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());
        }
    }
}
