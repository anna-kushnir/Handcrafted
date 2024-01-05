package com.annak.handcrafted.controller;

import com.annak.handcrafted.dto.NewUserDto;
import com.annak.handcrafted.dto.UserDto;
import com.annak.handcrafted.entity.User;
import com.annak.handcrafted.entity.embedded.Role;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String register(@ModelAttribute("user") NewUserDto newUserDto, Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/menu";
        }
        model.addAttribute("user", (newUserDto == null) ? new NewUserDto() : newUserDto);
        return "registration";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute("user") @Valid NewUserDto newUserDto, Model model) {
        try {
            if (!newUserDto.getPassword().equals(newUserDto.getSubmitPassword()))
                throw new Exception("Passwords do not match");
            newUserDto.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
            newUserDto.setSubmitPassword(passwordEncoder.encode(newUserDto.getSubmitPassword()));
            userService.save(newUserDto);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            newUserDto.setPassword(null);
            newUserDto.setSubmitPassword(null);
            model.addAttribute("user", newUserDto);
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

    @GetMapping("/profile")
    public String viewAndEditProfile(Principal principal, Model model) {
        model.addAttribute("oldUser", userDetailsService.loadUserByUsername(principal.getName()));
        model.addAttribute("newUser", new UserDto());
        return "user/profile";
    }

    @PostMapping("/editUser")
    public String editProfile(Principal principal, @ModelAttribute("newUser") @Valid UserDto userDto, RedirectAttributes redirectAttributes) {
        var user = (User)userDetailsService.loadUserByUsername(principal.getName());
        userDto.setId(user.getId());
        userDto.setUserName(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setActive(true);
        var result = userService.update(userDto);
        redirectAttributes.addFlashAttribute("message", result);
        return "redirect:/profile";
    }
}
