package com.corner.eshop.product.service;

import com.corner.eshop.product.entity.ProductIntro;

public interface ProductIntroService {

    void add(ProductIntro productIntro,String operationType);

    void delete(Integer id,String operationType);

    void update(ProductIntro productIntro,String operationType);

    ProductIntro selectById(Integer id);
}
