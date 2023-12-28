package com.annak.handcrafted.controller;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.dto.ProductInCartDto;
import com.annak.handcrafted.entity.User;
import com.annak.handcrafted.exception.ResourceNotFoundException;
import com.annak.handcrafted.service.FavoriteProductService;
import com.annak.handcrafted.service.ProductInCartService;
import com.annak.handcrafted.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final UserDetailsService userDetailsService;
    private final ProductInCartService productInCartService;
    private final FavoriteProductService favoriteProductService;
    private final ProductService productService;

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

    @DeleteMapping("/products/{id}/deleteFromCart")
    public String deleteProductWithIdFromCart(Principal principal, @PathVariable Long id) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Optional<ProductDto> productDtoOptional = productService.getById(id);
        if (productDtoOptional.isPresent()) {
            ProductDto productDto = productDtoOptional.get();
            productInCartService.delete(user, productDto);
            return "redirect:/cart";
        }
        else {
            throw new ResourceNotFoundException("Product with id " + id + " was not found");
        }
    }

    @PutMapping("/products/{id}/transferToFavorite")
    public String transferProductWithIdToFavorite(Principal principal, @PathVariable Long id) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Optional<ProductDto> productDtoOptional = productService.getById(id);
        if (productDtoOptional.isPresent()) {
            ProductDto productDto = productDtoOptional.get();
            productInCartService.delete(user, productDto);
            favoriteProductService.save(user, productDto);
            return "redirect:/cart";
        }
        else {
            throw new ResourceNotFoundException("Product with id " + id + " was not found");
        }
    }
}
