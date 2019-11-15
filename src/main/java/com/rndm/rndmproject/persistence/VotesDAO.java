package com.rndm.rndmproject.persistence;

import com.rndm.rndmproject.domain.Votes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class VotesDAO {

    private JdbcTemplate jdbctemplate;

    private final String FIND_TOPTHREADS = "SELECT `thread`.`id_thread`, `thread`.`title`, `vote`.`positive`, `vote`.`negative`, `thread`.`users_username`\n" +
            "FROM `thread`\n" +
            "    LEFT JOIN `vote` ON `vote`.`threads_id_thread` = `thread`.`id_thread`\n order by vote.positive DESC LIMIT 10";

    private final String FIND_VOTES_THREAD = "SELECT positive, threads_id_thread, users_username FROM vote WHERE threads_id_thread = ?";


    public VotesDAO(JdbcTemplate jdbctemplate){
        this.jdbctemplate = jdbctemplate;
    }

    private Votes votesMapper(ResultSet resultSet) throws SQLException {

        Votes votes = new Votes(
                resultSet.getString("id_thread"),
                resultSet.getBoolean("positive"),
                resultSet.getString("users_username"));
        return votes;
    };

    private final RowMapper<Votes> mapper = (resultSet, i) -> {
        return votesMapper(resultSet);
    };

    public List<Votes> getTopThread(){
        return this.jdbctemplate.query(FIND_TOPTHREADS,mapper);
    }

    public List<Votes> getThreadVotes(String threadID) {
        return this.jdbctemplate.query(FIND_VOTES_THREAD, mapper);
    }


}
