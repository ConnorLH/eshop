package com.corner.eshop.inventory.service.impl;

import com.corner.eshop.inventory.entity.ProductInventory;
import com.corner.eshop.inventory.mapper.ProductInventoryMapper;
import com.corner.eshop.inventory.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {

    @Autowired
    private ProductInventoryMapper productInventoryMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional
    public void add(ProductInventory productInventory) {
        productInventoryMapper.insert(productInventory);
        redisTemplate.opsForValue().set("product:inventory:"+productInventory.getProductId(),productInventory);
    }

    @Transactional
    public void delete(Integer id) {
        ProductInventory productInventory = productInventoryMapper.selectByPrimaryKey(id);
        productInventoryMapper.deleteByPrimaryKey(id);
        redisTemplate.delete("product:inventory:"+productInventory.getProductId());
    }

    @Transactional
    public void update(ProductInventory productInventory) {
        productInventoryMapper.updateByPrimaryKey(productInventory);
        redisTemplate.opsForValue().set("product:inventory:"+productInventory.getProductId(),productInventory);
    }

    public ProductInventory selectById(Integer id) {
        return productInventoryMapper.selectByPrimaryKey(id);
    }
}
