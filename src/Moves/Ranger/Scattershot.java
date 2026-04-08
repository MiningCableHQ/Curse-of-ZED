package Moves.Ranger;

import Entities.Characters.Ranger;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

public class Scattershot extends Move {
    public Scattershot() {
        super("Scattershot", 20, TargetType.ALL_ENEMIES);
        hasUnlocked = true;
        description = "Deal 55% of ATK to all enemies";
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
            double damage = totalATK * 0.55;
            double actualDamage = enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());

            setDamageDealt(actualDamage);
            setMessage(ranger.getName() + " used " + this.name + " and dealt damage to all enemies!");
        }
    }
}
