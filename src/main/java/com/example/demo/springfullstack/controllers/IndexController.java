package com.example.demo.springfullstack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "views/loginForm";
    }
}
