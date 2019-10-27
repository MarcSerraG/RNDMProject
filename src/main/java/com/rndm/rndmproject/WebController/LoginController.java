package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.UserUseCases;
import com.rndm.rndmproject.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class LoginController {

    private UserUseCases userUseCases;

    public LoginController(UserUseCases userUseCases) {
        this.userUseCases = userUseCases;
    }

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute(new User());
        return "login";
    }

    @PostMapping("login")
    public String login(@Valid User user, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            // Do nothing for now
            // return "login";
        }

        model.addAttribute("username", user.getUsername());
        this.userUseCases.insertUser(user);
        return "redirect:/";
    }


}
