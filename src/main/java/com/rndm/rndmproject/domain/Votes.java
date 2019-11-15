package com.rndm.rndmproject.domain;


public class Votes {

    private String threadID;
    private boolean positive; // If true, vote is positive. Else it is negative.
    private String user;

    public Votes(String threadID, boolean positive, String user){
        this.threadID = threadID;
        this.positive = positive;
        this.user = user;
    }

    public void setThreadID(String threadID) {
        this.threadID = threadID;
    }
    public void setPositive(boolean positive) {
        this.positive = positive;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String getThreadID(){
        return this.threadID;
    }
    public boolean getPositive() {
        return positive;
    }
    public String getUser() {
        return user;
    }

}
