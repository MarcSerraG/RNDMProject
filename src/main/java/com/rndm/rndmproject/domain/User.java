package com.rndm.rndmproject.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

    private final String username;
    private String email;
    private String password;
    private List<Thread> threads;
    private List<Comment> comments;
    private List<Achievement> achievements;
    private boolean premium;
    private boolean moderator;
    private Date date;
    //SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    public User (String username, String email, String password){

        this.username = username;
        this.email = email;
        this.password = password;
        threads = new ArrayList<Thread>();
        comments = new ArrayList<Comment>();
        date = new Date(System.currentTimeMillis());
        premium = false;
        moderator = false;
    }


}
