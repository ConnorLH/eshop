package com.corner.eshop.inventory.mapper;

import com.corner.eshop.inventory.entity.ProductInventory;
import java.util.List;

public interface ProductInventoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductInventory record);

    ProductInventory selectByPrimaryKey(Integer id);

    List<ProductInventory> selectAll();

    int updateByPrimaryKey(ProductInventory record);
}