package com.corner.eshop.product.service;

import com.corner.eshop.product.entity.ProductProperty;

import java.util.List;

public interface ProductPropertyService {

    void add(ProductProperty productProperty,String operationType);

    void delete(Integer id,String operationType);

    void update(ProductProperty productProperty,String operationType);

    ProductProperty selectById(Integer id);

    List<ProductProperty> selectByProductId(Integer productId);
}
