package com.rndm.rndmproject.persistence;

import com.rndm.rndmproject.domain.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CommentDAO {

    JdbcTemplate jdbcTemplate;

    private final String INSERT_COMMENT = "insert into comments (id_comment, content, comments_id_comment, users_username, threads_id_thread, date_comment) values (?,?,?,?,?,?)";
    private final String COUNT_COMMENTS = "select count(*) from comments where threads_id_thread = ?";
    private final String FIND_COMMENT = "select * from comments where id_comment = ?";
    private final String GET_FATHER = "select * from comments where comments_id_comment = ?";
    private final String GET_BY_USERNAME = "select * from comments where users_username = ?";
    private final String GET_BY_THREAD = "select * from comments where threads_id_thread = ?";
    private final String GET_FATHER_CONTENT = "select content from comments where id_comment = ?";
    private final String GET_FATHER_USER = "select users_username from comments where id_comment = ?";
    private final String GET_CONTENT = "select content from comments where id_comment = ?";
    private final String GET_COUNT_VOTES_USER = "select count(*) as num from comments c join thread t on c.threads_id_thread = t.id_thread where t.users_username = ?";

    public CommentDAO(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}

    private Comment commentMapper(ResultSet resultSet) throws SQLException {
        String FatherContent = " ";
        String FatherUser = " ";
        if(resultSet.getString("comments_id_comment")!= null) {
            FatherContent = getFatherContent(resultSet.getString("comments_id_comment"));
            FatherUser = getFatherUser(resultSet.getString("comments_id_comment"));
        }

        Comment comment = new Comment(
                resultSet.getString("id_comment"),
                resultSet.getString("users_username"),
                resultSet.getString("content"),
                resultSet.getString("comments_id_comment"),
                resultSet.getString("threads_id_thread"),
                resultSet.getString("date_comment"),
                FatherContent,
                FatherUser

        );
        return comment;
    }
    private final RowMapper<Comment> mapper = (resultSet, i) -> commentMapper(resultSet);

    private Comment getFatherComment(String id) {return jdbcTemplate.queryForObject(GET_FATHER, mapper, id);}
    private List<Comment> getCommentsByUsername(String username) {return jdbcTemplate.query(GET_BY_USERNAME, mapper, username);}
    public int getCount(String id) {return jdbcTemplate.queryForObject(COUNT_COMMENTS,Integer.class, id);}
    public Comment getComment(String id){return jdbcTemplate.queryForObject(FIND_COMMENT, mapper, id);}
    public List<Comment> getCommentsByThread (String id){return jdbcTemplate.query(GET_BY_THREAD, mapper, id);}
    public int getCountCommentsByUser (String user){return this.jdbcTemplate.queryForObject(GET_COUNT_VOTES_USER,Integer.class,user);}

    public int insert(Comment comment){return jdbcTemplate.update(
            INSERT_COMMENT,
            comment.getID(),
            comment.getContent(),
            comment.getFatherComment(),
            comment.getCommentuser(),
            comment.getThread(),
            comment.getDate()
            );
    }

    public String getFatherContent(String idFather){
        return jdbcTemplate.queryForObject(GET_FATHER_CONTENT, String.class, idFather);
    }

    public String getFatherUser(String idFather){
        return jdbcTemplate.queryForObject(GET_FATHER_USER, String.class, idFather);
    }

    public String getContent(String id) {
        return jdbcTemplate.queryForObject(GET_CONTENT, String.class, id);
    }
}
