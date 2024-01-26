package com.annak.handcrafted.controller;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.entity.User;
import com.annak.handcrafted.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

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
    public String getProductById(Principal principal, @PathVariable Long id , Model model, RedirectAttributes redirectAttributes) {
        var user = (User) userDetailsService.loadUserByUsername(principal.getName());
        var productDtoOptional = productService.getNotDeletedById(id);
        if (productDtoOptional.isPresent()) {
            var productDto = productDtoOptional.get();
            model.addAttribute("product" , productDto);
            List<ProductDto> productDtoList = productService.getRecommendedByProductIdAndUserId(productDto.getId(), user.getId());
            model.addAttribute("recommendedProducts", productDtoList);
            return "user/product";
        } else {
            redirectAttributes.addFlashAttribute("message", "Product with id <%s> was not found".formatted(id));
            return "redirect:/products";
        }
    }

    @GetMapping
    public String getAllProducts(Model model,
                                 @RequestParam(name = "sortByCost", required = false, defaultValue = "false") boolean sortByCost ,
                                 @RequestParam(name = "sortByCostAsc", required = false, defaultValue = "true") boolean sortByCostAsc ,
                                 @RequestParam(name = "sortByNewness", required = false, defaultValue = "false") boolean sortByNewness ,
                                 @RequestParam(name = "sortByNewnessAsc", required = false, defaultValue = "true") boolean sortByNewnessAsc ,
                                 @RequestParam(name = "priceLimitFrom", required = false, defaultValue = "0") BigDecimal priceLimitFrom ,
                                 @RequestParam(name = "priceLimitTo", required = false, defaultValue = "10000") BigDecimal priceLimitTo ,
                                 @RequestParam(name = "categoryId", required = false, defaultValue = "0") Long categoryId,
                                 @RequestParam(name = "search", required = false) String search
    ) {
        model.addAttribute("categories" , categoryService.getAll());
        model.addAttribute("colors" , colorService.getAll());
        if (categoryId != 0) {
            model.addAttribute("products" , productService.getAllNotDeletedByCategoryId(categoryId));
        } else if (search != null) {
            model.addAttribute("products", productService.getAllNotDeletedBySearchLine(search));
        } else {
            model.addAttribute("products" ,
                    productService.getAllNotDeletedByFilter(sortByCost , sortByCostAsc , sortByNewness , sortByNewnessAsc , priceLimitFrom , priceLimitTo));
        }
        return "user/list_of_products";
    }

    @PostMapping("/{id}/addToFavorites")
    public String addProductWithIdToFavorites(Principal principal, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        var user = (User) userDetailsService.loadUserByUsername(principal.getName());
        var productDtoOptional = productService.getNotDeletedById(id);
        if (productDtoOptional.isPresent()) {
            var productDto = productDtoOptional.get();
            var result = favoriteProductService.saveOrDeleteIfExists(user , productDto);
            redirectAttributes.addFlashAttribute("msgAddToFavorites" , result);
            return "redirect:/products/{id}";
        } else {
            return "redirect:/products";
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
            return "redirect:/products";
        }
    }
}
