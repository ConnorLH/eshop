package com.corner.eshop.data.link.feignclient;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EshopProductServiceFallback implements EshopProductService {
    @Override
    public String findBrandById(Long id) {
        return null;
    }

    @Override
    public String findBrandByIds(List<String> ids) {
        return null;
    }

    @Override
    public String findCategoryById(Long id) {
        return null;
    }

    @Override
    public String findProductIntroById(Long id) {
        return null;
    }

    @Override
    public String findProductPropertyById(Long id) {
        return null;
    }

    @Override
    public String findProductPropertyByProductId(Long productId) {
        return null;
    }

    @Override
    public String findProductById(Long id) {
        return null;
    }

    @Override
    public String findProductSpecificationById(Long id) {
        return null;
    }

    @Override
    public String findProductSpecificationByProductId(Long productId) {
        return null;
    }
}
