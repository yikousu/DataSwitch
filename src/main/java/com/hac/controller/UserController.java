package com.hac.controller;

import com.hac.entity.User;
import com.hac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 * 提供用户相关的REST API
 * 
 * @author HAC
 * @since 2025-07-14
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户ID获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根据用户名获取用户信息
     * 
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 获取所有用户
     * 
     * @return 用户列表
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * 创建用户
     * 
     * @param createUserRequest 创建用户请求
     * @return 创建的用户
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        try {
            User user = userService.createUser(
                createUserRequest.getUsername(),
                createUserRequest.getPassword(),
                createUserRequest.getRealName(),
                createUserRequest.getEmail(),
                createUserRequest.getMobile()
            );
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 更新用户信息
     * 
     * @param userId 用户ID
     * @param user 用户信息
     * @return 操作结果
     */
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody User user) {
        user.setUserId(userId);
        boolean success = userService.updateUser(user);
        if (success) {
            return ResponseEntity.ok("用户信息更新成功");
        } else {
            return ResponseEntity.badRequest().body("用户信息更新失败");
        }
    }

    /**
     * 更新用户状态
     * 
     * @param userId 用户ID
     * @param status 新状态
     * @return 操作结果
     */
    @PutMapping("/{userId}/status")
    public ResponseEntity<String> updateUserStatus(@PathVariable String userId, 
                                                  @RequestParam Integer status) {
        boolean success = userService.updateUserStatus(userId, status);
        if (success) {
            return ResponseEntity.ok("用户状态更新成功");
        } else {
            return ResponseEntity.badRequest().body("用户状态更新失败");
        }
    }

    /**
     * 删除用户
     * 
     * @param userId 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        boolean success = userService.deleteUser(userId);
        if (success) {
            return ResponseEntity.ok("用户删除成功");
        } else {
            return ResponseEntity.badRequest().body("用户删除失败");
        }
    }

    /**
     * 创建用户请求DTO
     */
    public static class CreateUserRequest {
        private String username;
        private String password;
        private String realName;
        private String email;
        private String mobile;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
} 