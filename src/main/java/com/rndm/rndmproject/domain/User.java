package com.rndm.rndmproject.domain;

import javax.validation.constraints.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class User {

    //Variable definition & validation constraints
    @NotEmpty(message = "username cannot be null nor empty")
    @Size(min = 8, max = 24, message = "username must be between 8 and 24 characters long")
    @Pattern(regexp = "^(?=.*[a-z])", message = "username must have at least one lowercase")
    @Pattern(regexp = "^(?=.*[A-Z])", message = "username must have at least one uppercase")
    private final String username;

    @NotEmpty(message = "email cannot be null nor empty")
    @Size(min = 12, max = 32, message = "email must be between 12 an 32 characters long")
    @Email(message = "email should be valid")
    private String email;

    @NotEmpty(message = "password cannot be null nor empty")
    @Size(min = 8, max = 64, message = "password must be between 8 an 64 characters long")
    @Pattern(regexp = "^(?=.*[a-z])", message = "password must have at least one lowercase")
    @Pattern(regexp = "^(?=.*[A-Z])", message = "password must have at least one uppercase")
    @Pattern(regexp = "^(?=.*[0-9])", message = "password must have at least one character")
    private String password;

    private List<Thread> threads;
    private List<Comment> comments;
    private Map<Thread, Boolean> pairingThreadVotes;
    private List<Achievement> achievements;
    private boolean premium;
    private boolean moderator;
    private final Date date;
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    //Constructor
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

    //Methods
    private void addThread(Thread thd){threads.add(thd);}
    private void removeThread(Thread thd){threads.remove(thd);}
    private void addComment(Comment cmnt){comments.add(cmnt);}
    private void removeComment(Comment cmnt){comments.remove(cmnt);}
    public String getDate(){return formatter.format(date);}
    public boolean isThread(Thread thread){return threads.contains(thread);}
    public boolean hasVoteFromThread (Thread thread){return pairingThreadVotes.containsKey(thread);}
    public boolean getVoteFromThread(Thread thread){
        if (pairingThreadVotes.containsKey(thread)) return pairingThreadVotes.get(thread);
        return false;
    }
    public void setVoteFromThread(Thread thread, boolean bool){pairingThreadVotes.replace(thread, bool);}
}
