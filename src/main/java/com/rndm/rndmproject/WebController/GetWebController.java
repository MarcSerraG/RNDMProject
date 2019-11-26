package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.CategoryUseCases;
import com.rndm.rndmproject.Controller.RESTController;
import com.rndm.rndmproject.Controller.ThreadUseCases;
import com.rndm.rndmproject.Controller.UserUseCases;
import com.rndm.rndmproject.REST.WeatherREST;
import com.rndm.rndmproject.domain.Thread;
import com.rndm.rndmproject.persistence.CommentDAO;
import com.rndm.rndmproject.persistence.ThreadDAO;
import com.rndm.rndmproject.persistence.VotesDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class GetWebController {

    private ThreadUseCases threadUseCases;
    private CategoryUseCases categoryUseCases;
    private VotesDAO votesDAO;
    private CommentDAO commentDAO;
    private RESTController restController;
    private UserUseCases userUseCases;

    public GetWebController(ThreadUseCases threadUseCases, CategoryUseCases categoryUseCases, CommentDAO commentDAO, VotesDAO votesDAO, RESTController rest, UserUseCases userUseCases){
        this.threadUseCases = threadUseCases;
        this.categoryUseCases = categoryUseCases;
        this.commentDAO = commentDAO;
        this.votesDAO = votesDAO;
        this.restController = rest;
        this.userUseCases = userUseCases;
    }

    @GetMapping("/")
    public String firstThreads (Model model, Principal principal){
        model.addAttribute("IndexThread", threadUseCases.findFirstTen());
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("TopThreads", votesDAO.getTopThread());
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("Category", threadUseCases);
        model.addAttribute("Users", userUseCases);
        model.addAttribute("Principal", principal);
        return "index";
    }


    @GetMapping("/{page}")
    public String firstThreads (Model model, @PathVariable int page){
        model.addAttribute("IndexThread", threadUseCases.findXThreads(page));
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Users", userUseCases);
        return "index";
    }

    @GetMapping("/Category/{category}")
    public String FindByCategory (Model model, @PathVariable String category, Principal principal){
        model.addAttribute("IndexThread", threadUseCases.findThreadByCategory(category));
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("TopThreads", votesDAO.getTopThread());
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("Category", threadUseCases);
        model.addAttribute("Users", userUseCases);
        model.addAttribute("Principal", principal);
        return"index";
    }

    @GetMapping("/Thread/{id}")
    public String LoadThread (Model model, @PathVariable String id, Principal principal){
        model.addAttribute("threadByID", threadUseCases.getThread(id));
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("Comments", commentDAO.getCommentsByThread(id));
        model.addAttribute("TopThreads", votesDAO.getTopThread());
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Category", threadUseCases);
        model.addAttribute("Users", userUseCases);
        model.addAttribute("Principal", principal);
        return "thread";
    }

    @GetMapping("Search/{title}")
    public String FindThreadByName (Model model, @PathVariable String title, Principal principal){
        model.addAttribute("IndexThread", threadUseCases.findThreadByName(title));
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("TopThreads", votesDAO.getTopThread());
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("Category", threadUseCases);
        model.addAttribute("Users", userUseCases);
        model.addAttribute("Principal", principal);
        return"index";
    }

}
