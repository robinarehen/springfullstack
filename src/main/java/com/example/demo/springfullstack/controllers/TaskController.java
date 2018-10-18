package com.example.demo.springfullstack.controllers;

import com.example.demo.springfullstack.entities.Task;
import com.example.demo.springfullstack.entities.User;
import com.example.demo.springfullstack.services.TaskService;
import com.example.demo.springfullstack.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class TaskController {

    private UserService userService;
    private TaskService taskService;

    public TaskController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/addTask")
    public String addTaskForm(String email, Model model, HttpSession session) {

        session.setAttribute("email", email);

        model.addAttribute("task", new Task());

        return "views/addTaskForm";
    }

    @PostMapping("/addTask")
    public String addTask(@Valid Task task, BindingResult bindingResult, Model model, HttpSession session) {

        if (!bindingResult.hasErrors()) {

            String email = session.getAttribute("email").toString();

            User user = this.userService.findOne(email);

            this.taskService.addTask(task, user);

            return "redirect:/users";
        }

        return "views/addTaskForm";
    }
}
