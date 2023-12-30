package com.annak.handcrafted.controller;

import com.annak.handcrafted.dto.OrderDto;
import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.dto.ProductInCartDto;
import com.annak.handcrafted.entity.TypeOfReceipt;
import com.annak.handcrafted.entity.User;
import com.annak.handcrafted.exception.ResourceNotFoundException;
import com.annak.handcrafted.service.FavoriteProductService;
import com.annak.handcrafted.service.ProductInCartService;
import com.annak.handcrafted.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping ("/cart")
@RequiredArgsConstructor
public class CartController {

    private final UserDetailsService userDetailsService;
    private final ProductInCartService productInCartService;
    private final FavoriteProductService favoriteProductService;
    private final ProductService productService;

    @GetMapping
    public String getAllProductsInCart(Principal principal, Model model) {
        var user = (User) userDetailsService.loadUserByUsername(principal.getName());
        List<ProductInCartDto> productInCartDtoList = productInCartService.getAllByUser(user);
        model.addAttribute("productsInCart", productInCartDtoList);
        model.addAttribute("totalPrice", getTotalPriceOfProductsInCart(productInCartDtoList));
        return "user/cart";
    }

    @DeleteMapping ("/products/{id}/deleteFromCart")
    public ResponseEntity<?> deleteProductWithIdFromCart(Principal principal, @PathVariable Long id) {
        var user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Optional<ProductDto> productDtoOptional = productService.getById(id);
        if (productDtoOptional.isPresent()) {
            var productDto = productDtoOptional.get();
            productInCartService.delete(user, productDto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping ("/products/{id}/transferToFavorite")
    public String transferProductWithIdToFavorite(Principal principal, @PathVariable Long id) {
        var user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Optional<ProductDto> productDtoOptional = productService.getById(id);
        if (productDtoOptional.isPresent()) {
            var productDto = productDtoOptional.get();
            productInCartService.delete(user, productDto);
            favoriteProductService.save(user, productDto);
            return "redirect:/cart";
        } else {
            throw new ResourceNotFoundException("Product with id " + id + " was not found");
        }
    }

    @GetMapping ("/createOrder")
    public String checkProductsToOrder(Principal principal, Model model) {
        var user = (User) userDetailsService.loadUserByUsername(principal.getName());
        List<ProductInCartDto> productInCartDtoList = productInCartService.getAllByUser(user);
        model.addAttribute("productsInCart", productInCartDtoList);
        model.addAttribute("totalPrice", getTotalPriceOfProductsInCart(productInCartDtoList));
        return "user/create_order";
    }

    @GetMapping("/createOrder/fillInTheOrderData")
    public String fillInTheOrderData(Principal principal, Model model) {
        var user = (User) userDetailsService.loadUserByUsername(principal.getName());
        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(user.getId());
        orderDto.setUserPhone(user.getUserPhone());
        model.addAttribute("order", orderDto);
        model.addAttribute("typesOfReceipt", TypeOfReceipt.values());
        return "user/fill_order_data";
    }

    @PostMapping("/createOrder")
    public String createOrder(Principal principal, @ModelAttribute("order") OrderDto orderDto, RedirectAttributes redirectAttributes) {
        orderDto.setFormationDate(LocalDateTime.now());
        return "redirect:/cart";
    }

    private BigDecimal getTotalPriceOfProductsInCart(List<ProductInCartDto> productInCartDtoList) {
        var totalPrice = BigDecimal.ZERO;
        for (var productInCartDto : productInCartDtoList) {
            totalPrice = totalPrice.add(productInCartDto.getCost());
        }
        return totalPrice;
    }
}
