package com.example.utils;

/**
 * 統一されたレスポンス結果のステータス情報クラス
 */
public enum ResultCodeEnum {

    SUCCESS(200, "success"),            // 成功
    USERNAME_ERROR(501, "usernameError"), // ユーザー名エラー
    PASSWORD_ERROR(503, "passwordError"), // パスワードエラー
    NOTLOGIN(504, "notLogin"),           // 未ログイン
    USERNAME_USED(505, "userNameUsed");  // ユーザー名が既に使用されています

    private Integer code;    // ステータスコード
    private String message;  // メッセージ

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
