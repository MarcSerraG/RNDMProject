package com.rndm.rndmproject.WebController;


import com.rndm.rndmproject.Controller.RESTController;
import com.rndm.rndmproject.domain.User;
import com.rndm.rndmproject.persistence.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class UserController {

    private UserDAO userDAO;
    private ThreadDAO threadDAO;
    private CommentDAO commentDAO;
    private CategoryDAO categoryDAO;
    private RESTController restController;

    public UserController(ThreadDAO threadDAO, CommentDAO commentDAO, UserDAO userDAO,
                          CategoryDAO categoryDAO, RESTController rest){
        this.threadDAO = threadDAO;
        this.categoryDAO = categoryDAO;
        this.userDAO = userDAO;
        this.restController = rest;
        this.commentDAO = commentDAO;
        this.threadDAO = threadDAO;
    }

    @GetMapping("profile")
    private String getProfile(Model model, Principal principal){

        model.addAttribute("Categories", categoryDAO.findCategories());
        model.addAttribute("IndexThread", threadDAO.findThreadByUserVote(principal.getName()));
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Category", threadDAO);
        model.addAttribute("Users", userDAO);
        User u = userDAO.getProfile(principal.getName());
        model.addAttribute("User", u);
        model.addAttribute("Principal", principal);
        model.addAttribute("CountThread",threadDAO.getCountByuser(principal.getName()));
        model.addAttribute("CountComment",commentDAO.getCountCommentsByUser(principal.getName()));
        return "/profile";
    }

    @GetMapping("Thread/{id}/delete")
    public String deleteThread(@PathVariable String id, Authentication auth) {
        if (auth.getName() == null)
            return("/login");
        try {
            this.threadDAO.deleteThread(id);
        }catch (Exception e){
            System.out.println("Error: "+e);
            return "redirect:/error";
        }
        return("redirect:/profile");
    }


}
