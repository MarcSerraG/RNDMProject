package com.rndm.rndmproject.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class userDAO {

    private JdbcTemplate jdbctemplate;

    private final String FIND_ALL = "select * from users";
    private final String FIND_USERNAME = FIND_ALL + "where username = ?";
    private final String INSERT = "";

    //TODO ROWMAPPER

    public userDAO(JdbcTemplate jdbctemplate){
        this.jdbctemplate = jdbctemplate;
    }

    //TODO
    public String getProfile(String username){
        return "";
    }

    //TODO
    public List<String> getAllUsers(){
        List<String> users = new ArrayList<String>();
        return users;
    }

    //TODO
    public boolean isPrivate(String username){
        return false;
    }

    //TODO
    public int insertUser(){
        return 0;
    }


}
