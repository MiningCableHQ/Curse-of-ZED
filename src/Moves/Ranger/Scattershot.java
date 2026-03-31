package Moves.Ranger;

import Entities.Characters.Ranger;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

public class Scattershot extends Move {
    public Scattershot() {
        super("Scattershot", 20);
        hasUnlocked = true;
    }

    @Override
    public <T> void execute(T Entity){
        if(Entity instanceof Ranger && Move.currentTarget != null){
            Ranger ranger = (Ranger) Entity;
            Entity enemy = Move.currentTarget;

            //all 3 needed ATK stats
            double totalATK = ranger.getAttack(); //ranger atk
            if (ranger.getWeapon() != null) {
                if (ranger.getWeapon() instanceof Items.Weapons.Weapon) {
                    Weapon equippedWeapon = ranger.getWeapon();
                    totalATK += equippedWeapon.getAttack();
                }
            }
            totalATK += this.attack; //this move's atk

            //multiply sum to dmg multiplier
            double dmgMultiplier = 0.55;
            double damage = totalATK * dmgMultiplier;
            double actualDamage = enemy.takeDamage(damage, (int)enemy.getDefense(), enemy.getDmgResistance());
            //TODO Add AOE dmg type
        }
    }
}
