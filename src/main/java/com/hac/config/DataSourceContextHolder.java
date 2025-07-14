package com.hac.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据源上下文持有者
 * 使用ThreadLocal保存当前线程的数据源标识
 * 
 * @author HAC
 * @since 2025-07-14
 */
public class DataSourceContextHolder {
    
    /**
     * 使用ThreadLocal保存数据源标识，确保线程安全
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    
    /**
     * 设置当前线程的数据源类型
     * 
     * @param dataSourceType 数据源类型标识
     */
    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }
    
    /**
     * 获取当前线程的数据源类型
     * 
     * @return 数据源类型标识
     */
    public static String getDataSourceType() {
        return contextHolder.get();
    }
    
    /**
     * 清除当前线程的数据源类型
     * 这个方法很重要，必须在数据库操作完成后调用，避免内存泄漏
     */
    public static void clearDataSourceType() {
        contextHolder.remove();
    }
} 