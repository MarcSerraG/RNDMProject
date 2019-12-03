package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.RESTController;
import com.rndm.rndmproject.domain.Thread;
import com.rndm.rndmproject.persistence.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class ModifyThreadController {

    private ThreadDAO threadDAO;
    private CategoryDAO categoryDAO;
    private VotesDAO votesDAO;
    private UserDAO userDAO;
    private CommentDAO  commentDAO;
    private RESTController restController;


    public ModifyThreadController(ThreadDAO DAO, CategoryDAO categoryDAO, VotesDAO votesDAO, UserDAO userDAO, CommentDAO commentDAO, RESTController rest){
        this.threadDAO = DAO;
        this.categoryDAO = categoryDAO;
        this.votesDAO = votesDAO;
        this.userDAO = userDAO;
        this.commentDAO = commentDAO;
        this.restController = rest;

    }

    @GetMapping("/ModifyThread/{id}")
    public String ModifyThread(Model model, @PathVariable String id, Principal principal){

        model.addAttribute("NewThread", new Thread());
        model.addAttribute("OldThread", this.threadDAO.getThread(id));
        model.addAttribute("Categories", categoryDAO.findCategories());
        model.addAttribute("Users", userDAO);
        model.addAttribute("Principal", principal);
        model.addAttribute("Category", threadDAO);
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("TopCategory", threadDAO.getTop());
        model.addAttribute("Logo", categoryDAO);
        model.addAttribute("TopThreads", threadDAO.getTopThreads());
        return "modify_thread";
    }


    @PostMapping("/ModifyThread/{id}")
    public String ModifyThread(Thread ModifyThread, @PathVariable String id, Errors errors, Model model, Principal principal){
        if(errors.hasErrors()){
            return "";
        }
        try {
            Thread modThread = ModifyThread;
            modThread.setId(id);
            this.threadDAO.update(modThread);
            return "redirect:/";

        }catch (Exception e){
            System.err.println("Error al crear un thread: " + e);
            return "/";
        }
    }
}