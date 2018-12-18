package com.corner.eshop.product.mapper;

import com.corner.eshop.product.entity.ProductIntro;
import java.util.List;

public interface ProductIntroMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductIntro record);

    ProductIntro selectByPrimaryKey(Integer id);

    List<ProductIntro> selectAll();

    int updateByPrimaryKey(ProductIntro record);
}