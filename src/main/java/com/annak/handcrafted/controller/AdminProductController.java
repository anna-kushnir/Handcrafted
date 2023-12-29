package com.annak.handcrafted.controller;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.exception.ResourceNotFoundException;
import com.annak.handcrafted.service.CategoryService;
import com.annak.handcrafted.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/add")
    public String getAddNewProductForm(Model model) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryService.getAll());
        return "admin/add_product";
    }

    @PostMapping
    public String addProduct(@ModelAttribute("product") ProductDto productDto, RedirectAttributes redirectAttributes) {
        if (productDto.getId() != null) {
            redirectAttributes.addFlashAttribute("message", "Id of new product must be null!");
            return "redirect:/admin/products";
        }
        if (!productDto.isInStock()) {
            productDto.setQuantity(0L);
        }
        productService.save(productDto);
        redirectAttributes.addFlashAttribute("message", "Product successfully added");
        return "redirect:/admin/products";
    }

    @GetMapping("/{id}/edit")
    public String getProductById(@PathVariable Long id, Model model) {
        Optional<ProductDto> productDtoOptional = productService.getById(id);
        if (productDtoOptional.isPresent()) {
            ProductDto productDto = productDtoOptional.get();
            model.addAttribute("oldProduct", productDto);
            ProductDto newProductDto = new ProductDto();
            newProductDto.setId(productDto.getId());
            model.addAttribute("newProduct", newProductDto);
            model.addAttribute("categories", categoryService.getAll());
            return "admin/edit_product";
        }
        else {
            throw new ResourceNotFoundException("Product with id " + id + " was not found");
        }
    }

    @PostMapping("/{id}")
    public String editProduct(@ModelAttribute("product") ProductDto productDto, RedirectAttributes redirectAttributes) {
        try {
            productService.update(productDto);
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/products";
        }
        return "redirect:/admin/products";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteProductById(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String result = productService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", result);
        return "redirect:/admin/products";
    }
}
