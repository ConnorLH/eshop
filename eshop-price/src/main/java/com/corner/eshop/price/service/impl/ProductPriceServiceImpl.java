package com.corner.eshop.price.service.impl;

import com.corner.eshop.price.entity.ProductPrice;
import com.corner.eshop.price.mapper.ProductPriceMapper;
import com.corner.eshop.price.service.ProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.server.ServerEndpoint;

@Service
public class ProductPriceServiceImpl implements ProductPriceService {

    @Autowired
    private ProductPriceMapper productPriceMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional
    public void add(ProductPrice productPrice) {
        productPriceMapper.insert(productPrice);
        redisTemplate.opsForValue().set("product:price:"+productPrice.getProductId(),productPrice);
    }

    @Transactional
    public void delete(Integer id) {
        ProductPrice productPrice = productPriceMapper.selectByPrimaryKey(id);
        productPriceMapper.deleteByPrimaryKey(id);
        redisTemplate.delete("product:price:"+productPrice.getProductId());
    }

    @Transactional
    public void update(ProductPrice productPrice) {
        productPriceMapper.updateByPrimaryKey(productPrice);
        redisTemplate.opsForValue().set("product:price:"+productPrice.getProductId(),productPrice);
    }

    public ProductPrice selectById(Integer id) {
        return productPriceMapper.selectByPrimaryKey(id);
    }
}
