package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.domain.Comment;
import com.rndm.rndmproject.persistence.CommentDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.CommonDataSource;

@Controller
@RequestMapping("NewComment")
public class NewCommentController {

    private CommentDAO commentDAO;

    public NewCommentController(CommentDAO commentDAO ){
       this.commentDAO = commentDAO;
    }

    @PostMapping
    public String NewComment (Comment newComment){
        this.commentDAO.insert(newComment);
        return "redirect:../../Thread/"+ newComment.getThread();
    }
}
