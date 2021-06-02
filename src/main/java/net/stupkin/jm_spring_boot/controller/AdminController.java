package net.stupkin.jm_spring_boot.controller;


import net.stupkin.jm_spring_boot.entity.Role;
import net.stupkin.jm_spring_boot.entity.User;
import net.stupkin.jm_spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers",allUsers);
        return "show-all-users";
    }


    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        List<Role> allRoles = userService.getAllRoles();
        model.addAttribute("allRoles", allRoles);
        return "new-user";
    }
    @Transactional
    @PostMapping()
    public String addNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @RequestParam (value ="roles", required = false) Long[] rolesId) {

        Set<Role> roles = new HashSet<>();
        for (Long id : rolesId) {
            roles.add(userService.getRoleById(id));
        }
        user.setRoles(roles);
        if (bindingResult.hasErrors()) {
            return "new-user";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        List<Role> allRoles = userService.getAllRoles();
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", allRoles);

        return "edit-user";
    }


    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @RequestParam (value ="roles", required = false) Long[] rolesId){


        Set<Role> roles = new HashSet<>();
        if(rolesId != null) {
            for (Long id : rolesId) {
                roles.add(userService.getRoleById(id));
            }
            user.setRoles(roles);
        } else {
            user.setRoles(userService.getUserById(user.getId()).getRoles());
        }

        if (bindingResult.hasErrors()) {
            return "edit-user";
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
