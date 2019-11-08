package com.rndm.rndmproject.domain;

import com.rndm.rndmproject.persistence.CommentDAO;
import com.rndm.rndmproject.persistence.ThreadDAO;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {

    //Variable definition & validation constraints
    @NotEmpty(message = "username cannot be null nor empty")
    private String username;
    @NotNull(message = "content cannot be null")
    private String content;
    private String thread;
    private String fatherComment;
    private Comment father;
    private String id;
    private Date date;
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    private CommentDAO commentDAO;
    //Constructor
    //We need to implement the comment addition yet
    public Comment(String username, String content, String fatherComment, String thread){

        this.username = username;
        this.content = content;
        this.thread = thread;
        date = new Date(System.currentTimeMillis());
        id = generateID();
        //addComment(id);
        if (fatherComment != null) this.fatherComment = fatherComment;
    }

    //Constructor DAO
    //We need to implement the comment addition yet
    public Comment(String id, String username, String content, String fatherComment, String thread, String date){

        this.username = username;
        this.content = content;
        this.thread = thread;
        try {
            this.date = formatter.parse(date);
        }catch (Exception e){}
        this.id = id;
        //thread.addComment(id);
        if (fatherComment != null) this.fatherComment = fatherComment;
    }

    //Methods
    private String generateID(){return username + date;}//This is temporary, alphanumeric encrypt needed
    public String getID() {return id;}
    public String getDate(){return formatter.format(date);}
    public String getContent(){return content;}
    public String getFatherComment(){return fatherComment;}
    public String getUsername(){return username;}
    public String getThread(){return thread;}
    @Override
    public String toString(){return id +" "+ content +" "+ fatherComment +" "+ username +" "+ thread +" "+ getDate();}
    //public void deleteComment(){if (fatherComment == null) thread.removeComment(this);}
}
