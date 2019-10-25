package com.rndm.rndmproject.persistence;

import com.rndm.rndmproject.domain.Thread;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ThreadDAO {

    private JdbcTemplate jdbctemplate;

    private final String INSERT_THREAT = "insert into Threads (id_thread, title, content, is_private) values (?,?,?,?)";
    private final String NUM_THREADS = "select count(*) from thread where username = ?";
    private final String FIRST_THREADS = "select * from thread limit 10"; //linia per h2


    public ThreadDAO (JdbcTemplate jdbcTemplate){
        this.jdbctemplate = jdbcTemplate;
    }

    public List<Thread> findFirstTen(){
        return jdbctemplate.query(FIRST_THREADS, new BeanPropertyRowMapper<>(Thread.class));
    }

}
