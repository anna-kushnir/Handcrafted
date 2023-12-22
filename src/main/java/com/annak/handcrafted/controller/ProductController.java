package com.annak.handcrafted.controller;

import com.annak.handcrafted.service.CategoryService;
import com.annak.handcrafted.service.ColorService;
import com.annak.handcrafted.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ColorService colorService;

    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("colors", colorService.getAll());
        return "user/list_of_products";
    }
}
