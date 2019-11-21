package com.rndm.rndmproject.domain;

import com.rndm.rndmproject.persistence.CommentDAO;
import com.rndm.rndmproject.persistence.ThreadDAO;
import org.springframework.beans.factory.annotation.Autowired;
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
    private String fatherContent;
    private String fatherUser;
    private String id;
    private Date date;
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");


    //Constructor DAO
    //We need to implement the comment addition yet
    public Comment(String id, String username, String content, String fatherComment, String thread, String date, String fatherContent, String fatherUser){

        this.username = username;
        this.content = content;
        this.thread = thread;
        try {
            this.date = formatter.parse(date);
        }catch (Exception e){}
        this.id = id;
        this.fatherContent = fatherContent;
        this.fatherUser = fatherUser;
        if (fatherComment != null){
            this.fatherComment = fatherComment;
        }
    }

    //new Comment of a Thread Constructor
    public Comment(String thread, String username){
        this.thread = thread;
        this.username = username;
        this.date = new Date(System.currentTimeMillis());
        this.id = generateID();
        this.fatherContent = "";
        this.fatherComment = "";
    }

    //new Comment of a Comment Constructor
    public Comment(String thread, String username, String fatherComment, String fatherContent){
        this.thread = thread;
        this.username = username;
        this.date = new Date(System.currentTimeMillis());
        this.id = generateID();
        this.fatherContent = fatherContent;
        this.fatherComment = fatherComment;
    }

    //Methods
    private String generateID(){return Integer.toString(username.hashCode() + date.hashCode());}//This is temporary, alphanumeric encrypt needed
    public String getID() {return id;}
    public String getDate(){return formatter.format(date);}
    public String getContent(){return content;}
    public String getFatherComment(){
        return fatherComment;
    }
    public String getFatherContent(){
        return fatherContent;
    }
    public String getFatherUser() { return fatherUser;}
    public String getUsername(){return username;}
    public String getThread(){return thread;}
    @Override
    public String toString(){return id +" "+ content +" "+ fatherComment +" "+ username +" "+ thread +" "+ getDate();}
    //public void deleteComment(){if (fatherComment == null) thread.removeComment(this);}


    public void setUsername(String username){
        this.username = username;
        this.id= generateID();
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setThread(String thread){
        this.thread = thread;
    }

    public void setFatherComment(String fatherComment){
        this.fatherComment = fatherComment;
    }

    public void setFatherContent(String fatherContent){
        this.fatherContent = fatherContent;
    }

    public void setFatherUser (String fatherUser){
        this.fatherUser = fatherUser;
    }
}
