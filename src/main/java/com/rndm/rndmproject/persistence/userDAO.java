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
public class userDAO {

    private JdbcTemplate jdbctemplate;

    private final String FIND_ALL = "select * from users";
    private final String FIND_USERNAME = FIND_ALL + "where username = ?";
    private final String INSERT = "";

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

    public userDAO(JdbcTemplate jdbctemplate){
        this.jdbctemplate = jdbctemplate;
    }

    //TODO
    public User getProfile(String username){

        return null;
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
    public int insertUser(User user){
        return 0;
    }


}
