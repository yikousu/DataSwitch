package com.hac.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源
 * 继承AbstractRoutingDataSource，根据上下文动态选择数据源
 * 
 * @author HAC
 * @since 2025-07-14
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

    /**
     * 构造函数
     * 
     * @param defaultTargetDataSource 默认数据源
     * @param targetDataSources 目标数据源集合
     */
    public DynamicDataSource(DataSource defaultTargetDataSource, 
                           Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    /**
     * 确定当前要使用的数据源
     * Spring在每次数据库操作前都会调用该方法来确定使用哪个数据源
     * 
     * @return 数据源标识
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceType = DataSourceContextHolder.getDataSourceType();
        logger.debug("当前使用数据源: {}", dataSourceType);
        return dataSourceType;
    }
    
    /**
     * 重写该方法，增加错误处理
     */
    @Override
    protected DataSource determineTargetDataSource() {
        try {
            return super.determineTargetDataSource();
        } catch (Exception e) {
            logger.error("确定目标数据源时发生错误: {}", e.getMessage());
            // 发生错误时返回默认数据源
            return super.getResolvedDefaultDataSource();
        }
    }
}