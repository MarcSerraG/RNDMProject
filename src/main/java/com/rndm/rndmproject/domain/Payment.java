package com.rndm.rndmproject.domain;

import java.util.Date;

public class Payment {

    private String id;
    private String issue;
    private User user;
    private float cost;
    private Date initialDate;
    private Date finalDate;
    private boolean periodic;
    private String paymentPlatform;//Provisional type to be changed


    public Payment(String id, String issue, User user, Date finalDate, boolean periodic, String paymentPlatform){

        this.id = id;
        this.issue = issue;
        this.user = user;
        this.finalDate = finalDate;
        this.periodic = periodic;
        initialDate = new Date(System.currentTimeMillis());
        cost = 0;//Ask Database for the actual cost
        this.paymentPlatform = paymentPlatform;


    }

}
