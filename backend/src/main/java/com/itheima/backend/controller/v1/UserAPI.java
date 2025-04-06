package com.itheima.backend.controller.v1;

import com.itheima.backend.model.entity.User;
import com.itheima.backend.service.UserService;
import com.itheima.backend.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserAPI {
    /**
     * session的字段名
     */
    public static final String SESSION_NAME = "userInfo";

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param user    传入登录用户信息
     * @param session 会话对象
     * @return 登录结果
     */
    @PostMapping(value = "/login", produces = "application/json")
    public Result<User> login(@RequestBody @Valid User user, HttpSession session) {
        Result<User> result = userService.login(user);
        if (result.isSuccess()) {
            // 登录成功，将用户信息存入session
            session.setAttribute(SESSION_NAME, result.getData());
        }
        return result;
    }

    /**
     * 用户注册
     *
     * @param user    传入注册用户信息
     * @param errors  Validation的校验错误存放对象
     * @return 注册结果
     */
    @PostMapping(value = "/register", produces = "application/json")
    public Result<User> register(@RequestBody @Valid User user, BindingResult errors) {  // BindingResult 是什么意思？

        Result<User> result = new Result<>();

        // 如果校验有错误，返回注册失败以及错误信息
        if (errors.hasErrors()) {
            result.setResultFailed(errors.getFieldError().getDefaultMessage());
            return result;
        }

        // 调用注册服务
        return userService.register(user);
    }
}
