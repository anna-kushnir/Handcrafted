package com.annak.handcrafted.controller;

import com.annak.handcrafted.dto.ProductInCartDto;
import com.annak.handcrafted.entity.User;
import com.annak.handcrafted.service.ProductInCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final UserDetailsService userDetailsService;
    private final ProductInCartService productInCartService;

    @GetMapping
    public String getAllProductsInCart(Principal principal, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        List<ProductInCartDto> productInCartDtoList = productInCartService.getAllByUser(user);
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ProductInCartDto productInCartDto : productInCartDtoList) {
            totalPrice = totalPrice.add(productInCartDto.getCost());
        }
        model.addAttribute("productsInCart", productInCartDtoList);
        model.addAttribute("totalPrice", totalPrice);
        return "user/cart";
    }
}
