package net.stupkin.jm_spring_boot.controller;


import net.stupkin.jm_spring_boot.entity.User;
import net.stupkin.jm_spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showUserById(Principal principal, Model model) {
        User authorizedUser = userService.getUserByEmail(principal.getName());
        model.addAttribute("authorizedUser", authorizedUser);
        return "user";
    }
}
