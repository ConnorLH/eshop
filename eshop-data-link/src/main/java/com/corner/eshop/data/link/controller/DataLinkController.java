package com.corner.eshop.data.link.controller;

import com.alibaba.fastjson.JSONObject;
import com.corner.eshop.data.link.feignclient.EshopProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;

@Slf4j
@RestController
public class DataLinkController {

    @Autowired
    private EshopProductService eshopProductService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/product")
    public String getProduct(Long productId) {
        // 应该还有一套本地缓存，这里不做了
        String dimProductJson = (String)redisTemplate.opsForValue().get("dim:product:"+productId);
        log.info("从redis中获取商品信息：{}",dimProductJson);
        if(dimProductJson == null || "".equals(dimProductJson)) {
            // 缓存没有从基础服务拿
            String productDataJSON = eshopProductService.findProductById(productId);

            if(productDataJSON != null && !"".equals(productDataJSON)) {
                JSONObject productDataJSONObject = JSONObject.parseObject(productDataJSON);

                String productPropertyDataJSON = eshopProductService.findProductPropertyByProductId(productId);
                if(productPropertyDataJSON != null && !"".equals(productPropertyDataJSON)) {
                    productDataJSONObject.put("product_property", JSONObject.parse(productPropertyDataJSON));
                }

                String productSpecificationDataJSON = eshopProductService.findProductSpecificationByProductId(productId);
                if(productSpecificationDataJSON != null && !"".equals(productSpecificationDataJSON)) {
                    productDataJSONObject.put("product_specification", JSONObject.parse(productSpecificationDataJSON));
                }
                log.info("从商品服务获取原子数据后聚合成商品数据:{}",productDataJSONObject.toJSONString());
                redisTemplate.opsForValue().set("dim:product:" + productId, productDataJSONObject.toJSONString());

                return productDataJSONObject.toJSONString();
            }
        }

        return "";

    }
}
