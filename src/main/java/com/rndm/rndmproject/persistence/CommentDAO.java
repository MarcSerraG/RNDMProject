package com.rndm.rndmproject.persistence;

import com.rndm.rndmproject.domain.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CommentDAO {

    ThreadDAO threadDAO;
    JdbcTemplate jdbcTemplate;

    private final String INSERT_COMMENT = "insert into comment " +
            "(id_comment, content, comments_id_comment, users_username, threads_id_thread) " +
            "values (?,?,?,?,?)";

    public CommentDAO(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}

    private Comment commentMapper(ResultSet resultSet) throws SQLException {

        Comment comment = new Comment(
                resultSet.getString("users_username"),
                resultSet.getString("content"),
                null,
                threadDAO.getThread(resultSet.getString("threads_id_thread")));
        return comment;
    }

    private Comment getFatherComment(int commentID){return null;}
    public int insert(Comment comment){return jdbcTemplate.update(
            INSERT_COMMENT,
            comment.getID(),
            comment.getContent(),
            comment.getFatherComment(),
            comment.getUsername(),
            comment.getThread());}

}
