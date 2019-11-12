package com.rndm.rndmproject.WebController;


import com.rndm.rndmproject.Controller.UserUseCases;
import com.rndm.rndmproject.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private UserUseCases userUseCases;

    public RegisterController(UserUseCases userUseCases) {
        this.userUseCases = userUseCases;
    }

    @GetMapping("register")
    public String registerUser(Model model) {
        model.addAttribute("usernew", new User());
        return "Register";
    }

    @PostMapping("register")
    public String registerUser(@Valid User usernew, Model model) {
        /*if (errors.hasErrors()) {
            System.err.println(errors.getAllErrors());
            return "redirect:/";
        }*/
        /*model.addAttribute("username", usernew.getUsername());
        model.addAttribute("password", usernew.getPassword());
        model.addAttribute("email", usernew.getEmail());*/

        this.userUseCases.insertUser(usernew);

        System.out.println(usernew.getUsername() + "   " + usernew.getEmail() + "    " + usernew.getPassword());

        return "redirect:/";
    }

}