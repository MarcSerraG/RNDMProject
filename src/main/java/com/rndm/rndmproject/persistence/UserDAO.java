package com.rndm.rndmproject.persistence;

import com.rndm.rndmproject.REST.Sys;
import com.rndm.rndmproject.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Random;

@Repository
public class UserDAO {

    private JdbcTemplate jdbctemplate;

    private final String FIND_ALL = "select * from usuari";
    private final String FIND_USERNAME = FIND_ALL + " where username = ?";
    private final String INSERT_USER = "insert into usuari (username, password, email, date_start, is_private, is_moderator, date_sus_start, image) values (?,?,?,?,?,?,?,?)";
    private final String CHANGE_PASSWORD = "update usuari set password = ? where username = ?";
    private final String GET_PASSWORD = "select password from usuari where username = ?";
    private final String GET_EMAIL = "select email from usuari where username = ?";
    private final String GET_DATE = "select date_start from usuari where username = ?";
    private final String GET_PRIVATE = "select is_private from usuari where username = ?";
    private final String GET_MOD = "select is_moderator from usuari where username = ?";
    private final String GET_ISCONNECTED = "select is_connected from usuari where username = ?";
    private final String GET_IMAGE = "select image from usuari where username = ?";
    private final String GET_CONNECTED = "select connected from usuari where username = ?";
    private final String CHANGE_STATUS = "update usuari set connected = ? where username = ?";

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //TODO ROWMAPPER
    private User userMapper(ResultSet resultSet) throws SQLException{

        User user = new User(resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getBoolean("is_private"));

        return user;
    };

    private final RowMapper<User> mapper = (resultSet,i) -> {
        return userMapper(resultSet);
    };

    public UserDAO(JdbcTemplate jdbctemplate){
        this.jdbctemplate = jdbctemplate;
    }

    public User getProfile(String username){
        return jdbctemplate.queryForObject(FIND_USERNAME, mapper, username);
    }


    public List<User> getAllUsers(){
        return jdbctemplate.query(FIND_ALL, mapper);
    }


    public boolean isPrivate(String name) {return this.jdbctemplate.queryForObject(GET_PRIVATE, Boolean.class, name);}

    public boolean isModerator(String name){return jdbctemplate.queryForObject(GET_MOD, Boolean.class, name);}

    public String getImage(String name){return this.jdbctemplate.queryForObject(GET_IMAGE, String.class, name);}

    // TO DO
    public int insertUser(User user){

        Random random = new Random();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        return jdbctemplate.update(INSERT_USER, user.getUsername(), passwordEncoder().encode(user.getPassword()), user.getEmail(), strDate, user.getPremium(),user.getModerator(), null, "images/avatar" + (random.nextInt(8) + 1) +".png");
    }

    public int IsUserconnected(String username){
        return jdbctemplate.queryForObject(GET_CONNECTED, Integer.class, username);
    }

    public int ChangeConnected (String username, int status){
        return jdbctemplate.update(CHANGE_STATUS, status, username);
    }


}
