package com.corner.eshop.product.service.impl;

import com.corner.eshop.product.entity.Category;
import com.corner.eshop.product.mapper.CategoryMapper;
import com.corner.eshop.product.rabbitmq.RabbitMQSender;
import com.corner.eshop.product.rabbitmq.RabbitQueue;
import com.corner.eshop.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Transactional
    public void add(Category category,String operationType) {
        categoryMapper.insert(category);
        String queue = null;

        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"add\",\"data_type\":\"category\",\"id\":\""+category.getId()+"\"}");

    }

    @Transactional
    public void delete(Integer id,String operationType) {
        categoryMapper.deleteByPrimaryKey(id);
        String queue = null;

        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"delete\",\"data_type\":\"category\",\"id\":\""+id+"\"}");

    }

    @Transactional
    public void update(Category category,String operationType) {
        categoryMapper.updateByPrimaryKey(category);
        String queue = null;

        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"update\",\"data_type\":\"category\",\"id\":\""+category.getId()+"\"}");

    }

    public Category selectById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }
}
