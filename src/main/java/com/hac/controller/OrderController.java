package com.hac.controller;

import com.hac.dto.OrderDetailVO;
import com.hac.entity.Order;
import com.hac.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单控制器
 * 提供订单相关的REST API
 * 
 * @author HAC
 * @since 2025-07-14
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 获取订单详情
     * 
     * @param orderId 订单ID
     * @return 订单详情
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailVO> getOrderDetail(@PathVariable String orderId) {
        OrderDetailVO orderDetail = orderService.getOrderDetail(orderId);
        if (orderDetail != null) {
            return ResponseEntity.ok(orderDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根据用户ID查询订单列表
     * 
     * @param userId 用户ID
     * @return 订单列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable String userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    /**
     * 查询所有订单
     * 
     * @return 订单列表
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * 创建订单
     * 
     * @param createOrderRequest 创建订单请求
     * @return 创建的订单
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        try {
            Order order = orderService.createOrder(
                createOrderRequest.getUserId(),
                createOrderRequest.getTotalAmount(),
                createOrderRequest.getRemark()
            );
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 更新订单状态
     * 
     * @param orderId 订单ID
     * @param status 新状态
     * @return 操作结果
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable String orderId, 
                                                   @RequestParam Integer status) {
        boolean success = orderService.updateOrderStatus(orderId, status);
        if (success) {
            return ResponseEntity.ok("订单状态更新成功");
        } else {
            return ResponseEntity.badRequest().body("订单状态更新失败");
        }
    }

    /**
     * 删除订单
     * 
     * @param orderId 订单ID
     * @return 操作结果
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderId) {
        boolean success = orderService.deleteOrder(orderId);
        if (success) {
            return ResponseEntity.ok("订单删除成功");
        } else {
            return ResponseEntity.badRequest().body("订单删除失败");
        }
    }

    /**
     * 创建订单请求DTO
     */
    public static class CreateOrderRequest {
        private String userId;
        private BigDecimal totalAmount;
        private String remark;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
} 