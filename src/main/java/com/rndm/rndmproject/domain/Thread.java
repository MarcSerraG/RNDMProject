package com.rndm.rndmproject.domain;

import org.jsoup.Jsoup;

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
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCategory(String category) {
        this.category = new Category(category);
    }

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

    //Constructor 2
    public Thread (String id, String title, String text, Object media, String username, List<Tag> tags, Category category, String data, int upvotes, int downvotes){

        this.id = id;
        this.title = title;
        this.text = text;
        this.media = media;
        this.username = username;
        this.tags = tags;
        this.category = category;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        comments = new ArrayList<Comment>();
        try {
            date = formatter.parse(data);
        }catch (Exception e){

        }
    }

    //Constructor 3
    public Thread (String title, String text, String category){
        this.id = "150";
        this.title = title;
        this.text = text;
        this.media = "http//";
        this.username = "Ricard";
        this.category = new Category(category);
        this.upvotes = 20;
        this.downvotes = 20;
        date = new Date(System.currentTimeMillis());
    }

    //Contructor 4

    public Thread(){
        this.id = "150";
        this.media = "http//";
        this.username = "Ricard";
        this.upvotes = 20;
        this.downvotes = 20;
        date = new Date(System.currentTimeMillis());
    }



    //ConstructorDAO
    public Thread (String title, String text, Object media, String username, List<Tag> tags, Category category, String id, int upvotes, int downvotes, List<Comment> comments, Date date) {

        this.title = title;
        this.text = text;
        this.media = media;
        this.username = username;
        this.tags = tags;
        this.category = category;
        this.id = id;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.comments = comments;
        this.date = date;
    }

    //Methods
    public String getID(){return id;}
    public String getDate(){return formatter.format(date);}
    public String getTitle(){return title;}
    public List<Comment> getComments(){return comments;}
    public String getMedia(){return (String) this.media;}
    public String getUsername() {return username;}
    public Category getCategory(){return category;}
    public int getUpvotes(){return upvotes;}
    public int getDownvotes(){return downvotes;}
    private String generateID(){return "generateIDThread not defined yet";}
    public void addComment(Comment comment){comments.add(comment);}
    public void removeComment(Comment comment){comments.remove(comment);}
    public String getText(){return text;}


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

    public String timeSinceCreation() {

        Long today = System.currentTimeMillis();
        Long milliDate = date.getTime();
        Long diff = today - milliDate;
        Long year = 31556952000L;
        Long month = 2592000000L;
        int week = 604800000;
        int day = 86400000;
        int hour = 3600000;
        int minute = 60000;
        int second = 1000;

        if (diff > year) return diff / year + " years";
        else if (diff > month) return diff / month + " months";
        else if (diff > week) return diff / week + " weeks";
        else if (diff > day) return diff / day + " days";
        else if (diff > hour) return diff / hour + " hours";
        else if (diff > minute) return diff / minute + " minutes";
        else return diff / second + " seconds";
    }

    //Convert html to text. index.html use it
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

}
