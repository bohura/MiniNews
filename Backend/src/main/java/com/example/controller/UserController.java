package com.example.controller;

import com.example.pojo.User;
import com.example.service.UserService;
import com.example.utils.JwtHelper;
import com.example.utils.Result;
import com.example.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ユーザーコントローラー
 */

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserService userService;

    // ログイン処理
    @PostMapping("login")
    public Result login(@RequestBody User user) {
        Result result = userService.login(user);
        return result;
    }

    // ユーザー情報取得
    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token) {
        Result result = userService.getUserInfo(token);
        return result;
    }

    // ユーザー名重複チェック
    @PostMapping("checkUserName")
    public Result checkUserName(String username) {
        Result result = userService.checkUserName(username);
        return result;
    }

    // ユーザー登録
    @PostMapping("regist")
    public Result regist(@RequestBody User user) {
        Result result = userService.regist(user);
        return result;
    }

    // ログイン状態確認
    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token) {

        boolean expiration = jwtHelper.isExpiration(token);

        if (expiration) {
            // トークンが無効
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        return Result.ok(null);
    }
}
