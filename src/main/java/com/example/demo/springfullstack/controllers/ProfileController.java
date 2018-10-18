package com.example.demo.springfullstack.controllers;

import com.example.demo.springfullstack.entities.User;
import com.example.demo.springfullstack.services.TaskService;
import com.example.demo.springfullstack.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ProfileController {

    private UserService userService;
    private TaskService taskService;

    public ProfileController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal){

        String email = principal.getName();

        User user = this.userService.findOne(email);

        model.addAttribute("tasks",this.taskService.findUserTask(user));

        return "views/profile";
    }
}
