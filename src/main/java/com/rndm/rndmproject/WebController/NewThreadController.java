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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("NewThread")
public class NewThreadController {

    private ThreadUseCases threadUseCases;
    private CategoryUseCases categoryUseCases;
    private VotesDAO votesDAO;
    private UserUseCases userUseCases;
    private CommentDAO  commentDAO;
    private RESTController restController;


    public NewThreadController(ThreadUseCases useCases, CategoryUseCases categoryUseCases, VotesDAO votesDAO, UserUseCases userUseCases, CommentDAO commentDAO, RESTController rest){
        this.threadUseCases = useCases;
        this.categoryUseCases = categoryUseCases;
        this.votesDAO = votesDAO;
        this.userUseCases = userUseCases;
        this.commentDAO = commentDAO;
        this.restController = rest;

    }

    @GetMapping
    public String NewThread(Model model, Principal principal){
        model.addAttribute("NewThread",new Thread());
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("Users", userUseCases);
        model.addAttribute("Principal", principal);
        model.addAttribute("Category", threadUseCases);
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("TopCategory", threadUseCases.getTop());
        model.addAttribute("Logo", categoryUseCases);

        model.addAttribute("TopThreads", threadUseCases.getTopThreads());
        return "new_thread";
    }


    @PostMapping
    public String NewThread(Thread NewThread, Errors errors, Model model){
        if(errors.hasErrors()){
            return "new_thread";
        }


        try {
            if(NewThread.getTitle() == null){
                System.err.println("Object is null");
            }

            model.addAttribute("title", NewThread.getTitle());
            this.threadUseCases.insert(NewThread);
            return "redirect:/";

        }catch (Exception e){
            System.err.println("Error al crear un thread: " + e);
            return "/";
        }
    }
}
