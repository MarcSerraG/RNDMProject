package com.rndm.rndmproject.Controller;


import com.rndm.rndmproject.domain.Category;
import com.rndm.rndmproject.persistence.CategoryDAO;
import com.rndm.rndmproject.persistence.ThreadDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryUseCases {

    private CategoryDAO categoryDAO;

    public CategoryUseCases (CategoryDAO categoryDAO){
        this.categoryDAO = categoryDAO;
    }

    public List<Category> findCategories(){ return this.categoryDAO.findCategories();}


}
