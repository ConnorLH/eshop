package com.corner.eshop.product.mapper;

import com.corner.eshop.product.entity.ProductProperty;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductPropertyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductProperty record);

    ProductProperty selectByPrimaryKey(Integer id);

    List<ProductProperty> selectAll();

    int updateByPrimaryKey(ProductProperty record);

    @Select("SELECT * FROM product_property WHERE product_id=#{productId}")
    List<ProductProperty> selectByProductId(Integer productId);
}