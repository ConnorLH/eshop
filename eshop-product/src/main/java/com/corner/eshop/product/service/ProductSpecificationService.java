package com.corner.eshop.product.service;

import com.corner.eshop.product.entity.ProductSpecification;

public interface ProductSpecificationService {

    void add(ProductSpecification productSpecification,String operationType);

    void delete(Integer id,String operationType);

    void update(ProductSpecification productSpecification,String operationType);

    ProductSpecification selectById(Integer id);

    ProductSpecification selectByProductId(Integer productId);
}
