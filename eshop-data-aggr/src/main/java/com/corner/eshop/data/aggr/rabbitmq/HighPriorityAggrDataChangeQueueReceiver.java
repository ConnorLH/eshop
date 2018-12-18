package com.corner.eshop.data.aggr.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 监听各维度原子数据变化，重新聚合该维度的数据存入缓存中
 */
@Slf4j
@Component
@RabbitListener(queues = "high-priority-aggr-data-change-queue")
public class HighPriorityAggrDataChangeQueueReceiver {

    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitHandler
    public void process(String message){
        log.info("AggrDataChangeQueueReceiver received message:{}",message);
        JSONObject messageJSONObject = JSONObject.parseObject(message);
        String changeType = messageJSONObject.getString("change_type");
        switch (changeType) {
            case "brand":
                processBrandDimDataChange(messageJSONObject);
                break;
            case "category":
                processCategoryDimDataChange(messageJSONObject);
                break;
            case "product_intro":
                processProductIntroDimDataChange(messageJSONObject);
                break;
            case "product":
                processProductDimDataChange(messageJSONObject);
                break;
        }
    }

    private void processBrandDimDataChange(JSONObject messageJSONObject) {
        Long id = messageJSONObject.getLong("id");

        // 多此一举，看一下，查出来一个品牌数据，然后直接就原样写redis
        // 实际上是这样子的，我们这里是简化了数据结构和业务，实际上任何一个维度数据都不可能只有一个原子数据
        // 品牌数据，肯定是结构多变的，结构比较复杂，有很多不同的表，不同的原子数据
        // 实际上这里肯定是要将一个品牌对应的多个原子数据都从redis查询出来，然后聚合之后写入redis
        String dataJSON = (String)redisTemplate.opsForValue().get("brand:" + id);

        if(dataJSON != null && !"".equals(dataJSON)) {
            redisTemplate.opsForValue().set("dim:brand:" + id, dataJSON);
        } else {
            redisTemplate.delete("dim:brand:" + id);
        }
    }

    private void processCategoryDimDataChange(JSONObject messageJSONObject) {
        Long id = messageJSONObject.getLong("id");

        // 多此一举，看一下，查出来一个品牌数据，然后直接就原样写redis
        // 实际上是这样子的，我们这里是简化了数据结构和业务，实际上任何一个维度数据都不可能只有一个原子数据
        // 品牌数据，肯定是结构多变的，结构比较复杂，有很多不同的表，不同的原子数据
        // 实际上这里肯定是要将一个品牌对应的多个原子数据都从redis查询出来，然后聚合之后写入redis
        String dataJSON = (String)redisTemplate.opsForValue().get("category:" + id);

        if(dataJSON != null && !"".equals(dataJSON)) {
            redisTemplate.opsForValue().set("dim:category:" + id, dataJSON);
        } else {
            redisTemplate.delete("dim:category:" + id);
        }
    }

    private void processProductIntroDimDataChange(JSONObject messageJSONObject) {
        Long id = messageJSONObject.getLong("id");

        String dataJSON = (String)redisTemplate.opsForValue().get("product:intro:" + id);
        if(dataJSON != null && !"".equals(dataJSON)) {
            redisTemplate.opsForValue().set("dim:product:intro:" + id, dataJSON);
        } else {
            redisTemplate.delete("dim:product:intro:" + id);
        }
    }

    private void processProductDimDataChange(JSONObject messageJSONObject) {
        Long id = messageJSONObject.getLong("id");

        String productDataJSON = (String)redisTemplate.opsForValue().get("product:" + id);
        log.info("AggrService Obtain product info from redis:{}",productDataJSON);
        if(productDataJSON != null && !"".equals(productDataJSON)) {
            JSONObject productDataJSONObject = JSONObject.parseObject(productDataJSON);

            String productPropertyDataJSON = (String)redisTemplate.opsForValue().get("product:property:" + id);
            if(productPropertyDataJSON != null && !"".equals(productPropertyDataJSON)) {
                productDataJSONObject.put("product_property", JSONObject.parse(productPropertyDataJSON));
            }

            String productSpecificationDataJSON = (String)redisTemplate.opsForValue().get("product:specification:" + id);
            if(productSpecificationDataJSON != null && !"".equals(productSpecificationDataJSON)) {
                productDataJSONObject.put("product_specification", JSONObject.parse(productSpecificationDataJSON));
            }
            redisTemplate.opsForValue().set("dim:product:" + id, productDataJSONObject.toJSONString());
            log.info("Aggr service set product dim data to redis:{}",productDataJSONObject.toJSONString());
        } else {
            redisTemplate.delete("dim:product:" + id);
            log.info("Aggr service can not obtain product info from redis,delete product dim data");
        }
    }

}
