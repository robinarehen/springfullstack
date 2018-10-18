package com.example.demo.springfullstack.controllers;

import com.example.demo.springfullstack.entities.Role;
import com.example.demo.springfullstack.entities.User;
import com.example.demo.springfullstack.services.RoleService;
import com.example.demo.springfullstack.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
public class RegisterController {

    private UserService userService;
    private RoleService roleService;

    public RegisterController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {

        User user = new User();

        user.setRoles(this.roleService.findAll());

        model.addAttribute("user", user);

        return "views/registerForm";
    }

    @PostMapping("/register")
    public String register(@RequestParam(defaultValue = "") String role, @Valid User user, BindingResult bindingResult, Model model) {

        boolean error = false;

        if (!bindingResult.hasErrors()) {

            if (this.userService.isUserExists(user.getEmail())) {

                model.addAttribute("userExist", true);

                error = true;

            } else {

                if (!role.isEmpty()) {
                    user.setRoles(Arrays.asList(new Role(role)));
                }

                this.userService.create(user);

                model.addAttribute("userCreated", true);
            }
        } else {
            error = true;
        }

        if (error){
            user.setRoles(this.roleService.findAll());
        }

        return "views/registerForm";
    }
}
