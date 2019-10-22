package com.rndm.rndmproject.domain;

public class Achievement {

    private String name;
    private Boolean unlocked;

    public Achievement(String name){
        this.name = name;
        unlocked = false;

    }
}
