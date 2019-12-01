package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.CategoryUseCases;
import com.rndm.rndmproject.Controller.RESTController;
import com.rndm.rndmproject.Controller.ThreadUseCases;
import com.rndm.rndmproject.Controller.UserUseCases;
import com.rndm.rndmproject.domain.Comment;
import com.rndm.rndmproject.domain.Thread;
import com.rndm.rndmproject.domain.User;
import com.rndm.rndmproject.domain.Votes;
import com.rndm.rndmproject.persistence.CommentDAO;
import com.rndm.rndmproject.persistence.ThreadDAO;
import com.rndm.rndmproject.persistence.VotesDAO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class UserController {

    private UserUseCases userUseCases;
    private ThreadUseCases threadUseCases;
    private CommentDAO commentDAO;
    private CategoryUseCases categoryUseCases;
    private RESTController restController;
    private ThreadDAO threadDAO;

    public UserController(ThreadUseCases threadUseCases, CommentDAO commentDAO, UserUseCases userUseCases,
                          CategoryUseCases categoryUseCases, RESTController rest, ThreadDAO threadDAO){
        this.threadUseCases = threadUseCases;
        this.categoryUseCases = categoryUseCases;
        this.userUseCases = userUseCases;
        this.restController = rest;
        this.commentDAO = commentDAO;
        this.threadDAO = threadDAO;
    }

    @GetMapping("profile")
    private String getProfile(Model model, Principal principal){

        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("IndexThread", threadUseCases.findThreadByUserVote(principal.getName()));
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Category", threadUseCases);
        model.addAttribute("Users", userUseCases);
        User u = userUseCases.getProfile(principal.getName());
        model.addAttribute("User", u);
        model.addAttribute("Principal", principal);
        model.addAttribute("CountThread",threadUseCases.getCountUser(principal.getName()));
        model.addAttribute("CountComment",commentDAO.getCountCommentsByUser(principal.getName()));
        return "/profile";
    }

    @GetMapping("Thread/{id}/delete")
    public String deleteThread(@PathVariable String id, Authentication auth) {
        if (auth.getName() == null)
            return("/login");
        try {
            this.threadUseCases.delete(id);
        }catch (Exception e){
            System.out.println("Error: "+e);
            return "redirect:/error";
        }
        return("redirect:/profile");
    }
}
