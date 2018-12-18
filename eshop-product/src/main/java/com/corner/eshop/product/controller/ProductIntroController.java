package com.corner.eshop.product.controller;

import com.corner.eshop.product.entity.ProductIntro;
import com.corner.eshop.product.entity.ProductProperty;
import com.corner.eshop.product.service.ProductIntroService;
import com.corner.eshop.product.service.ProductPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-intro")
public class ProductIntroController {

    @Autowired
    private ProductIntroService productIntroService;

    @PostMapping("/add")
    public void add(ProductIntro productIntro,String operationType) {
        productIntroService.add(productIntro,operationType);
    }

    @GetMapping("/delete")
    public void delete(Integer id,String operationType) {
        productIntroService.delete(id,operationType);
    }

    @PostMapping("/update")
    public void update(ProductIntro productIntro,String operationType) {
        productIntroService.update(productIntro,operationType);
    }

    @GetMapping("/findById")
    public ProductIntro selectById(Integer id) {
        return productIntroService.selectById(id);
    }
}
