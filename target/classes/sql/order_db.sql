-- 订单数据库初始化脚本
-- 创建数据库
CREATE DATABASE IF NOT EXISTS order_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE order_db;

-- 创建订单表
DROP TABLE IF EXISTS t_order;
CREATE TABLE t_order (
    order_id VARCHAR(50) NOT NULL COMMENT '订单ID',
    user_id VARCHAR(50) NOT NULL COMMENT '用户ID',
    order_no VARCHAR(100) NOT NULL COMMENT '订单编号',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
    status INT NOT NULL DEFAULT 0 COMMENT '订单状态：0-待支付，1-已支付，2-已发货，3-已完成，4-已取消',
    remark VARCHAR(500) COMMENT '订单备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (order_id),
    KEY idx_user_id (user_id),
    KEY idx_order_no (order_no),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- 插入测试数据
INSERT INTO t_order (order_id, user_id, order_no, total_amount, status, remark, create_time, update_time) VALUES
('order-001', 'user-001', 'ORDER-20240101001', 199.99, 1, '测试订单1', '2025-07-14 10:00:00', '2025-07-14 10:00:00'),
('order-002', 'user-002', 'ORDER-20240101002', 299.99, 0, '测试订单2', '2025-07-14 11:00:00', '2025-07-14 11:00:00'),
('order-003', 'user-001', 'ORDER-20240101003', 99.99, 2, '测试订单3', '2025-07-14 12:00:00', '2025-07-14 12:00:00'),
('order-004', 'user-003', 'ORDER-20240101004', 599.99, 3, '测试订单4', '2025-07-14 13:00:00', '2025-07-14 13:00:00'),
('order-005', 'user-002', 'ORDER-20240101005', 149.99, 1, '测试订单5', '2025-07-14 14:00:00', '2025-07-14 14:00:00');

-- 查询验证
SELECT * FROM t_order; 