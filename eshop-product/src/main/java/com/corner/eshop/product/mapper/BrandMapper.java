package com.corner.eshop.product.mapper;

import com.corner.eshop.product.entity.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BrandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Brand record);

    Brand selectByPrimaryKey(Integer id);

    List<Brand> selectAll();

    int updateByPrimaryKey(Brand record);

    @Select("SELECT * FROM brand WHERE id in (${ids})")
    List<Brand> selectByIds(@Param("ids") String ids);
}