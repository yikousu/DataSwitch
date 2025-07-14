package com.hac.aspect;

import com.hac.annotation.TargetDataSource;
import com.hac.config.DataSourceContextHolder;
import com.hac.enums.DataSourceType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 数据源切换切面
 * 通过AOP自动根据注解切换数据源
 * 
 * @author HAC
 * @since 2025-07-14
 */
@Aspect
@Order(1) // 确保在事务注解之前执行
@Component
public class DataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    /**
     * 环绕通知，拦截所有Mapper方法
     * 
     * @param point 切入点
     * @return 方法执行结果
     * @throws Throwable 异常
     */
    @Around("execution(* com.hac.mapper..*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = point.getTarget().getClass();

        logger.debug("拦截方法: {}.{}", targetClass.getSimpleName(), method.getName());

        // 获取方法上的数据源注解
        TargetDataSource methodDataSource = 
            AnnotationUtils.findAnnotation(method, TargetDataSource.class);
        
        // 获取类上的数据源注解
        TargetDataSource classDataSource = 
            AnnotationUtils.findAnnotation(targetClass, TargetDataSource.class);

        // 优先使用方法级别的注解，其次是类级别的注解
        TargetDataSource dataSource = methodDataSource != null ? methodDataSource : classDataSource;

        // 默认使用订单数据源
        String dataSourceKey = DataSourceType.ORDER.getValue();

        if (dataSource != null) {
            dataSourceKey = dataSource.value().getValue();
        }

        logger.info("切换数据源到: {} (方法: {}.{})", 
                   dataSourceKey, targetClass.getSimpleName(), method.getName());

        // 设置数据源
        DataSourceContextHolder.setDataSourceType(dataSourceKey);

        try {
            // 执行目标方法
            return point.proceed();
        } finally {
            // 清除数据源设置，避免内存泄漏和线程污染
            DataSourceContextHolder.clearDataSourceType();
            logger.debug("已清除数据源上下文");
        }
    }

    /**
     * 也可以通过注解直接拦截带有@TargetDataSource注解的方法
     * 
     * @param point 切入点
     * @param targetDataSource 数据源注解
     * @return 方法执行结果
     * @throws Throwable 异常
     */
    @Around("@annotation(targetDataSource)")
    public Object aroundWithAnnotation(ProceedingJoinPoint point, 
                                     TargetDataSource targetDataSource) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = point.getTarget().getClass();

        String dataSourceKey = targetDataSource.value().getValue();
        
        logger.info("通过注解切换数据源到: {} (方法: {}.{})", 
                   dataSourceKey, targetClass.getSimpleName(), method.getName());

        // 设置数据源
        DataSourceContextHolder.setDataSourceType(dataSourceKey);

        try {
            return point.proceed();
        } finally {
            DataSourceContextHolder.clearDataSourceType();
            logger.debug("已清除数据源上下文");
        }
    }
} 