package com.hac.mapper;

import com.hac.annotation.TargetDataSource;
import com.hac.entity.User;
import com.hac.enums.DataSourceType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 * 使用用户数据源
 * 
 * @author HAC
 * @since 2025-07-14
 */
@Mapper
@TargetDataSource(DataSourceType.USER)
public interface UserMapper {
    
    /**
     * 根据用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    User selectById(@Param("userId") String userId);
    
    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户信息
     */
    User selectByUsername(@Param("username") String username);
    
    /**
     * 查询所有用户
     * 
     * @return 用户列表
     */
    List<User> selectAll();
    
    /**
     * 插入用户
     * 
     * @param user 用户信息
     * @return 影响行数
     */
    int insertUser(User user);
    
    /**
     * 更新用户
     * 
     * @param user 用户信息
     * @return 影响行数
     */
    int updateUser(User user);
    
    /**
     * 删除用户
     * 
     * @param userId 用户ID
     * @return 影响行数
     */
    int deleteById(@Param("userId") String userId);
} 