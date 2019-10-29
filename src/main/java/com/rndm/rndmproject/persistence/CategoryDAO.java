package com.rndm.rndmproject.persistence;

import com.rndm.rndmproject.domain.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryDAO {

    private JdbcTemplate jdbctemplate;

    private final String FIND_CATEGORIES = "select * from category";

    public CategoryDAO(JdbcTemplate jdbctemplate){
        this.jdbctemplate = jdbctemplate;
    }

    private Category CategoryMapper(ResultSet resultSet) throws SQLException {
        Category category = new Category(resultSet.getString("name"));
        return category;
    };

    private final RowMapper<Category> mapper = (resultSet, i) -> {
        return CategoryMapper(resultSet);
    };

    public List<Category> findCategories(){
        return this.jdbctemplate.query(FIND_CATEGORIES, mapper);
    }

}
