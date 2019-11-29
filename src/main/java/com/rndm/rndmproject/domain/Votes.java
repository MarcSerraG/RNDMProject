package com.rndm.rndmproject.domain;


import java.util.Comparator;

public class Votes {

    private String threadID;

    // If true, vote is positive. If false, it is negative.
    // If null, the user has not voted yet.
    private Boolean positive;
    private String user;

    public Votes(String threadID, Boolean positive, String user){
        this.threadID = threadID;
        this.positive = positive;
        this.user = user;
    }

    public Votes(String threadID, String user) {
        this(threadID, null, user);
    }

    public void setThreadID(String threadID) {
        this.threadID = threadID;
    }
    public void setPositive(Boolean positive) {
        this.positive = positive;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String getThreadID(){
        return this.threadID;
    }
    public Boolean getPositive() {
        return positive;
    }
    public String getUser() {
        return user;
    }

    public boolean hasVoted() {
        if (this.positive == null)
            return false;
        else
            return true;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Votes)) return false;
        Votes obj = (Votes) o;

        return (obj.threadID.equals(obj.threadID) && obj.user.equals(this.user) && obj.positive == this.positive);
    }

}
