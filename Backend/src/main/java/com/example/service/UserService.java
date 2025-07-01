package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.User;
import com.example.utils.Result;

/**
 * アカウント関連のサービスインターフェース
 */
public interface UserService extends IService<User> {

    /**
     * ログイン処理
     */
    Result login(User user);

    /**
     * トークンからユーザー情報取得
     */
    Result getUserInfo(String token);

    /**
     * アカウントの重複確認
     */
    Result checkUserName(String username);

    /**
     * ユーザー登録処理
     */
    Result regist(User user);
}
