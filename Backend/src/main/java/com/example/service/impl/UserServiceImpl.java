package com.example.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.UserMapper;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.utils.JwtHelper;
import com.example.utils.MD5Util;
import com.example.utils.Result;
import com.example.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * ユーザー関連のサービス実装クラス
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result login(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);

        if (loginUser == null) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        if (!StringUtils.isEmpty(user.getUserPwd())
                && MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())) {
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            return Result.ok(data);
        }

        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
    }

    @Override
    public Result getUserInfo(String token) {
        boolean expiration = jwtHelper.isExpiration(token);

        if (expiration) {
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }

        int userId = jwtHelper.getUserId(token).intValue();
        User user = userMapper.selectById(userId);
        user.setUserPwd("");

        Map<String, Object> data = new HashMap<>();
        data.put("loginUser", user);

        return Result.ok(data);
    }

    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        Long count = userMapper.selectCount(queryWrapper);

        if (count == 0) {
            return Result.ok(null);
        }
        return Result.build(null, ResultCodeEnum.USERNAME_USED);
    }

    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }

        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        userMapper.insert(user);

        return Result.ok(null);
    }
}
