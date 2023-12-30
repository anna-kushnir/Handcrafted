package com.annak.handcrafted.controller;

import com.annak.handcrafted.entity.User;
import com.annak.handcrafted.exception.ResourceNotFoundException;
import com.annak.handcrafted.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ColorService colorService;
    private final UserDetailsService userDetailsService;
    private final ProductInCartService productInCartService;
    private final FavoriteProductService favoriteProductService;

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id , Model model) {
        var productDtoOptional = productService.getNotDeletedById(id);
        if (productDtoOptional.isPresent()) {
            var productDto = productDtoOptional.get();
            model.addAttribute("product" , productDto);
            return "user/product";
        } else {
            throw new ResourceNotFoundException("Product with id " + id + " was not found");
        }
    }

    @GetMapping
    public String getAllProducts(Model model ,
                                 @RequestParam(name = "sortByCost", required = false, defaultValue = "false") boolean sortByCost ,
                                 @RequestParam(name = "sortByCostAsc", required = false, defaultValue = "true") boolean sortByCostAsc ,
                                 @RequestParam(name = "sortByNewness", required = false, defaultValue = "false") boolean sortByNewness ,
                                 @RequestParam(name = "sortByNewnessAsc", required = false, defaultValue = "true") boolean sortByNewnessAsc ,
                                 @RequestParam(name = "priceLimitFrom", required = false, defaultValue = "0") BigDecimal priceLimitFrom ,
                                 @RequestParam(name = "priceLimitTo", required = false, defaultValue = "10000") BigDecimal priceLimitTo ,
                                 @RequestParam(name = "categoryId", required = false, defaultValue = "0") Long categoryId
    ) {
        model.addAttribute("categories" , categoryService.getAll());
        model.addAttribute("colors" , colorService.getAll());
        if (categoryId != 0) {
            model.addAttribute("products" , productService.getAllNotDeletedByCategoryId(categoryId));
        } else {
            model.addAttribute("products" ,
                    productService.getAllNotDeletedByFilter(sortByCost , sortByCostAsc , sortByNewness , sortByNewnessAsc , priceLimitFrom , priceLimitTo));
        }
        return "user/list_of_products";
    }

    @PostMapping("/{id}/addToFavorites")
    public String addProductWithIdToFavorites(Principal principal , @PathVariable Long id , RedirectAttributes redirectAttributes) {
        var user = (User) userDetailsService.loadUserByUsername(principal.getName());
        var productDtoOptional = productService.getNotDeletedById(id);
        if (productDtoOptional.isPresent()) {
            var productDto = productDtoOptional.get();
            var result = favoriteProductService.saveOrDeleteIfExists(user , productDto);
            redirectAttributes.addFlashAttribute("msgAddToFavorites" , result);
            return "redirect:/products/{id}";
        } else {
            throw new ResourceNotFoundException("Product with id " + id + " was not found");
        }
    }

    @PostMapping("/{id}/addToCart")
    public String addProductWithIdToCart(Principal principal , @PathVariable Long id , RedirectAttributes redirectAttributes) {
        var user = (User) userDetailsService.loadUserByUsername(principal.getName());
        var productDtoOptional = productService.getNotDeletedById(id);
        if (productDtoOptional.isPresent()) {
            var productDto = productDtoOptional.get();
            var result = productInCartService.saveOrDeleteIfExists(user , productDto);
            redirectAttributes.addFlashAttribute("msgAddToCart" , result);
            return "redirect:/products/{id}";
        } else {
            throw new ResourceNotFoundException("Product with id " + id + " was not found");
        }
    }
}
