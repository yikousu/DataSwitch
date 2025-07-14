package com.hac.service;

import com.hac.entity.User;
import com.hac.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 用户服务类
 * 
 * @author HAC
 * @since 2025-07-14
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户ID获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    public User getUserById(String userId) {
        logger.info("根据ID查询用户，用户ID: {}", userId);
        return userMapper.selectById(userId);
    }

    /**
     * 根据用户名获取用户信息
     * 
     * @param username 用户名
     * @return 用户信息
     */
    public User getUserByUsername(String username) {
        logger.info("根据用户名查询用户，用户名: {}", username);
        return userMapper.selectByUsername(username);
    }

    /**
     * 获取所有用户
     * 
     * @return 用户列表
     */
    public List<User> getAllUsers() {
        logger.info("查询所有用户");
        return userMapper.selectAll();
    }

    /**
     * 创建用户
     * 
     * @param username 用户名
     * @param password 密码
     * @param realName 真实姓名
     * @param email 邮箱
     * @param mobile 手机号
     * @return 用户信息
     */
    public User createUser(String username, String password, String realName, String email, String mobile) {
        logger.info("创建用户，用户名: {}", username);

        // 检查用户名是否已存在
        User existingUser = userMapper.selectByUsername(username);
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在：" + username);
        }

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPassword(password); // 实际项目中应该加密
        user.setRealName(realName);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setStatus(1); // 默认启用
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        int result = userMapper.insertUser(user);
        if (result > 0) {
            logger.info("用户创建成功: {}", user);
            return user;
        } else {
            throw new RuntimeException("用户创建失败");
        }
    }

    /**
     * 更新用户信息
     * 
     * @param user 用户信息
     * @return 是否成功
     */
    public boolean updateUser(User user) {
        logger.info("更新用户信息，用户ID: {}", user.getUserId());

        user.setUpdateTime(LocalDateTime.now());
        int result = userMapper.updateUser(user);
        boolean success = result > 0;
        logger.info("用户信息更新{}", success ? "成功" : "失败");
        return success;
    }

    /**
     * 删除用户
     * 
     * @param userId 用户ID
     * @return 是否成功
     */
    public boolean deleteUser(String userId) {
        logger.info("删除用户，用户ID: {}", userId);
        int result = userMapper.deleteById(userId);
        boolean success = result > 0;
        logger.info("用户删除{}", success ? "成功" : "失败");
        return success;
    }

    /**
     * 启用/禁用用户
     * 
     * @param userId 用户ID
     * @param status 状态：0-禁用，1-启用
     * @return 是否成功
     */
    public boolean updateUserStatus(String userId, Integer status) {
        logger.info("更新用户状态，用户ID: {}，新状态: {}", userId, status);

        User user = userMapper.selectById(userId);
        if (user == null) {
            logger.warn("用户不存在，更新失败");
            return false;
        }

        user.setStatus(status);
        user.setUpdateTime(LocalDateTime.now());

        int result = userMapper.updateUser(user);
        boolean success = result > 0;
        logger.info("用户状态更新{}", success ? "成功" : "失败");
        return success;
    }
} 