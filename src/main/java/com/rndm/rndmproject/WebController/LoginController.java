package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.UserUseCases;
import com.rndm.rndmproject.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class LoginController {

    private UserUseCases userUseCases;

    public LoginController(UserUseCases userUseCases) {
        this.userUseCases = userUseCases;
    }

    @GetMapping("register")
    public String registerUser(Model model) {
        model.addAttribute("usernew", new User());
        return "register-copia";
    }

    @PostMapping("register")
    public String registerUser(@Valid User usernew, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            // Do nothing for now
            // return "login";
        }
        /*
        model.addAttribute("username", usernew.getUsername());
        model.addAttribute("password", usernew.getPassword());
        model.addAttribute("email", usernew.getEmail());*/

        this.userUseCases.insertUser(usernew);

        return "redirect:/";
    }


}
