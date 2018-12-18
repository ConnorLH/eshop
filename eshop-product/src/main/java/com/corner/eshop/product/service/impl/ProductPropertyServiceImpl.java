package com.corner.eshop.product.service.impl;

import com.corner.eshop.product.entity.ProductProperty;
import com.corner.eshop.product.mapper.ProductPropertyMapper;
import com.corner.eshop.product.rabbitmq.RabbitMQSender;
import com.corner.eshop.product.rabbitmq.RabbitQueue;
import com.corner.eshop.product.service.ProductPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductPropertyServiceImpl implements ProductPropertyService {

    @Autowired
    private ProductPropertyMapper productPropertyMapper;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Transactional
    public void add(ProductProperty productProperty,String operationType) {
        productPropertyMapper.insert(productProperty);
        String queue = null;

        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"add\",\"data_type\":\"product_property\",\"id\":\""+productProperty.getId()+"\",\"product_id\":\""+productProperty.getProductId()+"\"}");

    }

    @Transactional
    public void delete(Integer id,String operationType) {
        ProductProperty productProperty = productPropertyMapper.selectByPrimaryKey(id);
        productPropertyMapper.deleteByPrimaryKey(id);
        String queue = null;

        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"delete\",\"data_type\":\"product_property\",\"id\":\""+id+"\",\"product_id\":\""+productProperty.getProductId()+"\"}");

    }

    @Transactional
    public void update(ProductProperty productProperty,String operationType) {
        productPropertyMapper.updateByPrimaryKey(productProperty);
        String queue = null;

        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"update\",\"data_type\":\"product_property\",\"id\":\""+productProperty.getId()+"\",\"product_id\":\""+productProperty.getProductId()+"\"}");

    }

    public ProductProperty selectById(Integer id) {
        return productPropertyMapper.selectByPrimaryKey(id);
    }

    public List<ProductProperty> selectByProductId(Integer productId) {
        return productPropertyMapper.selectByProductId(productId);
    }
}
