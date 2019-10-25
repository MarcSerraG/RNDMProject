package com.rndm.rndmproject.persistence;

import com.rndm.rndmproject.domain.Achievement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;

@Repository
public class AchievementDAO {

    private JdbcTemplate jdbctemplate;

    private final String FIND_ALL = "select ach_name, date_award from achievement";
    private final String FIND_BY_USER = FIND_ALL + " where users_username = ?";
    private final String INSERT_ACHIEVEMENT = "insert into achievement (ach_name, date_award, users_username) values (?,?,?)";

    private Achievement achievementMapper(ResultSet resultSet) throws SQLException {

        Achievement achievement = new Achievement(resultSet.getString("name"));

        return achievement;
    };

    private final RowMapper<Achievement> mapper = (resultSet, i) -> {
        return achievementMapper(resultSet);
    };

    public AchievementDAO(JdbcTemplate jdbctemplate) {
        this.jdbctemplate = jdbctemplate;
    }

    public List<Achievement> getUserAchievements(String username){
        return jdbctemplate.query(FIND_BY_USER, mapper, username);
    }

    // TO DO
    public int setAchievement(Achievement achievement, String username) {
        Date date = new Date();
        return  0; // jdbctemplate.update(achievement.getName(), date.getDate());
    }


}
