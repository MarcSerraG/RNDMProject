package com.rndm.rndmproject.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Thread {

    //Variable definition & validation constraints
    private String id;
    private String title;
    private Date date;
    private int upvotes;
    private int downvotes;
    private List<Comment> comments;
    private String text;
    private Object media;
    private String username;
    private List<Tag> tags;
    private Category category;
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    //Constructor
    public Thread (String title, String text, Object media, String username, List<Tag> tags, Category category){

        this.title = title;
        this.text = text;
        this.media = media;
        this.username = username;
        this.tags = tags;
        this.category = category;
        id = generateID();
        upvotes = 0;
        downvotes = 0;
        comments = new ArrayList<Comment>();
        date = new Date(System.currentTimeMillis());
    }

    //Methods
    public String getID(){return id;}
    public String getDate(){return formatter.format(date);}
    private String generateID(){return "generateIDThread not defined yet";}

    public void addUpvote(User user){

        if (user.hasVoteFromThread(this) && !user.getVoteFromThread(this)){
            upvotes++;
            downvotes --;
            user.setVoteFromThread(this, true);
        }
    }
    public void addDownvote(User user){

        if (user.hasVoteFromThread(this) && user.getVoteFromThread(this)){
            upvotes --;
            downvotes ++;
            user.setVoteFromThread(this, false);
        }
    }

}
