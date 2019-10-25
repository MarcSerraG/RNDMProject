package com.rndm.rndmproject.persistence;

import com.rndm.rndmproject.domain.Category;
import com.rndm.rndmproject.domain.Thread;
import org.springframework.jdbc.core.JdbcTemplate;
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

    //TODO ROWMAPPER
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


}
