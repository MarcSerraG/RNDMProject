package com.rndm.rndmproject.domain;

public class Achievement {

    //Variable definition & validation constraints
    private String name;
    private boolean unlocked;

    //Constructor
    public Achievement(String name){
        this.name = name;
        unlocked = false;

    }

    //Methods
    public String getName(){return name;}
    public boolean getUnlocked(){return unlocked;}
}
