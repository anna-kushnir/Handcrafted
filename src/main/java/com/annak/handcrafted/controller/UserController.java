package com.annak.handcrafted.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/start")
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
}
