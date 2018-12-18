package com.corner.eshop.product.service.impl;

import com.corner.eshop.product.entity.Brand;
import com.corner.eshop.product.entity.Category;
import com.corner.eshop.product.mapper.BrandMapper;
import com.corner.eshop.product.mapper.CategoryMapper;
import com.corner.eshop.product.rabbitmq.RabbitMQSender;
import com.corner.eshop.product.rabbitmq.RabbitQueue;
import com.corner.eshop.product.service.BrandService;
import com.corner.eshop.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Transactional
    public void add(Brand brand,String operationType) {
        brandMapper.insert(brand);
        String queue = null;
        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"add\",\"data_type\":\"brand\",\"id\":\""+brand.getId()+"\"}");
    }

    @Transactional
    public void delete(Integer id,String operationType) {
        brandMapper.deleteByPrimaryKey(id);
        String queue = null;
        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"delete\",\"data_type\":\"brand\",\"id\":\""+id+"\"}");
    }

    @Transactional
    public void update(Brand brand,String operationType) {
        brandMapper.updateByPrimaryKey(brand);
        String queue = null;
        if(operationType == null || "".equals(operationType)) {
            queue = RabbitQueue.DATA_CHANGE_QUEUE;
        } else if("refresh".equals(operationType)) {
            queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
        } else if("high".equals(operationType)) {
            queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
        }
        rabbitMQSender.send(queue,"{\"event_type\":\"update\",\"data_type\":\"brand\",\"id\":\""+brand.getId()+"\"}");
    }

    public Brand selectById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Brand> selectByIds(List<String> ids) {
        String idsStr = String.join(",",ids);
        return brandMapper.selectByIds(idsStr);
    }
}
