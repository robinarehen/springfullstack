package com.example.demo.springfullstack.controllers;

import com.example.demo.springfullstack.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String registerForm(Model model){

        model.addAttribute("user",new User());

        return "views/registerForm";
    }
}
