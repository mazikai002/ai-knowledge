package com.itheima.backend.service.impl;

import com.itheima.backend.common.Result;
import com.itheima.backend.repository.mybatis.UserMapper;
import com.itheima.backend.model.entity.User;
import com.itheima.backend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public Result<User> register(User user) {
        // 1. 初始化返回值
        Result<User> result = new Result<>();

        // 2. 现在数据库查找用户是否存在
        User getUser = userMapper.findByUsername(user.getUsername());
        if (getUser != null) {  // 如果用户存在，直接返回
            result.setResultFailed("用户已存在！");
            return result;
        }

        // 如果用户不存在，存入数据库
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes())); // 对用户密码进行加密存储

        // 3. 数据库操作
        userMapper.insert(user);
        result.setResultSuccess("注册用户成功！", user);
        return result;
    }

    @Override
    public Result<User> update(User user) throws Exception {
        return null;
    }

    @Override
    public Result<User> isLogin(HttpSession session) {
        return null;
    }

    @Override
    public Result<User> login(User user) {
        // 1. 初始化返回值
        Result<User> result = new Result<>();

        // 2. 从数据库查找用户
        User getUser = userMapper.findByUsername(user.getUsername());
        if (getUser == null) {  // 如果用户不存在
            result.setResultFailed("用户不存在！");
            return result;
        }

        // 3. 验证密码
        String encryptedPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        if (!getUser.getPassword().equals(encryptedPassword)) {  // 如果密码不正确
            result.setResultFailed("密码错误！");
            return result;
        }

        // 4. 登录成功
        result.setResultSuccess("登录成功！", getUser);
        return result;
    }
}
