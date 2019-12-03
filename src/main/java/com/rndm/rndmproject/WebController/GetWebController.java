package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.RESTController;
import com.rndm.rndmproject.domain.Comment;
import com.rndm.rndmproject.domain.Thread;
import com.rndm.rndmproject.persistence.*;
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

    private ThreadDAO threadDAO;
    private CategoryDAO categoryDAO;
    private CommentDAO commentDAO;
    private RESTController restController;
    private UserDAO userDAO;

    public GetWebController(ThreadDAO threadDAO, CategoryDAO categoryDAO, CommentDAO commentDAO, RESTController rest, UserDAO userDAO){
        this.threadDAO = threadDAO;
        this.categoryDAO = categoryDAO;
        this.commentDAO = commentDAO;
        this.restController = rest;
        this.userDAO = userDAO;
    }

    @GetMapping("/")
    public String firstThreads (Model model, Principal principal, Authentication auth){
        boolean premiumSearch = false;
        if (auth != null)
            premiumSearch = true;
        model.addAttribute("IndexThread", threadDAO.findFirstTen(premiumSearch));
        model.addAttribute("Pages", getNumberPages());
        model.addAttribute("Categories", categoryDAO.findCategories());
        model.addAttribute("TopThreads", threadDAO.getTopThreads());
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("Category", threadDAO);
        model.addAttribute("TopCategory", threadDAO.getTop());
        model.addAttribute("Logo", categoryDAO);
        model.addAttribute("Users", userDAO);
        model.addAttribute("Principal", principal);

        return "index";
    }

    @GetMapping("Page/{page}")
    public String firstThreads (Model model, @PathVariable int page, Principal principal){
        model.addAttribute("IndexThread", threadDAO.findXThreads(page));
        model.addAttribute("Pages", getNumberPages());
        model.addAttribute("Categories", categoryDAO.findCategories());
        model.addAttribute("TopThreads", threadDAO.getTopThreads());
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("Category", threadDAO);
        model.addAttribute("TopCategory", threadDAO.getTop());
        model.addAttribute("Logo", categoryDAO);
        model.addAttribute("Users", userDAO);
        model.addAttribute("Principal", principal);
        return "index";
    }

    @GetMapping("/Category/{category}")
    public String FindByCategory (Model model, @PathVariable String category, Principal principal){
        boolean premiumSearch = false;
        if (principal != null)
            premiumSearch = true;
        model.addAttribute("IndexThread", threadDAO.findThreadByCategory(category, premiumSearch));
        model.addAttribute("Pages", getNumberPages());
        model.addAttribute("Categories", categoryDAO.findCategories());
        model.addAttribute("TopThreads", threadDAO.getTopThreads());
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("Category", threadDAO);
        model.addAttribute("Logo", categoryDAO);
        model.addAttribute("Users", userDAO);
        model.addAttribute("Principal", principal);
        model.addAttribute("TopCategory", threadDAO.getTop());
        return"index";
    }

    @GetMapping("/Thread/{id}")
    public String LoadThread (Model model, @PathVariable String id, HttpServletRequest request, Principal principal){
        Thread thread = threadDAO.getThread(id);
        if (principal == null)
            thread.setText("<p>You need to be registered to view the content of this thread!</p>");

        model.addAttribute("threadByID", thread);
        model.addAttribute("Categories", categoryDAO.findCategories());
        model.addAttribute("Comments", commentDAO.getCommentsByThread(id));
        model.addAttribute("TopThreads", threadDAO.getTopThreads());
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Category", threadDAO);
        model.addAttribute("Users", userDAO);
        model.addAttribute("Principal", principal);
        model.addAttribute("commentThread", null);
        model.addAttribute("Logo", categoryDAO);
        model.addAttribute("TopCategory", threadDAO.getTop());

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
        model.addAttribute("IndexThread", threadDAO.findThreadByName(title));
        model.addAttribute("Categories", categoryDAO.findCategories());
        model.addAttribute("Pages", getNumberPages());
        model.addAttribute("TopThreads", threadDAO.getTopThreads());
        model.addAttribute("Weather", restController.getWeather());
        model.addAttribute("Comment", commentDAO);
        model.addAttribute("Category", threadDAO);
        model.addAttribute("Users", userDAO);
        model.addAttribute("Principal", principal);
        model.addAttribute("Logo", categoryDAO);
        model.addAttribute("TopCategory", threadDAO.getTop());
        return"index";
    }

    private List<Integer>  getNumberPages() {
        int numberPages;
        List<Integer> arrayPages = new ArrayList<Integer>();
        int totalThreads = this.threadDAO.getTotalThreads();
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
