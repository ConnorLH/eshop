package com.corner.eshop.price.service;

import com.corner.eshop.price.entity.ProductPrice;

public interface ProductPriceService {

    void add(ProductPrice productPrice);

    void delete(Integer id);

    void update(ProductPrice productPrice);

    ProductPrice selectById(Integer id);
}
