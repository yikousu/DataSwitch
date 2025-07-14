package com.hac.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置类
 * 配置双数据源和MyBatis相关组件
 * 
 * @author HAC
 * @since 2025-07-14
 */
@Configuration
@MapperScan(basePackages = "com.hac.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    /**
     * 订单数据源配置
     * 
     * @return 订单数据源
     */
    @Bean(name = "orderDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.order")
    public DataSource orderDataSource() {
        logger.info("初始化订单数据源");
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 用户数据源配置
     * 
     * @return 用户数据源
     */
    @Bean(name = "userDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.user")
    public DataSource userDataSource() {
        logger.info("初始化用户数据源");
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 动态数据源配置
     * 
     * @param orderDataSource 订单数据源
     * @param userDataSource 用户数据源
     * @return 动态数据源
     */
    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("orderDataSource") DataSource orderDataSource,
                                       @Qualifier("userDataSource") DataSource userDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("order", orderDataSource);
        targetDataSources.put("user", userDataSource);

        logger.info("初始化动态数据源，默认数据源: order，目标数据源: {}", targetDataSources.keySet());
        
        // 默认使用订单数据源
        return new DynamicDataSource(orderDataSource, targetDataSources);
    }

    /**
     * SqlSessionFactory配置
     * 
     * @param dataSource 动态数据源
     * @return SqlSessionFactory
     * @throws Exception 异常
     */
    @Primary
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) 
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        
        // 设置MyBatis mapper文件位置
        bean.setMapperLocations(
            new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/*.xml"));
        
        // 设置实体类包路径
        bean.setTypeAliasesPackage("com.hac.entity");
        
        logger.info("初始化SqlSessionFactory完成");
        return bean.getObject();
    }

    /**
     * 事务管理器配置
     * 
     * @param dataSource 动态数据源
     * @return 事务管理器
     */
    @Primary
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(
            @Qualifier("dynamicDataSource") DataSource dataSource) {
        logger.info("初始化事务管理器");
        return new DataSourceTransactionManager(dataSource);
    }
} 