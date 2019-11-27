package com.rndm.rndmproject.WebController;

import com.rndm.rndmproject.Controller.UserUseCases;
import com.rndm.rndmproject.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class LoginController {

    private UserUseCases userUseCases;

    public LoginController(UserUseCases userUseCases) {
        this.userUseCases = userUseCases;
    }

    @GetMapping("register")
    public String registerUser(Model model) {
        model.addAttribute("usernew", new User());
        model.addAttribute("UserExist", "");
        return "register";
    }

    @PostMapping("register")
    public String registerUser(@ModelAttribute("usernew") @Valid User usernew, Errors errors, Model model) {
        if (!errors.hasErrors()) {
            try {
                this.userUseCases.insertUser(usernew);
            }catch (Exception e){
                if(String.valueOf(e).contains("email")){
                    errors.rejectValue("username", null, "This user already exist");
                }else{
                    errors.rejectValue("username", null, "This user already exist");
                }
                System.out.println(e);

                return "register";
            }

            return "redirect:/";
        }
        return "register";
    }

    @GetMapping("login")
    public String loginUser(Model model , HttpServletRequest request) {
        model.addAttribute("userlogin", new User());
        model.addAttribute("badCredentials", request.getParameter("badCredentials") );
        return "login";
    }

    @GetMapping("login_error")
    public String loginError(Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("userlogin", new User());
        redirectAttributes.addAttribute("badCredentials", "username or/and password incorrect");

        return "redirect:/login";
    }

    @GetMapping("success")
    public String succesLogin(Principal principal, HttpServletRequest session){

        this.userUseCases.ChangeConnected(principal.getName(), 1);

        session.getSession().setAttribute("username", principal.getName());

        return "redirect:/";
    }




}
