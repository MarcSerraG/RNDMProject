package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.CategoryUseCases;
import com.rndm.rndmproject.Controller.ThreadUseCases;
import com.rndm.rndmproject.domain.Comment;
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

    public GetWebController(ThreadUseCases threadUseCases, CategoryUseCases categoryUseCases, CommentDAO commentDAO, VotesDAO votesDAO){
        this.threadUseCases = threadUseCases;
        this.categoryUseCases = categoryUseCases;
        this.commentDAO = commentDAO;
        this.votesDAO = votesDAO;
    }

    @GetMapping("/")
    public String firstThreads (Model model){
        model.addAttribute("IndexThread", threadUseCases.findFirstTen());
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("TopThreads", votesDAO.getTopThread());
        return "index";
    }


    @GetMapping("/{page}")
    public String firstThreads (Model model, @PathVariable int page){
        model.addAttribute("IndexThread", threadUseCases.findXThreads(page));
        return "index";
    }

    @GetMapping("/Category/{category}")
    public String FindByCategory (Model model, @PathVariable String category){
        model.addAttribute("IndexThread", threadUseCases.findThreadByCategory(category));
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("TopThreads", votesDAO.getTopThread());
        return"index";
    }

    @GetMapping("/Thread/{id}")
    public String LoadThread (Model model, @PathVariable String id, Principal principal, RedirectAttributes redirectAttributes){
        model.addAttribute("threadByID", threadUseCases.getThread(id));
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("Comments", commentDAO.getCommentsByThread(id));
        model.addAttribute("TopThreads", votesDAO.getTopThread());
        return "thread";
    }

    @GetMapping("Thread/{id}/Comment")
    public String CommentThread (Model model, @PathVariable String id, Principal principal, HttpServletRequest request){
        model.addAttribute("newComment", new Comment(id, principal.getName()));
        return "thread";
    }

    @GetMapping("Search/{title}")
    public String FindThreadByName (Model model, @PathVariable String title){
        model.addAttribute("IndexThread", threadUseCases.findThreadByName(title));
        model.addAttribute("Categories", categoryUseCases.findCategories());
        model.addAttribute("TopThreads", votesDAO.getTopThread());
        return"index";
    }


}
