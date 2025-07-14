package com.hac.enums;

/**
 * 数据源类型枚举
 * 
 * @author HAC
 * @since 2025-07-14
 */
public enum DataSourceType {
    /**
     * 订单数据源
     */
    ORDER("order"),
    
    /**
     * 用户数据源  
     */
    USER("user");
    
    private final String value;
    
    DataSourceType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return this.value;
    }
} 