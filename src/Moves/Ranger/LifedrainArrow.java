package Moves.Ranger;

import Entities.Characters.Ranger;
import Entities.Entity;
import Items.Weapons.Weapon;
import Moves.Move;
import java.util.Random;

public class LifedrainArrow extends Move {
    Random rand = new Random();

    public LifedrainArrow(){
        super("Lifedrain Arrow", 45, TargetType.ALL_ENEMIES);
        hasUnlocked = false;
        description = "Deals 50% of ATK to all enemies, and heals 150 HP for every enemy hit";
    }

    public LifedrainArrow(boolean hasUnlocked){
        super("Lifedrain Arrow", 45, TargetType.ALL_ENEMIES);
        hasUnlocked = this.hasUnlocked();
        description = "Deals 50% of ATK to all enemies, and heals 150 HP for every enemy hit";
    }

    @Override
    public <T> void execute(T Entity) {
        if(Entity instanceof Ranger && Move.currentTarget != null){
            Ranger ranger = (Ranger) Entity;
            Entity enemy = Move.currentTarget;

            if(rand.nextDouble() <= ranger.getAccuracy()){
                // Store HP before damage and heal
                double beforeRangerHp = ranger.getHp();

                // --- Damage Part -----------------------------------------------------------------------------------------
                // All 3 needed ATK stats
                double totalATK = ranger.getAttack();
                if(ranger.getWeapon() != null){
                    if(ranger.getWeapon() instanceof Items.Weapons.Weapon){
                        Weapon equippedWeapon = ranger.getWeapon();
                        totalATK += equippedWeapon.getAttack();
                    }
                }
                totalATK += this.attack; // this move's atk

                double damage = totalATK * 0.5;
                double actualDamage = enemy.takeDamage(damage, enemy.getDefense(), enemy.getDmgResistance());

                // --- Self Heal Part --------------------------------------------------------------------------------------
                ranger.heal(150); // Heals 150 for every enemy hit

                // Calculate actual heal amount
                double afterRangerHp = ranger.getHp();
                double actualHeal = afterRangerHp - beforeRangerHp;

                // Set message for battle display
                setDamageDealt(actualDamage);
                setHealAmount(actualHeal);

                String message = ranger.getName() + " used " + this.name + " and dealt damage to all enemies";

                setMessage(message);
            } else {
                setDamageDealt(0);
                setMessage(ranger.getName() + " used " + this.name + " but missed!");
            }
        }
    }
}