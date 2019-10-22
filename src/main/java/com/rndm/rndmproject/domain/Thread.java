package com.rndm.rndmproject.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Thread {

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
    //SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    public Thread (String id, String title, String text, Object media, String username, List<Tag> tags, Category category){

        this.id = id;
        this.title = title;
        this.text = text;
        this.media = media;
        this.username = username;
        this.tags = tags;
        this.category = category;
        upvotes = 0;
        downvotes = 0;
        comments = new ArrayList<Comment>();
        date = new Date(System.currentTimeMillis());
    }
}
