package com.rndm.rndmproject.domain;


public class Votes {

    private String threadID;
    private String titleThread;
    private int upVotes;
    private int downVotes;
    private String threadUser;

    public Votes(String threadID, String titleThread, int upVotes, int downVotes, String threadUser){
        this.threadID = threadID;
        this.titleThread = titleThread;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.threadUser = threadUser;
    }

    public String getThreadID(){
        return this.threadID;
    }

    public int getUpVotes() {
        return this.upVotes;
    }

    public String getTitleThread() {
        return this.titleThread;
    }

 public String getThreadUser(){
        return this.threadUser;
 }
}
