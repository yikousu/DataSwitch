package com.hac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Spring Boot 双数据源应用主启动类
 * 
 * @author HAC
 * @since 2025-07-14
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DualDataSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DualDataSourceApplication.class, args);
        System.out.println("  Spring Boot 双数据源应用启动成功！");
    }
} 