package com.corner.eshop.inventory.controller;

import com.corner.eshop.inventory.entity.ProductInventory;
import com.corner.eshop.inventory.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/productInventory")
public class ProductInventoryController {

    @Autowired
    private ProductInventoryService productInventoryService;

    @PostMapping("/add")
    public void add(ProductInventory productInventory) {
        productInventoryService.add(productInventory);
    }

    @GetMapping("/delete")
    public void delete(Integer id) {
        productInventoryService.delete(id);
    }

    @PostMapping("/update")
    public void update(ProductInventory productInventory) {
        productInventoryService.update(productInventory);
    }

    @GetMapping("/select")
    public ProductInventory selectById(Integer id) {
        return productInventoryService.selectById(id);
    }
}
