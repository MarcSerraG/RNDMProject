package com.rndm.rndmproject.domain;

public class Achievement {

    //Variable definition & validation constraints
    private String name;
    private Boolean unlocked;

    //Constructor
    public Achievement(String name){
        this.name = name;
        unlocked = false;

    }

    //Methods
    public String getName(){return name;}
}
