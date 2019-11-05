package com.rndm.rndmproject.domain;

import java.util.ArrayList;
import java.util.List;

public class Tag {

    //Variable definition & validation constraints
    private String name;
    private List<Thread> threads;

    //Constructor
    public Tag(String name){

        this.name = name;
        threads = new ArrayList();
    }

    //Methods
    public String getName(){return name;}
    public List<Thread> getThreads(){return threads;}
    public void removeThread (Thread thd){threads.remove(thd);}
}
