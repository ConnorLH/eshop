package com.corner.eshop.product.service;

import com.corner.eshop.product.entity.Brand;

import java.util.List;

public interface BrandService {

    void add(Brand brand,String operationType);

    void delete(Integer id,String operationType);

    void update(Brand brand,String operationType);

    Brand selectById(Integer id);

    List<Brand> selectByIds(List<String> ids);
}
