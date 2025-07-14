package com.hac.service;

import com.hac.dto.OrderDetailVO;
import com.hac.entity.Order;
import com.hac.entity.User;
import com.hac.mapper.OrderMapper;
import com.hac.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 订单服务类
 * 演示双数据源的使用
 * 
 * @author HAC
 * @since 2025-07-14
 */
@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取订单详情
     * 演示跨数据源查询：先查订单（订单库），再查用户（用户库）
     * 
     * @param orderId 订单ID
     * @return 订单详情
     */
    public OrderDetailVO getOrderDetail(String orderId) {
        logger.info("获取订单详情，订单ID: {}", orderId);

        // 自动使用订单数据源
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            logger.warn("订单不存在，订单ID: {}", orderId);
            return null;
        }

        // 自动切换到用户数据源
        User user = userMapper.selectById(order.getUserId());
        if (user == null) {
            logger.warn("用户不存在，用户ID: {}", order.getUserId());
        }

        OrderDetailVO result = new OrderDetailVO(order, user);
        logger.info("订单详情获取成功: {}", result);
        return result;
    }

    /**
     * 根据用户ID查询订单列表
     * 
     * @param userId 用户ID
     * @return 订单列表
     */
    public List<Order> getOrdersByUserId(String userId) {
        logger.info("查询用户订单列表，用户ID: {}", userId);
        return orderMapper.selectByUserId(userId);
    }

    /**
     * 查询所有订单
     * 
     * @return 订单列表
     */
    public List<Order> getAllOrders() {
        logger.info("查询所有订单");
        return orderMapper.selectAll();
    }

    /**
     * 创建订单
     * 
     * @param userId 用户ID
     * @param totalAmount 总金额
     * @param remark 备注
     * @return 订单信息
     */
    public Order createOrder(String userId, BigDecimal totalAmount, String remark) {
        logger.info("创建订单，用户ID: {}，金额: {}", userId, totalAmount);

        // 先验证用户是否存在（用户数据源）
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在，无法创建订单");
        }

        // 创建订单（订单数据源）
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setUserId(userId);
        order.setOrderNo("ORDER-" + System.currentTimeMillis());
        order.setTotalAmount(totalAmount);
        order.setStatus(0); // 待支付
        order.setRemark(remark);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());

        int result = orderMapper.insertOrder(order);
        if (result > 0) {
            logger.info("订单创建成功: {}", order);
            return order;
        } else {
            throw new RuntimeException("订单创建失败");
        }
    }

    /**
     * 更新订单状态
     * 
     * @param orderId 订单ID
     * @param status 新状态
     * @return 是否成功
     */
    public boolean updateOrderStatus(String orderId, Integer status) {
        logger.info("更新订单状态，订单ID: {}，新状态: {}", orderId, status);

        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            logger.warn("订单不存在，更新失败");
            return false;
        }

        order.setStatus(status);
        order.setUpdateTime(LocalDateTime.now());

        int result = orderMapper.updateOrder(order);
        boolean success = result > 0;
        logger.info("订单状态更新{}", success ? "成功" : "失败");
        return success;
    }

    /**
     * 删除订单
     * 
     * @param orderId 订单ID
     * @return 是否成功
     */
    public boolean deleteOrder(String orderId) {
        logger.info("删除订单，订单ID: {}", orderId);
        int result = orderMapper.deleteById(orderId);
        boolean success = result > 0;
        logger.info("订单删除{}", success ? "成功" : "失败");
        return success;
    }
} 