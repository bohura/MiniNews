package com.example.utils;

import com.alibaba.druid.util.StringUtils;
import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWTトークン操作用ヘルパークラス
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class JwtHelper {

    private long tokenExpiration; // 有効時間（単位：ミリ秒）1000ミリ秒 = 1秒
    private String tokenSignKey;  // 現在のアプリケーション署名キー

    /**
     * トークン文字列を生成
     *
     * @param userId ユーザーID
     * @return トークン文字列
     */
    public String createToken(Long userId) {
        System.out.println("tokenExpiration = " + tokenExpiration);
        System.out.println("tokenSignKey = " + tokenSignKey);
        String token = Jwts.builder()
                .setSubject("YYGH-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration * 1000 * 60)) // 単位：分
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    /**
     * トークン文字列からユーザーIDを取得
     *
     * @param token トークン文字列
     * @return ユーザーID
     */
    public Long getUserId(String token) {
        if (StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId.longValue();
    }

    /**
     * トークンが有効かどうか判定
     *
     * @param token トークン文字列
     * @return 期限切れの場合true、有効な場合false
     */
    public boolean isExpiration(String token) {
        try {
            boolean isExpire = Jwts.parser()
                    .setSigningKey(tokenSignKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration().before(new Date());
            return isExpire; // 有効ならfalse
        } catch (Exception e) {
            return true; // エラーが出たら期限切れとみなす
        }
    }
}
