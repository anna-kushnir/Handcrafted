package com.annak.handcrafted.controller;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.exception.ResourceNotFoundException;
import com.annak.handcrafted.service.CategoryService;
import com.annak.handcrafted.service.ColorService;
import com.annak.handcrafted.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ColorService colorService;

    @GetMapping
    public String getAllProducts(Model model,
                                 @RequestParam(name = "sortByCost", required = false, defaultValue = "false") boolean sortByCost,
                                 @RequestParam(name = "sortByCostAsc", required = false, defaultValue = "false") boolean sortByCostAsc,
                                 @RequestParam(name = "sortByNewness", required = false, defaultValue = "false") boolean sortByNewness,
                                 @RequestParam(name = "sortByNewnessAsc", required = false, defaultValue = "false") boolean sortByNewnessAsc,
                                 @RequestParam(name = "priceLimitFrom", required = false, defaultValue = "0") BigDecimal priceLimitFrom,
                                 @RequestParam(name = "priceLimitTo", required = false, defaultValue = "10000") BigDecimal priceLimitTo
    ) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("colors", colorService.getAll());
        model.addAttribute("products", productService.getAllFiltered(sortByCost, sortByCostAsc, sortByNewness, sortByNewnessAsc, priceLimitFrom, priceLimitTo));
        return "user/list_of_products";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        Optional<ProductDto> productDtoOptional = productService.getById(id);
        if (productDtoOptional.isPresent()) {
            ProductDto productDto = productDtoOptional.get();
            model.addAttribute("product", productDto);
            return "user/product";
        }
        else {
            throw new ResourceNotFoundException("Product with id " + id + " was not found");
        }
    }
}
