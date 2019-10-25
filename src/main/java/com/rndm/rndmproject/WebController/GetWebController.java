package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.ThreadUseCases;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class GetWebController {

    private final ThreadUseCases threadUseCases;

    public GetWebController(ThreadUseCases threadUseCases){
        this.threadUseCases = threadUseCases;
    }

    @GetMapping("home")
    public String firstThreads (Model model){
        model.addAttribute("FirstThreads", threadUseCases.findFirstTen());
        return "index";
    }

}
