package com.annak.handcrafted.controller;

import com.annak.handcrafted.entity.User;
import com.annak.handcrafted.service.ProductService;
import com.annak.handcrafted.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @GetMapping
    public String getAllProductsInCart(Principal principal, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("productsInCart", userService.getProductsInCartByUserId(user.getId()));
        return "user/cart";
    }

}
