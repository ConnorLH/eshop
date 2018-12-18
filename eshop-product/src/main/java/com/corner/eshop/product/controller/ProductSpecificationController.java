package com.corner.eshop.product.controller;

import com.corner.eshop.product.entity.ProductSpecification;
import com.corner.eshop.product.service.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-specification")
public class ProductSpecificationController {

    @Autowired
    private ProductSpecificationService productSpecificationService;

    @PostMapping("/add")
    public void add(ProductSpecification productSpecification,String operationType) {
        productSpecificationService.add(productSpecification,operationType);
    }

    @GetMapping("/delete")
    public void delete(Integer id,String operationType) {
        productSpecificationService.delete(id,operationType);
    }

    @PostMapping("/update")
    public void update(ProductSpecification productSpecification,String operationType) {
        productSpecificationService.update(productSpecification,operationType);
    }

    @GetMapping("/findById")
    public ProductSpecification selectById(Integer id) {
        return productSpecificationService.selectById(id);
    }

    @GetMapping("/findByProductId")
    public ProductSpecification selectByProductId(Integer productId) {
        return productSpecificationService.selectByProductId(productId);
    }
}
