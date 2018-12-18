package com.corner.eshop.product.service.impl;

import com.corner.eshop.product.entity.ProductIntro;
import com.corner.eshop.product.entity.ProductProperty;
import com.corner.eshop.product.mapper.ProductIntroMapper;
import com.corner.eshop.product.mapper.ProductPropertyMapper;
import com.corner.eshop.product.rabbitmq.RabbitMQSender;
import com.corner.eshop.product.rabbitmq.RabbitQueue;
import com.corner.eshop.product.service.ProductIntroService;
import com.corner.eshop.product.service.ProductPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductIntroServiceImpl implements ProductIntroService {

    @Autowired
    private ProductIntroMapper productIntroMapper;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Transactional
    public void add(ProductIntro productIntro,String operationType) {
        productIntroMapper.insert(productIntro);
        String queue = null;

        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"add\",\"data_type\":\"product_intro\",\"id\":\""+productIntro.getId()+"\",\"product_id\":\""+productIntro.getProductId()+"\"}");

    }

    @Transactional
    public void delete(Integer id,String operationType) {
        ProductIntro productIntro = productIntroMapper.selectByPrimaryKey(id);
        productIntroMapper.deleteByPrimaryKey(id);
        String queue = null;

        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"delete\",\"data_type\":\"product_intro\",\"id\":\""+id+"\",\"product_id\":\""+productIntro.getProductId()+"\"}");

    }

    @Transactional
    public void update(ProductIntro productIntro,String operationType) {
        productIntroMapper.updateByPrimaryKey(productIntro);
        String queue = null;

        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"update\",\"data_type\":\"product_intro\",\"id\":\""+productIntro.getId()+"\",\"product_id\":\""+productIntro.getProductId()+"\"}");

    }

    public ProductIntro selectById(Integer id) {
        return productIntroMapper.selectByPrimaryKey(id);
    }
}
