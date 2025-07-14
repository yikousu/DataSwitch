package com.hac.annotation;

import com.hac.enums.DataSourceType;

import java.lang.annotation.*;

/**
 * 数据源切换注解
 * 用于标识方法或类使用的数据源类型
 * 
 * @author HAC
 * @since 2025-07-14
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    
    /**
     * 指定数据源类型
     * 默认使用订单数据源
     * @return 数据源类型
     */
    DataSourceType value() default DataSourceType.ORDER;
} 