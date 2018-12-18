package com.corner.eshop.product.controller;

import com.corner.eshop.product.entity.ProductProperty;
import com.corner.eshop.product.service.ProductPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product-property")
public class ProductPropertyController {

    @Autowired
    private ProductPropertyService productPropertyService;

    @PostMapping("/add")
    public void add(ProductProperty productProperty,String operationType) {
        productPropertyService.add(productProperty,operationType);
    }

    @GetMapping("/delete")
    public void delete(Integer id,String operationType) {
        productPropertyService.delete(id,operationType);
    }

    @PostMapping("/update")
    public void update(ProductProperty productProperty,String operationType) {
        productPropertyService.update(productProperty,operationType);
    }

    @GetMapping("/findById")
    public ProductProperty selectById(Integer id) {
        return productPropertyService.selectById(id);
    }

    @GetMapping("/findByProductId")
    public List<ProductProperty> selectByProductId(Integer productId) {
        return productPropertyService.selectByProductId(productId);
    }
}
