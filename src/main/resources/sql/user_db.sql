-- 用户数据库初始化脚本
-- 创建数据库
CREATE DATABASE IF NOT EXISTS user_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE user_db;

-- 创建用户表
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
    user_id VARCHAR(50) NOT NULL COMMENT '用户ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    email VARCHAR(100) COMMENT '邮箱',
    mobile VARCHAR(20) COMMENT '手机号',
    status INT NOT NULL DEFAULT 1 COMMENT '用户状态：0-禁用，1-启用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (user_id),
    UNIQUE KEY uk_username (username),
    KEY idx_email (email),
    KEY idx_mobile (mobile),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 插入测试数据
INSERT INTO t_user (user_id, username, password, real_name, email, mobile, status, create_time, update_time) VALUES
('user-001', 'zhangsan', '123456', '张三', 'zhangsan@example.com', '13800138001', 1, '2025-07-14 09:00:00', '2025-07-14 09:00:00'),
('user-002', 'lisi', '123456', '李四', 'lisi@example.com', '13800138002', 1, '2025-07-14 09:30:00', '2025-07-14 09:30:00'),
('user-003', 'wangwu', '123456', '王五', 'wangwu@example.com', '13800138003', 1, '2025-07-14 10:00:00', '2025-07-14 10:00:00'),
('user-004', 'zhaoliu', '123456', '赵六', 'zhaoliu@example.com', '13800138004', 0, '2025-07-14 10:30:00', '2025-07-14 10:30:00'),
('user-005', 'qianqi', '123456', '钱七', 'qianqi@example.com', '13800138005', 1, '2025-07-14 11:00:00', '2025-07-14 11:00:00');

-- 查询验证
SELECT * FROM t_user; 