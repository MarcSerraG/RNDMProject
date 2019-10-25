package com.rndm.rndmproject.persistence;

import com.rndm.rndmproject.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO {

    private JdbcTemplate jdbctemplate;

    private final String FIND_ALL = "select * from users";
    private final String FIND_USERNAME = FIND_ALL + " where username = ?";
    private final String INSERT_USER = "insert into user (username, password, email, date_start, is private) values (?,?,?,?,?)";
    private final String CHANGE_PASSWORD = "update user set password = ? where username = ?";
    private final String GET_PASSWORD = "select password from user where username = ?";
    private final String GET_EMAIL = "select email from user where username = ?";
    private final String GET_DATE = "select date_start from user where username = ?";


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


    public boolean isPrivate(String username){
        User user = this.getProfile(username);
        return false; // user.isPremium();
    }

    // TO DO
    public int insertUser(User user){
        return jdbctemplate.update(INSERT_USER);
    }


}
