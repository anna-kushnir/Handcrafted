package com.annak.handcrafted.controller;

import com.annak.handcrafted.dto.FavoriteProductDto;
import com.annak.handcrafted.entity.User;
import com.annak.handcrafted.service.FavoriteProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final UserDetailsService userDetailsService;
    private final FavoriteProductService favoriteProductService;

    @GetMapping
    public String getAllProductsInFavorites(Principal principal, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        List<FavoriteProductDto> favoriteProductDtoList = favoriteProductService.getAllByUser(user);
        model.addAttribute("favoriteProducts", favoriteProductDtoList);
        return "user/favorite_products";
    }
}
