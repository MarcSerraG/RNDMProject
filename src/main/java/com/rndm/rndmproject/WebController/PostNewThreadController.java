package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.ThreadUseCases;
import com.rndm.rndmproject.domain.Thread;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("NewThread")

public class PostNewThreadController {
    private final ThreadUseCases threadUseCases;

    public PostNewThreadController(ThreadUseCases threadUseCases){
        this.threadUseCases = threadUseCases;
    }

    @GetMapping
    public String NewThread(Model model){
        return "new_thread";
    }

    @PostMapping
    public String NewThread(Thread thread, Errors errors, Model model){
        if(errors.hasErrors()){
            return "new_thread";
        }
        try{
            model.addAttribute("title", thread.getTitle());
            threadUseCases.insert(thread);
            return("/");
        }catch(Exception e){
            return null;
        }
    }

}
