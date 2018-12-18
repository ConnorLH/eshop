package com.corner.eshop.data.sync.rabbitmq;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.corner.eshop.data.sync.feignclient.EshopProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 获取商品信息的各维度数据变更消息
 * 1、通过调用各基础服务获取变更后的数据
 * 2、将原子数据在redis中进行增删改
 * 3、提取维度变化消息写入另外一个queue中，供数据聚合服务消费
 */
@Slf4j
@Component
@RabbitListener(queues = "data-change-queue")
public class DataChangeQueueReceiver {

    @Autowired
    private EshopProductService eshopProductService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    private Set<String> dimMessageDataSet = Collections.synchronizedSet(new HashSet<String>());

    private List<String> brandDataChangeMessageList = new ArrayList<>();

    public DataChangeQueueReceiver(){
        new SendThread().start();
    }

    @RabbitHandler
    public void process(String message){
        log.info("DataChangeQueueReceiver received message:{}",message);
        JSONObject jsonObject = JSONObject.parseObject(message);
        String dataType = jsonObject.getString("data_type");
        switch (dataType) {
            case "brand":
                processBrandChangeMessage(jsonObject);
                break;
            case "category":
                processCategoryDataChangeMessage(jsonObject);
                break;
            case "product_intro":
                processProductIntroDataChangeMessage(jsonObject);
                break;
            case "product_data":
                processProductDataChangeMessage(jsonObject);
                break;
            case "product_property":
                processProductPropertyDataChangeMessage(jsonObject);
                break;
            case "product_specification":
                processProductSpecificationDataChangeMessage(jsonObject);
                break;
        }
    }

    private void processBrandChangeMessage(JSONObject jsonObject){
        Long id = jsonObject.getLong("id");
        String eventType = jsonObject.getString("event_type");
        if(StringUtils.equals("add",eventType) || StringUtils.equals("update",eventType)){
            // 手动批量查询，优化效率
            brandDataChangeMessageList.add(String.valueOf(id));
            if(brandDataChangeMessageList.size() >= 3){
                String brandListJson = eshopProductService.findBrandByIds(brandDataChangeMessageList);
                JSONArray brandJsonArray = JSONArray.parseArray(brandListJson);
                log.info("Process add or update brand message, Obtain batch brand change datas:{}",brandJsonArray);
                brandJsonArray.forEach(brandJsonObject -> {
                    redisTemplate.opsForValue().set("brand:"+id,brandJsonObject);
                });
                log.info("Set or update brand message to redis success");
                brandDataChangeMessageList.clear();
            }
        } else if(StringUtils.equals("delete",eventType)){
            redisTemplate.delete("brand:"+id);
            log.info("Delete brand message to redis success");
        }
        rabbitMQSender.send("aggr-data-change-queue","{\"change_type\":\"brand\",\"id\":\""+id+"\"}");
        log.info("Brand data changed, send to aggration success");
    }

    private void processCategoryDataChangeMessage(JSONObject jsonObject) {
        Long id = jsonObject.getLong("id");
        String eventType = jsonObject.getString("event_type");

        if("add".equals(eventType) || "update".equals(eventType)) {
            String categoryJson = eshopProductService.findCategoryById(id);
            JSONObject parseObject = JSONObject.parseObject(categoryJson);
            log.info("Process add or update category message, Obtain brand change data:{}",categoryJson);
            redisTemplate.opsForValue().set("category:"+id,categoryJson);
            log.info("Set or update category message to redis success");
        } else if ("delete".equals(eventType)) {
            redisTemplate.delete("category:"+id);
            log.info("Delete category message to redis success");
        }

        rabbitMQSender.send("aggr-data-change-queue","{\"change_type\":\"category\",\"id\":\""+id+"\"}");
        log.info("Category data changed, send to aggration success");
    }

