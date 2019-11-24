package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.domain.Comment;
import com.rndm.rndmproject.persistence.CommentDAO;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller

public class NewCommentController {

    private CommentDAO commentDAO;

    public NewCommentController(CommentDAO commentDAO ){
       this.commentDAO = commentDAO;
    }

    @PostMapping("postComment")
    public String NewComment (@ModelAttribute Comment newComment){
        System.out.println(newComment);
        //newComment.generateID();
        this.commentDAO.insert(newComment);
        return "redirect:/Thread/"+ newComment.getThread();
    }


}
