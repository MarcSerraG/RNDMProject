package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.RESTController;
import com.rndm.rndmproject.domain.Thread;
import com.rndm.rndmproject.persistence.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("NewThread")
public class NewThreadController {

    private ThreadDAO threadDAO;
    private CategoryDAO categoryDAO;
    private VotesDAO votesDAO;
    private UserDAO userDAO;
    private CommentDAO  commentDAO;
    private RESTController restController;


    public NewThreadController(ThreadDAO DAO, CategoryDAO categoryDAO, VotesDAO votesDAO, UserDAO userDAO, CommentDAO commentDAO, RESTController rest){
        this.threadDAO = DAO;
        this.categoryDAO = categoryDAO;
        this.votesDAO = votesDAO;
        this.userDAO = userDAO;
        this.commentDAO = commentDAO;
        this.restController = rest;

    }

    @GetMapping
    public String NewThread(Model model, Principal principal){
        model.addAttribute("NewThread",new Thread());
        model.addAttribute("Categories", categoryDAO.findCategories());
        model.addAttribute("Users", userDAO);
        model.addAttribute("Principal", principal);
        model.addAttribute("Category", threadDAO);
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("TopCategory", threadDAO.getTop());
        model.addAttribute("Logo", categoryDAO);

        model.addAttribute("TopThreads", threadDAO.getTopThreads());
        return "new_thread";
    }


    @PostMapping
    public String NewThread(Thread NewThread, Errors errors, Model model, Principal principal){
        if(errors.hasErrors()){
            return "new_thread";
        }


        try {
            if(NewThread.getTitle() == null){
                System.err.println("Object is null");
            }

            model.addAttribute("title", NewThread.getTitle());
            NewThread.setUsername(principal.getName());
            this.threadDAO.insert(NewThread);
            return "redirect:/";

        }catch (Exception e){
            System.err.println("Error al crear un thread: " + e);
            return "/";
        }
    }
}
