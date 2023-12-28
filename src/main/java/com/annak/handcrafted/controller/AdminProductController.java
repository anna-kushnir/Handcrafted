package com.annak.handcrafted.controller;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.exception.ResourceNotFoundException;
import com.annak.handcrafted.service.CategoryService;
import com.annak.handcrafted.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("products", productService.getAll());
        return "admin/list_of_products";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        Optional<ProductDto> productDtoOptional = productService.getById(id);
        if (productDtoOptional.isPresent()) {
            ProductDto productDto = productDtoOptional.get();
            model.addAttribute("product", productDto);
            return "admin/edit_product";
        }
        else {
            throw new ResourceNotFoundException("Product with id " + id + " was not found");
        }
    }

    @PutMapping
    public String editProductById(@ModelAttribute("product") ProductDto productDto, Model model) {
        try {
            productService.update(productDto);
        } catch (ResourceNotFoundException e) {
            model.addAttribute("message", e.getMessage());
            return "redirect:/admin/products";
        }
        return "redirect:/admin/products";
    }

    @PostMapping
    public String addProduct(@ModelAttribute("product") ProductDto productDto, Model model) {
        if (productDto.getId() != null) {
            model.addAttribute("message", "Product id must be null!");
            return "redirect:/admin/products";
        }
        productService.save(productDto);
        return "redirect:/admin/products";
    }
}
