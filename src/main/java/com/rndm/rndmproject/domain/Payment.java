package com.rndm.rndmproject.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class Payment {

    //Variable definition & validation constraints
    @NotNull(message = "id cannot be null")
    @Size(min = 3, max = 15, message = "name must be between 4 an 15 characters long")
    @Pattern(regexp = "^\\w+", message = "must have alphanumeric characters")
    private String id;
    private String issue;
    private User user;
    private float cost;
    private Date initialDate;
    private Date finalDate;
    private boolean periodic;
    private String paymentPlatform;//Provisional type to be changed

    //Constructor
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

    //Methods

}
