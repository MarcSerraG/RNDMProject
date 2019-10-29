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
    private Comment fatherComment;
    private int id;
    private Date date;
    private List<Comment> childComments;
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    //Constructor
    public Comment(String username, String content, Comment fatherComment, Thread thread){

        this.username = username;
        this.content = content;
        this.thread = thread;
        date = new Date(System.currentTimeMillis());
        id = generateID();
        if (fatherComment == null) thread.addComment(this);
        else{
            this.fatherComment = fatherComment;
            fatherComment.addChild(this);
        }
    }

    //Methods
    private int generateID(){return username.hashCode() + date.hashCode();}
    public void addChild (Comment cmnt){childComments.add(cmnt);}
    public void removeChild (Comment cmnt){childComments.remove(cmnt);}
    public int getID() {return id;}
    public String getDate(){return formatter.format(date);}
    public String getContent(){return content;}
    public void deleteComment(){
        if(fatherComment == null) thread.removeComment(this);
        else fatherComment.removeChild(this);
    }
}
