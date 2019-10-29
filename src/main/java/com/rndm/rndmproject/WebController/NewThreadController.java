package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.CategoryUseCases;
import com.rndm.rndmproject.Controller.ThreadUseCases;
import com.rndm.rndmproject.domain.Thread;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller

public class NewThreadController {

    private ThreadUseCases threadUseCases;
    private CategoryUseCases categoryUseCases;
    public NewThreadController(ThreadUseCases useCases, CategoryUseCases categoryUseCases){this.threadUseCases = useCases; this.categoryUseCases = categoryUseCases;}

    @GetMapping("/NewThread")
    public String NewThread(Model model){
        model.addAttribute("NewThread", new Thread());
        model.addAttribute("Categories", categoryUseCases.findCategories());
        return "new_thread";
    }


    @PostMapping("/NewThread")
    public String NewThread(Thread thread, Errors errors, Model model){
        if(errors.hasErrors()){
            return "new_thread";
        }
        try {
            model.addAttribute("title", thread.getTitle());
            this.threadUseCases.insert(thread);
            return "index";

        }catch (Exception e){
            System.err.println("Error al crear un thread: " + e);
            return "index";
        }
    }
}
