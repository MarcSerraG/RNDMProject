package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.ThreadUseCases;
import com.rndm.rndmproject.domain.Thread;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class GetWebController {

    private ThreadUseCases threadUseCases;

    public GetWebController(ThreadUseCases threadUseCases){
        this.threadUseCases = threadUseCases;
    }

    @GetMapping("/")
    public String firstThreads (Model model){
        model.addAttribute("FirstThreads", threadUseCases.findFirstTen());
        return "index";
    }

    @GetMapping("/New_Thread")
    public String NewThreadPage (){
        return "new_thread";
    }
    @GetMapping("/{page}")
    public String firstThreads (Model model, @PathVariable int page){
        model.addAttribute("FirstThreads", threadUseCases.findXThreads(page));
        return "index";
    }


}
