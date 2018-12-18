package com.corner.eshop.product.mapper;

import com.corner.eshop.product.entity.ProductProperty;
import com.corner.eshop.product.entity.ProductSpecification;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductSpecificationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductSpecification record);

    ProductSpecification selectByPrimaryKey(Integer id);

    List<ProductSpecification> selectAll();

    int updateByPrimaryKey(ProductSpecification record);

    @Select("SELECT * FROM product_specification WHERE product_id=#{productId}")
    ProductSpecification selectByProductId(Integer productId);
}