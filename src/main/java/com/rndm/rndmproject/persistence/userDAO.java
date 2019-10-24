package com.rndm.rndmproject.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class userDAO {

    private JdbcTemplate jdbctemplate;

    private final String FIND_ALL = "select * from users";
    private final String FIND_USERNAME = FIND_ALL + " where username = ?";
    //private final String INSERT_THREAT = "insert into Threads (id_thread, title, content, is_private) values (?,?,?,?)";
    private final String INSERT_USER = "insert into user (username, password, email, date_start, is private) values (?,?,?,?,?)";
    private final String CHANGE_PASSWORD = "update user set password = ? where username = ?";
    private final String GET_PASSWORD = "select password from user where username = ?";
    private final String GET_EMAIL = "select email from user where username = ?";
    private final String GET_DATE = "select date_start from user where username = ?";
   //private final String NUM_THREADS = "select count(*) from thread where username = ?";


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
