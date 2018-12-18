package com.corner.eshop.product.controller;

import com.corner.eshop.product.entity.Brand;
import com.corner.eshop.product.entity.Category;
import com.corner.eshop.product.service.BrandService;
import com.corner.eshop.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping("/add")
    public void add(Brand brand,String operationType) {
        brandService.add(brand,operationType);
    }

    @GetMapping("/delete")
    public void delete(Integer id,String operationType) {
        brandService.delete(id,operationType);
    }

    @PostMapping("/update")
    public void update(Brand brand,String operationType) {
        brandService.update(brand,operationType);
    }

    @GetMapping("/findById")
    public Brand selectById(Integer id) {
        return brandService.selectById(id);
    }

    @GetMapping("/findByIds")
    public List<Brand> selectByIds(List<String> ids) {
        return brandService.selectByIds(ids);
    }
}
