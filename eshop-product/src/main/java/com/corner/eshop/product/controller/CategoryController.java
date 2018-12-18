package com.corner.eshop.product.controller;

import com.corner.eshop.product.entity.Category;
import com.corner.eshop.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public void add(Category category,String operationType) {
        categoryService.add(category,operationType);
    }

    @GetMapping("/delete")
    public void delete(Integer id,String operationType) {
        categoryService.delete(id,operationType);
    }

    @PostMapping("/update")
    public void update(Category category,String operationType) {
        categoryService.update(category,operationType);
    }

    @GetMapping("/findById")
    public Category selectById(Integer id) {
        return categoryService.selectById(id);
    }
}
