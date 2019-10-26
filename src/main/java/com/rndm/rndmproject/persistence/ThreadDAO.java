package com.rndm.rndmproject.persistence;

import com.rndm.rndmproject.domain.Category;
import com.rndm.rndmproject.domain.Thread;
import com.rndm.rndmproject.domain.User;
import com.rndm.rndmproject.persistence.UserDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ThreadDAO {

    private JdbcTemplate jdbctemplate;

    private final String INSERT_THREAD = "insert into thread " +
            "(id_thread, title, content, image_url, is_private, users_username, category_name) " +
            "values (?,?,?,?,?,?,?)";
    private final String NUM_THREADS = "select count(*) from thread where users_username = ?";
    private final String GET_AUTHOR = "select users_username from thread where id_thread = ?";
    private final String FIND_THREAD = "select * from thread where id_thread = ?";

    //TODO
    private Thread threadMapper(ResultSet resultSet) throws SQLException {

        Thread thread = new Thread(resultSet.getString("id"),
                resultSet.getString("title"),
                resultSet.getString("content"),
                resultSet.getString("image_url"),
                resultSet.getString("users_username"),
                null,
                new Category(resultSet.getString("category_name")),
                0,
                0);

        return thread;
    };

    private final RowMapper<Thread> mapper = (resultSet, i) -> {
        return threadMapper(resultSet);
    };

    public ThreadDAO(JdbcTemplate jdbctemplate){
        this.jdbctemplate = jdbctemplate;
    }

    public Thread getThread(String id){
        return jdbctemplate.queryForObject(FIND_THREAD, mapper, id);
    }

    /* Returns Author's username */
    public String getAuthor(String id) {
        return this.jdbctemplate.queryForObject(GET_AUTHOR, String.class, id);
    }



}
