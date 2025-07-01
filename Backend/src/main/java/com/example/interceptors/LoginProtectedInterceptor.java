package com.example.interceptors;

import com.example.utils.JwtHelper;
import com.example.utils.Result;
import com.example.utils.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * ログイン保護インターセプター。リクエストヘッダーに有効なトークンが含まれているかを確認する。
 * 有効な場合：許可する
 * 無効な場合：504エラーを返す
 */
@Component
public class LoginProtectedInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // リクエストヘッダーからトークンを取得
        String token = request.getHeader("token");
        // トークンの有効性を確認
        boolean expiration = jwtHelper.isExpiration(token);
        // 有効なら許可
        if (!expiration) {
            return true;
        }
        // 無効なら504エラーのJSONを返す
        Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().print(json);

        return false;
    }
}
