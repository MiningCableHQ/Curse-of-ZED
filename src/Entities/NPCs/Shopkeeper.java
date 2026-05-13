package Entities.NPCs;

import Entities.Characters.Player;
import Items.Consumables.Buff.*;
import Items.Consumables.Debuff.Blinding.Blinding;
import Items.Consumables.Debuff.Blinding.GreaterBlinding;
import Items.Consumables.Debuff.Blinding.LesserBlinding;
import Items.Consumables.Debuff.Dulling.Dulling;
import Items.Consumables.Debuff.Dulling.GreaterDulling;
import Items.Consumables.Debuff.Dulling.LesserDulling;
import Items.Consumables.Debuff.Softening.GreaterSoftening;
import Items.Consumables.Debuff.Softening.LesserSoftening;
import Items.Consumables.Debuff.Softening.Softening;
import Items.Consumables.Heal.GreaterHealing;
import Items.Consumables.Heal.Healing;
import Items.Consumables.Heal.LesserHealing;
import Items.Consumables.Heal.Purific;
import Items.Inventory;
import Items.Weapons.Mage.AnkhStaff;
import Items.Weapons.Mage.Arcanum;
import Items.Weapons.Mage.ElementalCodex;
import Items.Weapons.Ranger.Mistwood;
import Items.Weapons.Ranger.Slowstring;
import Items.Weapons.Ranger.Swiftwind;
import Items.Weapons.Swordsman.RazorEdge;
import Items.Weapons.Swordsman.Stunblade;
import Items.Weapons.Swordsman.Unyielding;

public class Shopkeeper extends NPC{
    Inventory shop;

    public Shopkeeper(String name){
        super(name);

        shop = new Inventory();
    }

    public void generateShop(Player player){
        if(player.getLevel() <= 3){
            getShop1();
        }else if(player.getLevel() <= 6){
            getShop2();
        }else if(player.getLevel() <= 10){
            getShop3();
        }
    }

    public void getShop1(){
        shop.addItem(new LesserHealing());
        shop.addItem(new Purific());
        shop.addItem(new LesserPower());
        shop.addItem(new LesserHardening());
        shop.addItem(new LesserBlinding());
        shop.addItem(new LesserDulling());
        shop.addItem(new LesserSoftening());
        shop.addItem(new RazorEdge(1));
        shop.addItem(new Stunblade(5));
        shop.addItem(new Slowstring(5));
        shop.addItem(new Mistwood(5));
        shop.addItem(new Arcanum(1));
        shop.addItem(new ElementalCodex(5));
        shop.addItem(new AnkhStaff(5));
        shop.addItem(new Swiftwind());
        shop.addItem(new Unyielding(5));
    }
    public void getShop2(){
        shop.addItem(new Healing());
        shop.addItem(new Purific());
        shop.addItem(new Power());
        shop.addItem(new Hardening());
        shop.addItem(new Blinding());
        shop.addItem(new Dulling());
        shop.addItem(new Softening());
        shop.addItem(new RazorEdge(1));
        shop.addItem(new Stunblade(5));
        shop.addItem(new Slowstring(5));
        shop.addItem(new Mistwood(5));
        shop.addItem(new Arcanum(1));
        shop.addItem(new ElementalCodex(5));
        shop.addItem(new AnkhStaff(5));
        shop.addItem(new Swiftwind());
        shop.addItem(new Unyielding(5));
    }
    public void getShop3(){
        shop.addItem(new GreaterHealing());
        shop.addItem(new Purific());
        shop.addItem(new GreaterPower());
        shop.addItem(new GreaterHardening());
        shop.addItem(new GreaterBlinding());
        shop.addItem(new GreaterDulling());
        shop.addItem(new GreaterSoftening());
        shop.addItem(new RazorEdge(1));
        shop.addItem(new Stunblade(5));
        shop.addItem(new Slowstring(5));
        shop.addItem(new Mistwood(5));
        shop.addItem(new Arcanum(1));
        shop.addItem(new ElementalCodex(5));
        shop.addItem(new AnkhStaff(5));
        shop.addItem(new Swiftwind());
        shop.addItem(new Unyielding(5));
    }

    public Inventory getShop(){
        return shop;
    }
}