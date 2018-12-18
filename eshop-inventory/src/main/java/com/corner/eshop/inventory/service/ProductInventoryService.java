package com.corner.eshop.inventory.service;

import com.corner.eshop.inventory.entity.ProductInventory;

public interface ProductInventoryService {

    void add(ProductInventory productInventory);

    void delete(Integer id);

    void update(ProductInventory productInventory);

    ProductInventory selectById(Integer id);
}
