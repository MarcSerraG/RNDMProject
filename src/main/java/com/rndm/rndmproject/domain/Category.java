package com.rndm.rndmproject.domain;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private String name;
    private List<Thread> threads;

    public Category(String name){
        this.name = name;
        threads = new ArrayList<Thread>();
    }
}
