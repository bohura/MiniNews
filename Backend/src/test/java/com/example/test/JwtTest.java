package com.example.test;

import com.example.utils.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * JWT機能のテストクラス
 */
@SpringBootTest
public class JwtTest {

    @Autowired
    private JwtHelper jwtHelper;

    @Test
    public void test() {
        // トークン生成（ユーザーIDを渡す）
        String token = jwtHelper.createToken(1L);
        System.out.println("token = " + token);

        // トークン解析（ユーザーIDを取得）
        int userId = jwtHelper.getUserId(token).intValue();
        System.out.println("userId = " + userId);

        // 有効期限チェック false：有効 true：期限切れ
        boolean expiration = jwtHelper.isExpiration(token);
        System.out.println("expiration = " + expiration);
    }
}
