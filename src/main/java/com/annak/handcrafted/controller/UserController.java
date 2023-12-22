package com.annak.handcrafted.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {

    @GetMapping("/")
    public String start() {
        return "user/start";
    }

    @GetMapping("/register")
    public String register() {
        return "user/registration";
    }

    @GetMapping("/authorize")
    public String authorize() {
        return "user/authorization";
    }

    @GetMapping("/menu")
    public String viewMenu() {
        return "user/main_menu";
    }
}
