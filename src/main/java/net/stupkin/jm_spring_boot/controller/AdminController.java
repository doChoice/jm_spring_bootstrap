package net.stupkin.jm_spring_boot.controller;


import net.stupkin.jm_spring_boot.entity.Role;
import net.stupkin.jm_spring_boot.entity.User;
import net.stupkin.jm_spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;


    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showAllUsers(@ModelAttribute("newUser") User newUser, Principal principal, Model model) {
        List<User> allUsers = userService.getAllUsers();
        List<Role> allRoles = userService.getAllRoles();
        User authorizedUser = userService.getUserByUsername(principal.getName());
        model.addAttribute("allUsers",allUsers);
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("authorizedUser", authorizedUser);
        return "admin";
    }

    @Transactional
    @PostMapping()
    public String addNewUser(@ModelAttribute("user") @Valid User user,
                             @RequestParam (value ="roles", required = false) Long[] rolesId) {

        Set<Role> roles = new HashSet<>();
        for (Long id : rolesId) {
            roles.add(userService.getRoleById(id));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             @RequestParam (value ="roles", required = false) Long[] rolesId){

        Set<Role> roles = new HashSet<>();
        for (Long id : rolesId) {
            roles.add(userService.getRoleById(id));
        }
        user.setRoles(roles);
        if(rolesId != null) {
            for (Long id : rolesId) {
                roles.add(userService.getRoleById(id));
            }
            user.setRoles(roles);
        } else {
            user.setRoles(userService.getUserById(user.getId()).getRoles());
        }

        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
