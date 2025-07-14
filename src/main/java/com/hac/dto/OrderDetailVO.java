package com.hac.dto;

import com.hac.entity.Order;
import com.hac.entity.User;

/**
 * 订单详情视图对象
 * 组合订单信息和用户信息
 * 
 * @author HAC
 * @since 2025-07-14
 */
public class OrderDetailVO {
    
    /**
     * 订单信息
     */
    private Order order;
    
    /**
     * 用户信息
     */
    private User user;

    public OrderDetailVO() {}

    public OrderDetailVO(Order order, User user) {
        this.order = order;
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "OrderDetailVO{" +
                "order=" + order +
                ", user=" + user +
                '}';
    }
} 