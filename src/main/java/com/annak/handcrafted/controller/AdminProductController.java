package com.annak.handcrafted.controller;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.service.CategoryService;
import com.annak.handcrafted.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String getAllProducts(Model model,
                                 @RequestParam(name = "categoryId", required = false, defaultValue = "0") Long categoryId,
                                 @RequestParam(name = "search", required = false) String search) {
        model.addAttribute("categories", categoryService.getAll());
        if (categoryId != 0) {
            model.addAttribute("products", productService.getAllNotDeletedByCategoryId(categoryId));
        } else if (search != null) {
            model.addAttribute("products", productService.getAllNotDeletedBySearchLine(search));
        } else {
            model.addAttribute("products", productService.getAllNotDeleted());
        }
        return "admin/list_of_products";
    }

    @GetMapping("/add")
    public String getAddNewProductForm(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "admin/add_product";
    }

    @PostMapping
    public String addProduct(@RequestBody ProductDto productDto, RedirectAttributes redirectAttributes) {
        if (productDto.getId() != null) {
            redirectAttributes.addFlashAttribute("message", "Id of new product must be null!");
            return "redirect:/admin/products";
        }
        if (!productDto.isInStock()) {
            productDto.setQuantity(0L);
        }
        productDto.setCategory(categoryService.getEntityById(productDto.getCategoryId()));
        productService.save(productDto);
        redirectAttributes.addFlashAttribute("message", "Product successfully added");
        return "redirect:/admin/products";
    }

    @GetMapping("/{id}/edit")
    public String getProductToEditById(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        var productDtoOptional = productService.getNotDeletedById(id);
        if (productDtoOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Product with id <%s> was not found".formatted(id));
            return "redirect:/admin/products";
        }
        model.addAttribute("oldProduct", productDtoOptional.get());
        model.addAttribute("categories", categoryService.getAll());
        return "admin/edit_product";
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editProduct(@RequestBody ProductDto productDto) {
        productService.update(productDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
