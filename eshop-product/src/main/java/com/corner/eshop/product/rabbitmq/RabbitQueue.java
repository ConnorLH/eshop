package com.corner.eshop.product.rabbitmq;

/**
 * rabbitmq相关常量
 */
public interface RabbitQueue {
    /**
     * 数据变更队列
     */
    public static final String DATA_CHANGE_QUEUE = "data-change-queue";

    /**
     * 刷数据操作队列
     */
    public static final String REFRESH_DATA_CHANGE_QUEUE = "refresh-data-change-queue";

    /**
     * 高优先级队列
     */
    public static final String HIGH_PRIORITY_DATA_CHANGE_QUEUE = "high-priority-data-change-queue";
}
