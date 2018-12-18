package com.corner.eshop.product.service;

import com.corner.eshop.product.entity.Category;

public interface CategoryService {

    void add(Category category,String operationType);

    void delete(Integer id,String operationType);

    void update(Category category,String operationType);

    Category selectById(Integer id);
}
