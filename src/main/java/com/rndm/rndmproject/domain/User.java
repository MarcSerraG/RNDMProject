package com.rndm.rndmproject.domain;

import javax.validation.constraints.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class User {

    //Variable definition & validation constraints

    @Size(min = 4, max = 20, message = "username must be between 4 and 20 characters long")
    private String username;

    @NotEmpty(message = "email cannot be empty")
    @Email(message = "email should be valid")
    private String email;


    @Size(min = 8, max = 64, message = "password must be between 8 an 64 characters long")
    private String password;

    private List<Thread> threads;
    private List<Comment> comments;
    private Map<Thread, Boolean> pairingThreadVotes;
    private List<Achievement> achievements;
    private boolean premium;
    private boolean moderator;
    private Date date;
    private boolean isConnected;
  
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    SimpleDateFormat formattersimple = new SimpleDateFormat("dd/MM/yyyy");

    //Constructor 1
    public User (String username, String email, String password){

        this.username = username;
        this.email = email;
        this.password = password;
        threads = new ArrayList();
        comments = new ArrayList();
        date = new Date(System.currentTimeMillis());
        premium = false;
        moderator = false;
    }

    // Constructor 2 - LoginController
    public User () {
        this.username = "";
        this.email = "";
        this.password = "";
        threads = new ArrayList();
        comments = new ArrayList();
        date = new Date(System.currentTimeMillis());
        premium = false;
        moderator = false;
    };

    //Methods

    // Getters
    public String getUsername(){return username;}
    public String getEmail(){return email;}
    public String getDate(){return formatter.format(date);}
    public String getSimpleDate() {return formattersimple.format(date);}
    public boolean getPremium(){return premium;}
    public String getPassword(){return password;}
    public boolean getModerator(){return moderator;}
    public boolean getIsConnected(){return isConnected;}
    // Setters
    public void setUsername(String name){
        this.username = name;
    }
    public void setEmail(String email){this.email = email;}
    public void setDate(Date date){this.date = date;}
    public void setPassword(String password){this.password = password;}
    public void setPremium(boolean premium){this.premium = premium;}
    public void setModerator(boolean moderator){this.moderator = moderator;}
    public void setConnected(int connected){
        switch (connected){
            case 1: this.isConnected = true; break;
            case 0: this.isConnected = false; break;
        }
    }

  
    public void addThread(Thread thd){threads.add(thd);}
    public void removeThread(Thread thd){threads.remove(thd);}
    public List<Thread> getThreads(){return threads;}
    public void addComment(Comment cmnt){comments.add(cmnt);}
    public void removeComment(Comment cmnt){comments.remove(cmnt);}
    public boolean isThread(Thread thread){return threads.contains(thread);}
    public boolean hasVoteFromThread (Thread thread){return pairingThreadVotes.containsKey(thread);}
    public boolean getVoteFromThread(Thread thread){
        if (pairingThreadVotes.containsKey(thread)) return pairingThreadVotes.get(thread);
        return false;
    }
    public void setVoteFromThread(Thread thread, boolean bool){pairingThreadVotes.replace(thread, bool);}
}