    private void processProductIntroDataChangeMessage(JSONObject jsonObject) {
        Long id = jsonObject.getLong("id");
        Long productId = jsonObject.getLong("product_id");
        String eventType = jsonObject.getString("event_type");

        if("add".equals(eventType) || "update".equals(eventType)) {
            String productIntroJson = eshopProductService.findProductIntroById(id);
            JSONObject parseObject = JSONObject.parseObject(productIntroJson);
            log.info("Process add or update productIntro message, Obtain brand change data:{}",productIntroJson);
            redisTemplate.opsForValue().set("product:intro:"+productId,productIntroJson);
            log.info("Set or update productIntro message to redis success");
        } else if ("delete".equals(eventType)) {
            redisTemplate.delete("product:intro:"+productId);
            log.info("Delete productIntro message to redis success");
        }

        rabbitMQSender.send("aggr-data-change-queue","{\"change_type\":\"product_intro\",\"id\":\""+productId+"\"}");
        log.info("ProductIntro data changed, send to aggration success");
    }

    private void processProductDataChangeMessage(JSONObject jsonObject) {
        Long id = jsonObject.getLong("id");
        Long productId = jsonObject.getLong("product_id");
        String eventType = jsonObject.getString("event_type");

        if("add".equals(eventType) || "update".equals(eventType)) {
            String productDataJson = eshopProductService.findProductById(id);
            JSONObject parseObject = JSONObject.parseObject(productDataJson);
            log.info("Process add or update productData message, Obtain brand change data:{}",productDataJson);
            redisTemplate.opsForValue().set("product:"+productId,productDataJson);
            log.info("Set or update productData message to redis success");
        } else if ("delete".equals(eventType)) {
            redisTemplate.delete("product:"+productId);
            log.info("Delete productData message to redis success");
        }
        dimMessageDataSet.add("{\"change_type\":\"product\",\"id\":\""+productId+"\"}");
        log.info("ProductData data changed, add to dimMessageDataSet to wait be sended");
    }

    private void processProductPropertyDataChangeMessage(JSONObject jsonObject) {
        Long id = jsonObject.getLong("id");
        Long productId = jsonObject.getLong("product_id");
        String eventType = jsonObject.getString("event_type");

        if("add".equals(eventType) || "update".equals(eventType)) {
            String productPropertyDataJson = eshopProductService.findProductPropertyById(id);
            JSONObject parseObject = JSONObject.parseObject(productPropertyDataJson);
            log.info("Process add or update productProperty message, Obtain brand change data:{}",productPropertyDataJson);
            redisTemplate.opsForValue().set("product:property:"+productId,productPropertyDataJson);
            log.info("Set or update productProperty message to redis success");
        } else if ("delete".equals(eventType)) {
            redisTemplate.delete("product:property:"+productId);
            log.info("Delete productProperty message to redis success");
        }

        dimMessageDataSet.add("{\"change_type\":\"product\",\"id\":\""+productId+"\"}");
        log.info("ProductData data changed, add to dimMessageDataSet to wait be sended");
    }

    private void processProductSpecificationDataChangeMessage(JSONObject jsonObject) {
        Long id = jsonObject.getLong("id");
        Long productId = jsonObject.getLong("product_id");
        String eventType = jsonObject.getString("event_type");

        if("add".equals(eventType) || "update".equals(eventType)) {
            String productSpecificationJson = eshopProductService.findProductSpecificationById(id);
            JSONObject parseObject = JSONObject.parseObject(productSpecificationJson);
            log.info("Process add or update productSpecification message, Obtain brand change data:{}",productSpecificationJson);
            redisTemplate.opsForValue().set("product:specification:"+productId,productSpecificationJson);
            log.info("Set or update productSpecification message to redis success");
        } else if ("delete".equals(eventType)) {
            redisTemplate.delete("product:specification:"+productId);
            log.info("Delete productSpecification message to redis success");
        }
        dimMessageDataSet.add("{\"change_type\":\"product\",\"id\":\""+productId+"\"}");
        log.info("ProductData data changed, add to dimMessageDataSet to wait be sended");
    }

    private class SendThread extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!dimMessageDataSet.isEmpty()){
                    for(String message : dimMessageDataSet){
                        rabbitMQSender.send("aggr-data-change-queue",message);
                        log.info("ProductData data changed, send message to aggr service:{}",message);
                    }
                    dimMessageDataSet.clear();
                }
            }
        }
    }

    private class BrandBranchFindThread extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!dimMessageDataSet.isEmpty()){
                    for(String message : dimMessageDataSet){
                        rabbitMQSender.send("aggr-data-change-queue",message);
                        log.info("ProductData data changed, send message to aggr service:{}",message);
                    }
                    dimMessageDataSet.clear();
                }
            }
        }
    }

}
