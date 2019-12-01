
package com.rndm.rndmproject.persistence;

import com.rndm.rndmproject.domain.Category;
import com.rndm.rndmproject.domain.Thread;
import com.rndm.rndmproject.domain.Votes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ThreadDAO {

    private JdbcTemplate jdbctemplate;
    private VotesDAO votesDAO;

    private final String INSERT_THREAD = "insert into thread " +
            "(id_thread, title, content, image_url, is_private, users_username, category_name, date_creation) " +
            "values (?,?,?,?,?,?,?,?)";
    private final String GET_TOP = "select category_name from thread group by category_name asc";
    private final String GET_COUNT = "select count(*) from thread where category_name = ?";
    private final String NUM_THREADS = "select count(*) from thread where users_username = ?";
    private final String GET_AUTHOR = "select users_username from thread where id_thread = ?";
    private final String FIND_THREAD = "select * from thread where id_thread = ?";
    private final String GET_PRIVATE = "select is_private from thread where id_thread = ?";
    private final String FIND_USER_THREADS = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread where users_username = ?";
    private final String FIRST_THREADS = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread where is_private = '0' order by date_creation DESC limit ?" ; //linia per h2
    private final String FINDX_THREADS = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread where is_private = '0' order by date_creation DESC limit ?,10" ; //linia per h2
    private final String FIND_THREAD_CATEGORY = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread where is_private = '0' and category_name = ? limit 10" ;
    private final String FIND_THREADS_BYNAME = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread where title like \"%?%\" ";
    private final String FIND_TOPTHREADS = "SELECT threads_id_thread FROM vote WHERE positive = 1 GROUP BY threads_id_thread ORDER BY count(positive) DESC";
    private final String COUNT_THREADS = "select count(*) from thread ";

    private Thread threadMapper(ResultSet resultSet) throws SQLException {

        Thread thread = new Thread(
                resultSet.getString("id_thread"),
                resultSet.getString("title"),
                resultSet.getString("content"),
                resultSet.getString("image_url"),
                resultSet.getString("users_username"),
               null,
                new Category(resultSet.getString("category_name")),
                resultSet.getString("date_creation"),
                0,
                0,
                votesDAO.getThreadVotes(resultSet.getString("id_thread")));
        return thread;
    };

    private final RowMapper<Thread> mapper = (resultSet, i) -> {
        return threadMapper(resultSet);
    };

    public ThreadDAO(JdbcTemplate jdbctemplate, VotesDAO votesDAO){
        this.jdbctemplate = jdbctemplate;
        this.votesDAO = votesDAO;
    }

    public int insert(Thread thread){
        return jdbctemplate.update(INSERT_THREAD, thread.getID(), thread.getTitle(), thread.getText(), thread.getMedia(), 0, thread.getUsername(), thread.getCategory().getName(), (String)thread.getDate());
    }
    public List<String> getTop () {return jdbctemplate.queryForList(GET_TOP, String.class);}

    public int getCount(String name) {return jdbctemplate.queryForObject(GET_COUNT, Integer.class, name);}

    public List<Thread> findFirstTen(){
        return jdbctemplate.query(FIRST_THREADS, mapper, 10);
    }

    public List<Thread> findXThreads(int page){
        return jdbctemplate.query(FINDX_THREADS, mapper, (page * 10)-10);
    }

    public List<Thread> findThreadByCategory(String Category){
        return jdbctemplate.query(FIND_THREAD_CATEGORY, mapper, Category);
    }

    public List<Thread> findThreadByUser(String User){
        return jdbctemplate.query(FIND_USER_THREADS,mapper,User);
    }

    public List<Thread> findThreadByName(String title){
        return jdbctemplate.query(FIND_THREADS_BYNAME.replace("?", title),mapper);
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

    public List<Thread> getTopThreads() {
        List<String> threadsIDs = this.jdbctemplate.queryForList(FIND_TOPTHREADS, String.class);

        List<Thread> listThreads = new ArrayList<Thread>();

        for (String id : threadsIDs) {
            listThreads.add(this.getThread(id));
        }

        return listThreads;
    }

    public void addVote(Thread thread, Votes vote) {

        Boolean bool = thread.getVote(vote.getUser());
        if (bool == null) {
            this.votesDAO.insertVote(vote);
            thread.addVote(vote);
        }
        else if ((bool && vote.getPositive()) || (!bool && !vote.getPositive())) {
            this.votesDAO.deleteVote(vote);
            thread.removeVote(vote);
        }
        else {
            this.votesDAO.updateVote(vote);
            thread.addVote(vote);
        }

    }

    public int getTotalThreads(){
        return this.jdbctemplate.queryForObject(COUNT_THREADS, Integer.class);
    }



}
