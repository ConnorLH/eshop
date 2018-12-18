package com.corner.eshop.product.controller;

import com.corner.eshop.product.entity.Product;
import com.corner.eshop.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public void add(Product product,String operationType) {
        productService.add(product,operationType);
    }

    @GetMapping("/delete")
    public void delete(Integer id,String operationType) {
        productService.delete(id,operationType);
    }

    @PostMapping("/update")
    public void update(Product product,String operationType) {
        productService.update(product,operationType);
    }

    @GetMapping("/findById")
    public Product selectById(Integer id) {
        return productService.selectById(id);
    }
}
