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

    private final String FIND_VOTES_THREAD = "SELECT positive, threads_id_thread, users_username FROM vote WHERE threads_id_thread = ?";
    private final String INSERT_UPVOTE = "INSERT INTO vote (positive, users_username, threads_id_thread) VALUES ('1', ?, ?)";
    private final String INSERT_DOWNVOTE = "INSERT INTO vote (positive, users_username, threads_id_thread) VALUES ('0', ?, ?)";
    public final String DELETE_VOTE = "DELETE FROM vote WHERE users_username = ? AND threads_id_thread = ?";
    public final String UPDATE_VOTE = "UPDATE vote SET positive = ? WHERE users_username = ? AND threads_id_thread = ?";
    public final String FIND_VOTE = "SELECT * FROM vote WHERE users_username = ? AND threads_id_thread = ?";
    public final String COUNT_VOTE = "select count(*) as votes from vote v join thread t on v.threads_id_thread " +
            "= t.id_thread where t.users_username = ?";


    public VotesDAO(JdbcTemplate jdbctemplate){
        this.jdbctemplate = jdbctemplate;
    }

    private Votes votesMapper(ResultSet resultSet) throws SQLException {
        Boolean positive = resultSet.getBoolean("positive");
        if (resultSet.wasNull())
            positive = null;

        Votes votes = new Votes(
                resultSet.getString("threads_id_thread"),
                positive,
                resultSet.getString("users_username"));
        return votes;
    };

    private final RowMapper<Votes> mapper = (resultSet, i) -> {
        return votesMapper(resultSet);
    };

    public int getCountVotesUser(String user){return this.jdbctemplate.queryForObject( COUNT_VOTE, Integer.class, user);}

    public List<Votes> getThreadVotes(String threadID) {
        return this.jdbctemplate.query(FIND_VOTES_THREAD, mapper,threadID);
    }

    public int insertUpvote(String username, String threadID) {
        return jdbctemplate.update(INSERT_UPVOTE,username, threadID);
    }

    public int insertDownvote(String username, String threadID) {
        return jdbctemplate.update(INSERT_DOWNVOTE, username, threadID);
    }

    public int insertVote(Votes vote) {
        if (vote.getPositive())
            return insertUpvote(vote.getUser(), vote.getThreadID());
        else
            return insertDownvote(vote.getUser(), vote.getThreadID());
    }

    public int deleteVote(Votes vote) {
        return this.jdbctemplate.update(DELETE_VOTE, vote.getUser(), vote.getThreadID());
    }

    public int updateVote(Votes vote) {
        int positive = 0;

        if (vote.getPositive())
            positive = 1;

        return this.jdbctemplate.update(UPDATE_VOTE, positive, vote.getUser(), vote.getThreadID());
    }

    public Votes findVote(String username, String threadID) {
        return this.jdbctemplate.queryForObject(FIND_VOTE, mapper, username, threadID);
    }


}
