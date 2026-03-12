package Entities.Enemies;

import Entities.Entity;

public class Enemy extends Entity {
    protected int expYield;

    public Enemy(){
        accuracy = 0.90;
    }

    public Enemy(String name, double attack){
    }

    //Getters and Setters
    public int getExpYield(){
        return  expYield;
    }
}
