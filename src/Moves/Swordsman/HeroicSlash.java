package Moves.Swordsman;

import Entities.Characters.Swordsman;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;

public class HeroicSlash extends Move {
    public HeroicSlash(){
        super("Heroic Slash", 20);
        hasUnlocked = true;
        description = "Deal 160% of ATK to a single target";
    }

    @Override
    public <T> void execute(T Entity) {
        if(Entity instanceof Swordsman && Move.currentTarget != null){
            Swordsman swordsman = (Swordsman) Entity;
            Entity enemy = Move.currentTarget;

            //all 3 needed ATK stats
            double totalATK = swordsman.getAttack();
            if (swordsman.getWeapon() != null) {
                if (swordsman.getWeapon() instanceof Items.Weapons.Weapon) {
                    Weapon equippedWeapon = swordsman.getWeapon();
                    totalATK += equippedWeapon.getAttack();
                }
            }
            totalATK += this.attack;

            double dmgMultiplier = 1.60;
            double damage = totalATK * dmgMultiplier;
            enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());
        }
    }
}
