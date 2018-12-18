package com.corner.eshop.product.service.impl;

import com.corner.eshop.product.entity.Product;
import com.corner.eshop.product.mapper.ProductMapper;
import com.corner.eshop.product.rabbitmq.RabbitMQSender;
import com.corner.eshop.product.rabbitmq.RabbitQueue;
import com.corner.eshop.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Transactional
    public void add(Product product,String operationType) {
        productMapper.insert(product);
        String queue = null;

        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"add\",\"data_type\":\"product_data\",\"id\":\""+product.getId()+"\",\"product_id\":\""+product.getId()+"\"}");
    }

    @Transactional
    public void delete(Integer id,String operationType) {
        Product product = productMapper.selectByPrimaryKey(id);
        productMapper.deleteByPrimaryKey(id);
        String queue = null;

        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"delete\",\"data_type\":\"product_data\",\"id\":\""+id+"\",\"product_id\":\""+product.getId()+"\"}");

    }

    @Transactional
    public void update(Product product,String operationType) {
        productMapper.updateByPrimaryKey(product);
        String queue = null;

        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"update\",\"data_type\":\"product_data\",\"id\":\""+product.getId()+"\",\"product_id\":\""+product.getId()+"\"}");

    }

    public Product selectById(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }
}
