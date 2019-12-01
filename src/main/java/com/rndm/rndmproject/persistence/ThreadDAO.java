
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
    private final String GET_COUNT_USER = "select count(*) from thread where users_username = ?";
    private final String NUM_THREADS = "select count(*) from thread where users_username = ?";
    private final String GET_AUTHOR = "select users_username from thread where id_thread = ?";
    private final String FIND_THREAD = "select * from thread where id_thread = ?";
    private final String GET_PRIVATE = "select is_private from thread where id_thread = ?";
    private final String FIND_USER_THREADS = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread where users_username = ?";
    private final String FIND_USER_THREAD_VOTES = "select id_thread, title, content, image_url, t.users_username, category_name , date_creation, count(v.positive) as vot from thread t"+
            " left join vote v on v.threads_id_thread = t.id_thread where t.users_username = ? group by t.id_thread order by vot desc;";
    private final String FIRST_THREADS = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread where is_private = '0' order by date_creation DESC limit ?" ; //linia per h2
    private final String FIRST_THREADS_ALL = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread order by date_creation DESC limit ?" ; //linia per h2
    private final String FINDX_THREADS = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread where is_private = '0' limit 10 offset ?" ; //linia per h2
    private final String FINDX_THREADS_ALL = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread limit 10 offset ?" ; //linia per h2
    private final String FIND_THREAD_CATEGORY = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread where is_private = '0' AND category_name = ? limit 10";
    private final String FIND_THREAD_CATEGORY_ALL = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread where category_name = ? limit 10";
    private final String FIND_THREADS_BYNAME = "select id_thread, title, content, image_url, users_username, category_name , date_creation from thread where title like \"%?%\" ";
    private final String FIND_TOPTHREADS = "SELECT threads_id_thread FROM vote WHERE positive = 1 GROUP BY threads_id_thread ORDER BY count(positive) DESC";
    private final String DELETE_THREAD = "DELETE FROM thread WHERE id_thread = ?";

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

    public int deleteThread(String id){
        return jdbctemplate.update(DELETE_THREAD,id);
    }
    public List<String> getTop () {return jdbctemplate.queryForList(GET_TOP, String.class);}

    public int getCount(String name) {return jdbctemplate.queryForObject(GET_COUNT, Integer.class, name);}

    public int getCountByuser(String username) {return jdbctemplate.queryForObject(GET_COUNT_USER,Integer.class,username);}

    public List<Thread> findFirstTen(){
        return jdbctemplate.query(FIRST_THREADS, mapper, "", 10);
    }

    public List<Thread> findFirstTen(boolean premiumSearch) {
       if (premiumSearch)
           return jdbctemplate.query(FIRST_THREADS_ALL, mapper, 10);
       else
           return jdbctemplate.query(FIRST_THREADS, mapper, 10);
    }

    public List<Thread> findXThreads(int page){
        return jdbctemplate.query(FINDX_THREADS, mapper, page * 10);
    }
    public List<Thread> findXThreads(int page, boolean premiumSearch) {
        if (premiumSearch)
            return jdbctemplate.query(FINDX_THREADS_ALL, mapper, page * 10);
        else
            return jdbctemplate.query(FINDX_THREADS, mapper, page * 10);
    }

    public List<Thread> findThreadByCategory(String Category){
        return jdbctemplate.query(FIND_THREAD_CATEGORY, mapper, Category);
    }

    public List<Thread> findThreadByCategory(String Category, boolean premiumSearch){
        if (premiumSearch)
            return jdbctemplate.query(FIND_THREAD_CATEGORY_ALL, mapper, Category);
        else
            return jdbctemplate.query(FIND_THREAD_CATEGORY, mapper, Category);

    }

    public List<Thread> findThreadByUser(String User){
        return jdbctemplate.query(FIND_USER_THREADS,mapper,User);
    }

    public List<Thread> findThreadByUserVote(String User) { return jdbctemplate.query(FIND_USER_THREAD_VOTES,mapper,User);}

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



}
