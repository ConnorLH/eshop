package com.corner.eshop.price.mapper;

import com.corner.eshop.price.entity.ProductPrice;
import java.util.List;

public interface ProductPriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductPrice record);

    ProductPrice selectByPrimaryKey(Integer id);

    List<ProductPrice> selectAll();

    int updateByPrimaryKey(ProductPrice record);
}