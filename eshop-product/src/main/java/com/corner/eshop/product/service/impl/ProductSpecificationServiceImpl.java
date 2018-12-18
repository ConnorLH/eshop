package com.corner.eshop.product.service.impl;

import com.corner.eshop.product.entity.ProductSpecification;
import com.corner.eshop.product.mapper.ProductSpecificationMapper;
import com.corner.eshop.product.rabbitmq.RabbitMQSender;
import com.corner.eshop.product.rabbitmq.RabbitQueue;
import com.corner.eshop.product.service.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductSpecificationServiceImpl implements ProductSpecificationService {

    @Autowired
    private ProductSpecificationMapper productSpecificationMapper;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Transactional
    public void add(ProductSpecification productSpecification,String operationType) {
        productSpecificationMapper.insert(productSpecification);
        String queue = null;

        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"add\",\"data_type\":\"product_specification\",\"id\":\""+productSpecification.getId()+"\",\"product_id\":\""+productSpecification.getProductId()+"\"}");

    }

    @Transactional
    public void delete(Integer id,String operationType) {
        ProductSpecification productSpecification = productSpecificationMapper.selectByPrimaryKey(id);
        productSpecificationMapper.deleteByPrimaryKey(id);
        String queue = null;

        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"delete\",\"data_type\":\"product_specification\",\"id\":\""+id+"\",\"product_id\":\""+productSpecification.getProductId()+"\"}");

    }

    @Transactional
    public void update(ProductSpecification productSpecification,String operationType) {
        productSpecificationMapper.updateByPrimaryKey(productSpecification);
        String queue = null;

        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"update\",\"data_type\":\"product_specification\",\"id\":\""+productSpecification.getId()+"\",\"product_id\":\""+productSpecification.getProductId()+"\"}");

    }

    public ProductSpecification selectById(Integer id) {
        return productSpecificationMapper.selectByPrimaryKey(id);
    }

    public ProductSpecification selectByProductId(Integer productId) {
        return productSpecificationMapper.selectByProductId(productId);
    }
}
