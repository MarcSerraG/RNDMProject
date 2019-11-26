package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.domain.Votes;
import com.rndm.rndmproject.domain.Thread;
import com.rndm.rndmproject.persistence.ThreadDAO;
import com.rndm.rndmproject.persistence.VotesDAO;
import com.rndm.rndmproject.Controller.ThreadUseCases;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VoteController {

    private ThreadDAO threadDAO;
    private VotesDAO votesDAO;

    public VoteController(ThreadDAO threadDAO, VotesDAO votesDAO) {
        this.threadDAO = threadDAO;
        this.votesDAO = votesDAO;
    }

    @PostMapping("/Thread/{id}/upvote")
    public String addUpvote(@ModelAttribute("threadByID") Thread thread, Model model, Authentication auth) {
        if (auth.getName() == null)
            return("/login");

        //Votes vote = this.votesDAO.findVote(auth.getName(), thread.getID());
        Votes vote = new Votes(thread.getID(), true, auth.getName());
        this.threadDAO.addVote(thread, vote);
        return("/Thread/"+thread.getID());
    }
}
