package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.ThreadUseCases;
import com.rndm.rndmproject.domain.Thread;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class NewThreadController {

    private ThreadUseCases threadUseCases;
    public NewThreadController(ThreadUseCases useCases){this.threadUseCases = useCases;}

    @GetMapping("NewThread")
    public String NewThread(Model model){
        return "new_thread";
    }

    @PostMapping("NewThread")
    public String NewThread(@Valid Thread thread, Errors errors){
        if(errors.hasErrors()){
            return "new_thread";
        }
        return "/";
    }
}
