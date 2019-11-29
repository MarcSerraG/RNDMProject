package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.CategoryUseCases;
import com.rndm.rndmproject.Controller.RESTController;
import com.rndm.rndmproject.Controller.ThreadUseCases;
import com.rndm.rndmproject.Controller.UserUseCases;
import com.rndm.rndmproject.REST.Sys;
import com.rndm.rndmproject.REST.WeatherREST;
import com.rndm.rndmproject.domain.Comment;
import com.rndm.rndmproject.domain.Pages;
import com.rndm.rndmproject.domain.Thread;
import com.rndm.rndmproject.domain.Votes;
import com.rndm.rndmproject.persistence.CommentDAO;
import com.rndm.rndmproject.persistence.ThreadDAO;
import com.rndm.rndmproject.persistence.VotesDAO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
        model.addAttribute("Pages", getNumberPages());
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("TopThreads", threadUseCases.getTopThreads());
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("Category", threadUseCases);
        model.addAttribute("TopCategory", threadUseCases.getTop());
        model.addAttribute("Logo", categoryUseCases);
        model.addAttribute("Users", userUseCases);
        model.addAttribute("Principal", principal);

        return "index";
    }


    @GetMapping("/{page}")
    public String firstThreads (Model model, @PathVariable int page, Principal principal){
        model.addAttribute("IndexThread", threadUseCases.findXThreads(page));
        model.addAttribute("Pages", getNumberPages());
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("TopThreads", threadUseCases.getTopThreads());
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("Category", threadUseCases);
        model.addAttribute("TopCategory", threadUseCases.getTop());
        model.addAttribute("Logo", categoryUseCases);
        model.addAttribute("Users", userUseCases);
        model.addAttribute("Principal", principal);
        return "index";
    }

    @GetMapping("/Category/{category}")
    public String FindByCategory (Model model, @PathVariable String category, Principal principal){
        model.addAttribute("IndexThread", threadUseCases.findThreadByCategory(category));
        model.addAttribute("Pages", getNumberPages());
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("TopThreads", threadUseCases.getTopThreads());
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("Category", threadUseCases);
        model.addAttribute("Logo", categoryUseCases);
        model.addAttribute("Users", userUseCases);
        model.addAttribute("Principal", principal);
        model.addAttribute("TopCategory", threadUseCases.getTop());
        return"index";
    }

    @GetMapping("/Thread/{id}")
    public String LoadThread (Model model, @PathVariable String id, HttpServletRequest request, Principal principal){
        model.addAttribute("threadByID", threadUseCases.getThread(id));
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("Comments", commentDAO.getCommentsByThread(id));
        model.addAttribute("TopThreads", threadUseCases.getTopThreads());
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Category", threadUseCases);
        model.addAttribute("Users", userUseCases);
        model.addAttribute("Principal", principal);
        model.addAttribute("commentThread", null);
        model.addAttribute("Logo", categoryUseCases);
        model.addAttribute("TopCategory", threadUseCases.getTop());

        try{
            request.getParameter("commentThread");
            //Comment of a thread
            if(request.getParameter("commentThread").equals("thread")){
                model.addAttribute("user", principal.getName());
                model.addAttribute("commentThread", "thread");

            }else{
                //comment of a comment
                model.addAttribute("user", principal.getName());
                model.addAttribute("CommentID", request.getParameter("commentID"));
                model.addAttribute("commentThread", "new");
            }
            model.addAttribute("newComment", new Comment());
        }catch (Exception e){
            return "thread";
        }
        return "thread";
    }

    @GetMapping("Thread/{id}/Comment")
    public String CommentThread (@PathVariable String id, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("commentThread", "thread");
        return "redirect:../../Thread/"+id;
    }


    @GetMapping("Thread/{id}/Comment/{commentID}")
    public String CommentThreadComment ( @PathVariable String id, @PathVariable String commentID, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("commentID", commentID);
        redirectAttributes.addAttribute("commentThread", "new");
        return "redirect:../../../Thread/"+id;
    }


    @GetMapping("Search/{title}")
    public String FindThreadByName (Model model, @PathVariable String title, Principal principal){
        model.addAttribute("IndexThread", threadUseCases.findThreadByName(title));
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("Pages", getNumberPages());
        model.addAttribute("TopThreads", threadUseCases.getTopThreads());
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("Category", threadUseCases);
        model.addAttribute("Users", userUseCases);
        model.addAttribute("Principal", principal);
        model.addAttribute("Logo", categoryUseCases);
        model.addAttribute("TopCategory", threadUseCases.getTop());
        return"index";
    }


    private int[] getNumberPages() {
        int numberPages;
        int arrayPages[];
        int totalThreads = this.threadUseCases.getTotalThreads();
        System.out.println("Total threads: " + totalThreads);
        numberPages = totalThreads / 10;
        if(totalThreads % 10 > 0) numberPages += 1;
        if(numberPages == 0) numberPages = 1;
        System.out.println("Total pagines: " + numberPages);
        arrayPages = new int[numberPages];

        return arrayPages;
    }

}
