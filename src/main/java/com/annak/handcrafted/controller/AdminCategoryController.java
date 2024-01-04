package com.annak.handcrafted.controller;

import com.annak.handcrafted.dto.CategoryDto;
import com.annak.handcrafted.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "admin/list_of_categories";
    }

    @GetMapping("/add")
    public String getAddNewCategoryForm(Model model) {
        model.addAttribute("category", new CategoryDto());
        return "admin/add_category";
    }

    @PostMapping
    public String addCategory(@ModelAttribute("category") CategoryDto categoryDto, RedirectAttributes redirectAttributes) {
        if (categoryDto.getId() != null) {
            redirectAttributes.addFlashAttribute("message", "Id of new category must be null!");
            return "redirect:/admin/categories";
        }
        categoryService.save(categoryDto);
        redirectAttributes.addFlashAttribute("message", "Category successfully added");
        return "redirect:/admin/categories";
    }

    @GetMapping("/{id}/edit")
    public String getCategoryToEditById(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        var categoryDtoOptional = categoryService.getById(id);
        if (categoryDtoOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Category with id <%s> was not found".formatted(id));
            return "redirect:/admin/categories";
        }
        var categoryDto = categoryDtoOptional.get();
        model.addAttribute("oldCategory", categoryDto);
        var newCategoryDto = new CategoryDto();
        newCategoryDto.setId(categoryDto.getId());
        model.addAttribute("newCategory", newCategoryDto);
        return "admin/edit_category";
    }

    @PostMapping("/{id}")
    public String editCategory(@ModelAttribute("category") CategoryDto categoryDto, RedirectAttributes redirectAttributes) {
        var result = categoryService.update(categoryDto);
        redirectAttributes.addFlashAttribute("message", result);
        return "redirect:/admin/categories";
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
