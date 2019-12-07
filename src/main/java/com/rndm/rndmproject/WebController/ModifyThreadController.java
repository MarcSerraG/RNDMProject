package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.CategoryUseCases;
import com.rndm.rndmproject.Controller.RESTController;
import com.rndm.rndmproject.Controller.ThreadUseCases;
import com.rndm.rndmproject.Controller.UserUseCases;
import com.rndm.rndmproject.domain.Comment;
import com.rndm.rndmproject.domain.Thread;
import com.rndm.rndmproject.persistence.CommentDAO;
import com.rndm.rndmproject.persistence.VotesDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ModifyThreadController {

    private ThreadUseCases threadUseCases;
    private CategoryUseCases categoryUseCases;
    private VotesDAO votesDAO;
    private UserUseCases userUseCases;
    private CommentDAO  commentDAO;
    private RESTController restController;


    public ModifyThreadController(ThreadUseCases useCases, CategoryUseCases categoryUseCases, VotesDAO votesDAO, UserUseCases userUseCases, CommentDAO commentDAO, RESTController rest){
        this.threadUseCases = useCases;
        this.categoryUseCases = categoryUseCases;
        this.votesDAO = votesDAO;
        this.userUseCases = userUseCases;
        this.commentDAO = commentDAO;
        this.restController = rest;

    }

    @GetMapping("/ModifyThread/{id}")
    public String ModifyThread(Model model, @PathVariable String id, Principal principal){

        Thread OldThread = this.threadUseCases.getThread(id);

        if(!OldThread.getUsername().equals(principal.getName())) return "redirect: ../../../profile";

        model.addAttribute("NewThread", new Thread());
        model.addAttribute("OldThread", OldThread);
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("Users", userUseCases);
        model.addAttribute("Principal", principal);
        model.addAttribute("Category", threadUseCases);
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("TopCategory", threadUseCases.getTop());
        model.addAttribute("Logo", categoryUseCases);
        model.addAttribute("TopThreads", threadUseCases.getTopThreads());
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
            this.threadUseCases.update(modThread);
            return "redirect:/";

        }catch (Exception e){
            System.err.println("Error al crear un thread: " + e);
            return "/";
        }
    }
}