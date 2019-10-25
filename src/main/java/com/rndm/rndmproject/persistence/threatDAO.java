package com.rndm.rndmproject.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class threatDAO {

    private JdbcTemplate jdbctemplate;

    private final String INSERT_THREAT = "insert into Threads (id_thread, title, content, is_private) values (?,?,?,?)";
    private final String NUM_THREADS = "select count(*) from thread where username = ?";
}
