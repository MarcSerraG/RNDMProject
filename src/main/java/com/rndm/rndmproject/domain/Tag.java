package com.rndm.rndmproject.domain;

import java.util.ArrayList;
import java.util.List;

public class Tag {

    private String name;
    private List<Thread> threads;

    public Tag(String name){

        this.name = name;
        threads = new ArrayList();
    }
}
