package com.rndm.rndmproject.domain;

import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.*;


public class Thread {

    //Variable definition & validation constraints
    private String id;
    private String title;
    private Date date;
    private int upvotes;
    private int downvotes;
    private List<String> comments;
    private String text;
    private Object media;
    private String username;
    private boolean premium;
    private List<Tag> tags;
    private Category category;
    // Username - Positive (true) negative (false) - No vote (null)
    private Map<String, Boolean> mapVotes;
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

    public void setUsername(String username) { this.username = username; }

    public void setPremium(boolean premium) { this.premium = premium; }

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
        comments = new ArrayList<String>();
        mapVotes = new HashMap<String, Boolean>();
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
        comments = new ArrayList<String>();
        try {
            date = formatter.parse(data);
        }catch (Exception e){

        }
    }

    //Contructor 4

    public Thread(){
        this.media = "http//";
        this.username = "Ricard";
        this.upvotes = 20;
        this.downvotes = 20;
        date = new Date(System.currentTimeMillis());
        comments = new ArrayList<String>();
        this.id = generateID();
        this.premium = false;
        System.out.println("Identificador: " +id);
    }



    //ConstructorDAO
    public Thread (String title, String text, Object media, String username, List<Tag> tags, Category category, String id, int upvotes, int downvotes, List<String> comments, Date date) {

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

    //ConstructorDAO2
    public Thread (String id, String title, String text, Object media, String username, List<Tag> tags, Category category, String data, int upvotes, int downvotes, Collection<Votes> votes) {

        this.id = id;
        this.title = title;
        this.text = text;
        this.media = media;
        this.username = username;
        this.tags = tags;
        this.category = category;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        comments = new ArrayList<String>();
        try {
            date = formatter.parse(data);
        }catch (Exception e){

        }
        this.mapVotes = new HashMap<String, Boolean>();
        this.addManyVotes(votes);
        this.countVotes();
    }

    //Methods
    public String getID(){return id;}
    public String getDate(){return formatter.format(date);}
    public String getTitle(){return title;}
    public List<String> getComments(){return comments;}
    public String getMedia(){return (String) this.media;}
    public String getUsername() {return username;}
    public Category getCategory(){return category;}
    public boolean isPremium() { return this.premium; }
    public int getUpvotes(){return countVotes()[0];}
    public int getDownvotes(){return countVotes()[1];}
    private String generateID(){return Integer.toString(Math.abs(username.hashCode() + date.hashCode()));}//Need a modification, alphanumeric encrypt
    public void addComment(String comment){comments.add(comment);}
    public void removeComment(Comment comment){comments.remove(comment);}
    public String getText(){return text;}
    public Boolean getVote(String username) { return this.mapVotes.get(username); }

    public void setId(String newID){this.id = newID;}


    public void addVote(Votes vote){
        String username = vote.getUser();
        Boolean positive = vote.getPositive();
        this.mapVotes.put(username, positive);
    }

    public boolean removeVote(Votes vote) {
        String username = vote.getUser();
        return this.mapVotes.remove(username);
    }

    public void addManyVotes(Collection<Votes> votes){
        for (Votes vote : votes)
            addVote(vote);
    }

    public int[] countVotes() {
       Collection<Boolean> votes = this.mapVotes.values();
       int[] res = {0, 0};
       for (Boolean vote : votes) {
           if (vote)
                   ++res[0];
           else
               ++res[1];
       }
       this.upvotes = res[0];
       this.downvotes = res[1];
       return res;
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
    @Override
    public String toString(){return id +" "+title+" "+date;}

    //Convert html to text. index.html use it
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

}
