package com.rndm.rndmproject.persistence;

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

@Repository
public class UserDAO {

    private JdbcTemplate jdbctemplate;

    private final String FIND_ALL = "select * from usuari";
    private final String FIND_USERNAME = FIND_ALL + " where username = ?";
    private final String INSERT_USER = "insert into usuari (username, password, email, date_start, is_private, date_sus_start) values (?,?,?,?,?,?)";
    private final String CHANGE_PASSWORD = "update usuari set password = ? where username = ?";
    private final String GET_PASSWORD = "select password from usuari where username = ?";
    private final String GET_EMAIL = "select email from usuari where username = ?";
    private final String GET_DATE = "select date_start from usuari where username = ?";
    private final String GET_PRIVATE = "select is_private from usuari where username = ?";
    private final String GET_ISCONNECTED = "select is_connected from usuari where username = ?";

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //TODO ROWMAPPER
    private User userMapper(ResultSet resultSet) throws SQLException{

        User user = new User(resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("password"));

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

    //To be revised
    public boolean getIsConnected(String username) {
        List<User> li = jdbctemplate.query(FIND_USERNAME, mapper, username);
        User u = li.get(0);
        return u.getIsConnected();
    }


    public boolean isPrivate(String name) {
        Character c = this.jdbctemplate.queryForObject(GET_PRIVATE, Character.class, name);
        if (c == '1')
            return true;
        else
            return false;
    }

    // TO DO
    public int insertUser(User user){

        Date date = new Date();

        //TODO date string format de ricard
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        return jdbctemplate.update(INSERT_USER, user.getUsername(), passwordEncoder().encode(user.getPassword()), user.getEmail(), strDate, user.getPremium(), null);
    }


}
