
package com.rndm.rndmproject.persistence;

import com.rndm.rndmproject.domain.Category;
import com.rndm.rndmproject.domain.Thread;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ThreadDAO {

    private JdbcTemplate jdbctemplate;

    private final String INSERT_THREAD = "insert into thread " +
            "(id_thread, title, content, image_url, is_private, users_username, category_name, date_creation) " +
            "values (?,?,?,?,?,?,?,?)";
    private final String NUM_THREADS = "select count(*) from thread where users_username = ?";
    private final String GET_AUTHOR = "select users_username from thread where id_thread = ?";
    private final String FIND_THREAD = "select * from thread where id_thread = ?";
    private final String GET_PRIVATE = "select is_private from thread where id_thread = ?";
    private final String FIND_USER_THREADS = "select * from thread where users_username = ?";
    private final String FIRST_THREADS = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread where is_private = '0' order by date_creation DESC limit ?" ; //linia per h2
    private final String FINDX_THREADS = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread where is_private = '0' limit 10 offset ?" ; //linia per h2
    private final String FIND_THREAD_CATEGORY = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread where is_private = '0' and category_name = ? limit 10" ;
    private final String FIND_THREADS_BYNAME = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread where title like '%?%' ";


    //TODO
    private Thread threadMapper(ResultSet resultSet) throws SQLException {

        Thread thread = new Thread(resultSet.getString("id_thread"),
                resultSet.getString("title"),
                resultSet.getString("content"),
               resultSet.getString("image_url"),
               resultSet.getString("users_username"),
               null,
                new Category(resultSet.getString("category_name")),
                resultSet.getString("date_creation"),
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

    public int insert(Thread thread){
        return jdbctemplate.update(INSERT_THREAD, thread.getID(), thread.getTitle(), thread.getText(), thread.getMedia(), 0, thread.getUsername(), thread.getCategory().getName(), (String)thread.getDate());
    }

    public List<Thread> findFirstTen(){
        return jdbctemplate.query(FIRST_THREADS, mapper, 10);
    }

    public List<Thread> findXThreads(int page){
        return jdbctemplate.query(FINDX_THREADS, mapper, page * 10);
    }

    public List<Thread> findThreadByCategory(String Category){
        return jdbctemplate.query(FIND_THREAD_CATEGORY, mapper, Category);
    }

    public List<Thread> findThreadByName(String title){
        return jdbctemplate.query(FIND_THREADS_BYNAME,mapper, title);
    }

    public Thread getThread(String id){
        return jdbctemplate.queryForObject(FIND_THREAD, mapper, id);
    }

    /* Returns Author's username */
    public String getAuthor(String id) {
        return this.jdbctemplate.queryForObject(GET_AUTHOR, String.class, id);
    }

    public boolean isPrivate(String id) {
        Character c = this.jdbctemplate.queryForObject(GET_PRIVATE, Character.class, id);
        if (c == '1')
            return true;
        else
            return false;
    }

    // Returns all the threads an User has
    public List<Thread> getUserThreads(String username) {
        return this.jdbctemplate.query(FIND_USER_THREADS, mapper, username);
    }

    // Returns the count of an User's threads
    public int countUserThreads(String username) {
        return this.jdbctemplate.queryForObject(NUM_THREADS, Integer.class, username);
    }





}
