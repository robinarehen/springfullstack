package com.example.demo.springfullstack.controllers;

import com.example.demo.springfullstack.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUsers(@RequestParam(defaultValue = "") String name, Model model){

        model.addAttribute("users",this.userService.findByName(name));

        return "views/listUsers";
    }
}
