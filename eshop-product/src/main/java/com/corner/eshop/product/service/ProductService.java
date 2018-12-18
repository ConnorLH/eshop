package com.corner.eshop.product.service;

import com.corner.eshop.product.entity.Product;

public interface ProductService {

    void add(Product product,String operationType);

    void delete(Integer id,String operationType);

    void update(Product product,String operationType);

    Product selectById(Integer id);
}
