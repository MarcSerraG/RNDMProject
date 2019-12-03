package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.domain.Votes;
import com.rndm.rndmproject.persistence.ThreadDAO;
import com.rndm.rndmproject.persistence.VotesDAO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VoteController {

    private ThreadDAO threadDAO;
    private VotesDAO votesDAO;

    public VoteController(ThreadDAO threadDAO, VotesDAO votesDAO) {
        this.threadDAO = threadDAO;
        this.votesDAO = votesDAO;
    }

    @GetMapping("/Thread/{id}/upvote")
    public String addUpvote(@PathVariable String id, Model model, Authentication auth) {
        if (auth.getName() == null)
            return("/login");

        Votes vote = new Votes(id, true, auth.getName());
        this.threadDAO.addVote(threadDAO.getThread(id), vote);
        return("redirect:/Thread/"+id);
    }

    @GetMapping("/Thread/{id}/downvote")
    public String addDownvote(@PathVariable String id, Model model, Authentication auth) {
        if (auth.getName() == null)
            return("/login");

        Votes vote = new Votes(id, false, auth.getName());
        this.threadDAO.addVote(threadDAO.getThread(id), vote);
        return("redirect:/Thread/"+id);
    }

}
