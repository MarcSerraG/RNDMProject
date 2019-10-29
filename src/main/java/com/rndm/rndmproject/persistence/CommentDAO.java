package com.rndm.rndmproject.persistence;

import com.rndm.rndmproject.domain.Comment;
import com.rndm.rndmproject.domain.Thread;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CommentDAO {

    ThreadDAO threadDAO;
    JdbcTemplate jdbcTemplate;

    private final String INSERT_COMMENT = "insert into comment " +
            "(id_comment, content, comments_id_comment, users_username, threads_id_thread) " +
            "values (?,?,?,?,?)";
    private final String FIND_COMMENT = "select * from comments where id_comment = ?";
    private final String GET_FATHER = "select * from comments where comments_id_comment = ?";
    private final String GET_BY_USERNAME = "select * from comments where users_username = ?";

    public CommentDAO(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}

    private Comment commentMapper(ResultSet resultSet) throws SQLException {

        Comment comment = new Comment(
                resultSet.getString("users_username"),
                resultSet.getString("content"),
                getFatherComment(resultSet.getInt("comments_id_comment")),
                threadDAO.getThread(resultSet.getString("threads_id_thread")));
        return comment;
    }

    private final RowMapper<Comment> mapper = (resultSet, i) -> {
        return commentMapper(resultSet);
    };

    private Comment getComment(int id){return jdbcTemplate.queryForObject(FIND_COMMENT, mapper, id);}
    private Comment getFatherComment(int id){return jdbcTemplate.queryForObject(GET_FATHER, mapper, id);}
    private List<Comment> getCommentsByUsername(String username) {return jdbcTemplate.query(GET_BY_USERNAME, mapper, username);}
    public int insert(Comment comment){return jdbcTemplate.update(
            INSERT_COMMENT,
            comment.getID(),
            comment.getContent(),
            comment.getFatherComment(),
            comment.getUsername(),
            comment.getThread());
    }


}
