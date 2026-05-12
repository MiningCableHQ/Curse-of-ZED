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

public class Shopkeeper extends NPC{
    Inventory shop;

    public Shopkeeper(String name){
        super(name);

        shop = new Inventory();
    }

    public void generateShop(Player player){
        if(player.getLevel() <= 4){
            getShop1();
        }else if(player.getLevel() <= 7){
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
    }
    public void getShop2(){
        shop.addItem(new Healing());
        shop.addItem(new Purific());
        shop.addItem(new Power());
        shop.addItem(new Hardening());
        shop.addItem(new Blinding());
        shop.addItem(new Dulling());
        shop.addItem(new Softening());
    }
    public void getShop3(){
        shop.addItem(new GreaterHealing());
        shop.addItem(new Purific());
        shop.addItem(new GreaterPower());
        shop.addItem(new GreaterHardening());
        shop.addItem(new GreaterBlinding());
        shop.addItem(new GreaterDulling());
        shop.addItem(new GreaterSoftening());
    }

    public Inventory getShop(){
        return shop;
    }
}