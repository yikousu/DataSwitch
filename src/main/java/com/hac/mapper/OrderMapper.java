package com.hac.mapper;

import com.hac.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单Mapper接口
 * 默认使用订单数据源
 * 
 * @author HAC
 * @since 2025-07-14
 */
@Mapper
public interface OrderMapper {
    
    /**
     * 根据订单ID查询订单
     * 
     * @param orderId 订单ID
     * @return 订单信息
     */
    Order selectById(@Param("orderId") String orderId);
    
    /**
     * 根据用户ID查询订单列表
     * 
     * @param userId 用户ID
     * @return 订单列表
     */
    List<Order> selectByUserId(@Param("userId") String userId);
    
    /**
     * 查询所有订单
     * 
     * @return 订单列表
     */
    List<Order> selectAll();
    
    /**
     * 插入订单
     * 
     * @param order 订单信息
     * @return 影响行数
     */
    int insertOrder(Order order);
    
    /**
     * 更新订单
     * 
     * @param order 订单信息
     * @return 影响行数
     */
    int updateOrder(Order order);
    
    /**
     * 删除订单
     * 
     * @param orderId 订单ID
     * @return 影响行数
     */
    int deleteById(@Param("orderId") String orderId);
} 