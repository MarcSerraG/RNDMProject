package com.rndm.rndmproject.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {

    private String username;
    private String content;
    private Date date;
    private Comment fatherComment;
    private int upvotes;
    private int downvotes;
    //SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    public Comment(String username, String content, Comment fatherComment){

        this.username = username;
        this.content = content;
        this.fatherComment = fatherComment;
        date = new Date(System.currentTimeMillis());
        upvotes = 0;
        downvotes = 0;
    }
}
