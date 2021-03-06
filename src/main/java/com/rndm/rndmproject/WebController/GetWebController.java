package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.CategoryUseCases;
import com.rndm.rndmproject.Controller.RESTController;
import com.rndm.rndmproject.Controller.ThreadUseCases;
import com.rndm.rndmproject.Controller.UserUseCases;
import com.rndm.rndmproject.domain.Comment;
import com.rndm.rndmproject.domain.Thread;
import com.rndm.rndmproject.persistence.CommentDAO;
import com.rndm.rndmproject.persistence.VotesDAO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
    public String firstThreads (Model model, Principal principal, Authentication auth){
        boolean premiumSearch = false;
        if (auth != null)
            premiumSearch = true;
        model.addAttribute("IndexThread", threadUseCases.findFirstTen(premiumSearch)); // For hiding private threads
        model.addAttribute("Pages", getNumberPages(principal));
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


    @GetMapping("Page/{page}")
    public String firstThreads (Model model, @PathVariable int page, Principal principal){
        boolean premium = false;
        if (principal != null)
            premium = true;

        model.addAttribute("IndexThread", threadUseCases.findXThreads(page, premium));
        model.addAttribute("Pages", getNumberPages(principal));
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
        boolean premiumSearch = false;
        if (principal != null)
            premiumSearch = true;
        model.addAttribute("IndexThread", threadUseCases.findThreadByCategory(category, premiumSearch)); // Hide private threads
        model.addAttribute("Pages", getNumberPages(principal));
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
        Thread thread = threadUseCases.getThread(id);
        if (principal == null && thread.isPremium())
            thread.setText("<p>You need to be registered to view the content of this thread!</p>");

        model.addAttribute("threadByID", thread);
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
        model.addAttribute("Pages", getNumberPages(principal));
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


    private List<Integer>  getNumberPages(Principal princapal) {
        int numberPages;
        int totalThreads;
        List<Integer> arrayPages = new ArrayList<Integer>();
        if(princapal == null)
            totalThreads = this.threadUseCases.getTotalThreads(0);
        else {
            totalThreads = this.threadUseCases.getTotalThreads(1) + this.threadUseCases.getTotalThreads(0);
        }
        
        numberPages = totalThreads / 10;
        if(totalThreads % 10 > 0) numberPages += 1;
        if(numberPages == 0) numberPages = 1;
        int i = 0;
        while (numberPages > i){
            arrayPages.add(i+1);
            i += 1;
        }
        return arrayPages;
    }

}
