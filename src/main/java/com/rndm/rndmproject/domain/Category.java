package com.rndm.rndmproject.domain;

import java.util.ArrayList;
import java.util.List;

public class Category {

    //Variable definition & validation constraints
    private String name;
    private List<Thread> threads;

    //Constructor
    public Category(String name){
        this.name = name;
        threads = new ArrayList<Thread>();
    }

    //Methods
    public String getName(){return name;}

    private List<Thread> getThreads(){return threads;}

    public void removeThread(Thread thd){threads.remove(thd);}
    public boolean addThreadtoCategory(Thread thd){return threads.add(thd);}





}
