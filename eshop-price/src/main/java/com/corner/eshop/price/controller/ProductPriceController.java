package com.corner.eshop.price.controller;

import com.corner.eshop.price.entity.ProductPrice;
import com.corner.eshop.price.service.ProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/productPrice")
public class ProductPriceController {

    @Autowired
    private ProductPriceService productPriceService;

    @PostMapping("/add")
    public void add(ProductPrice productPrice) {
        productPriceService.add(productPrice);
    }

    @GetMapping("/delete")
    public void delete(Integer id) {
        productPriceService.delete(id);
    }

    @PostMapping("/update")
    public void update(ProductPrice productPrice) {
        productPriceService.update(productPrice);
    }

    @GetMapping("/select")
    public ProductPrice selectById(Integer id) {
        return productPriceService.selectById(id);
    }
}
