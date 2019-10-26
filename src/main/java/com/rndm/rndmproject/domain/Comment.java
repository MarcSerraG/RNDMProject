package com.rndm.rndmproject.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Comment {

    //Variable definition & validation constraints
    @NotEmpty(message = "username cannot be null nor empty")
    private String username;
    @NotNull(message = "content cannot be null")
    private String content;
    private Thread thread;
    private String id;
    private Date date;
    private List<Comment> childComments;
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    //Constructor
    public Comment(String username, String content, Comment fatherComment){

        this.username = username;
        this.content = content;
        date = new Date(System.currentTimeMillis());
        id = generateID();
    }

    //Methods
    private String generateID(){return "generateIDComment not defined yet";}
    public void addChild (Comment cmnt){childComments.add(cmnt);}
    public void removeChild (Comment cmnt){childComments.remove(cmnt);}
    public String getDate(){return formatter.format(date);}
    public String getContent(){return content;}
}
