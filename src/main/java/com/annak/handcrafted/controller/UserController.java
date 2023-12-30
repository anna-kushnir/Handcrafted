package com.annak.handcrafted.controller;

import com.annak.handcrafted.dto.UserDto;
import com.annak.handcrafted.entity.Role;
import com.annak.handcrafted.exception.ResourceUniqueViolationException;
import com.annak.handcrafted.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @GetMapping("/")
    public String start(Principal principal) {
        if (principal != null) {
            return "redirect:/menu";
        }
        return "start";
    }

    @GetMapping("/authorize")
    public String authorize(Principal principal) {
        if (principal != null) {
            return "redirect:/menu";
        }
        return "authorization";
    }

    @GetMapping("/register")
    public String register(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/menu";
        }
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute("user") @Valid UserDto userDto, Model model) {
        try {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userService.save(userDto);
        } catch (ResourceUniqueViolationException e) {
            model.addAttribute("message", e.getMessage());
            return "registration";
        }
        return "redirect:/authorize";
    }

    @GetMapping("/menu")
    public String viewMenu(Principal principal) {
        if (userDetailsService.loadUserByUsername(principal.getName()).getAuthorities().contains(Role.ADMIN)) {
            return "redirect:/admin/menu";
        }
        return "user/main_menu";
    }
}
