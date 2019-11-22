package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.CategoryUseCases;
import com.rndm.rndmproject.Controller.RESTController;
import com.rndm.rndmproject.Controller.ThreadUseCases;
import com.rndm.rndmproject.Controller.UserUseCases;
import com.rndm.rndmproject.domain.Comment;
import com.rndm.rndmproject.domain.User;
import com.rndm.rndmproject.persistence.CommentDAO;
import com.rndm.rndmproject.persistence.VotesDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("profile")
public class UserController {

    private UserUseCases userUseCases;
    private ThreadUseCases threadUseCases;
    private VotesDAO votesDAO;
    private CommentDAO commentDAO;
    private CategoryUseCases categoryUseCases;
    private RESTController restController;

    public UserController(ThreadUseCases threadUseCases, CommentDAO commentDAO, CategoryUseCases categoryUseCases, VotesDAO votesDAO, RESTController rest){
        this.threadUseCases = threadUseCases;
        this.categoryUseCases = categoryUseCases;
        this.restController = rest;
        this.votesDAO = votesDAO;
        this.commentDAO = commentDAO;
    }

    @GetMapping
    private String getProfile(Model model, Principal principal){
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("IndexThread", threadUseCases.findThreadByUser(principal.getName()));
        //model.addAttribute("TopThreads", votesDAO.getTopThread());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Category", threadUseCases);
        return "/profile";
    }



}
