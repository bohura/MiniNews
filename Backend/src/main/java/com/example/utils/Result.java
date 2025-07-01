package com.example.utils;

/**
 * 全体で統一されたレスポンス結果クラス
 */
public class Result<T> {

    // ステータスコード
    private Integer code;
    // メッセージ
    private String message;
    // データ
    private T data;

    public Result() {
    }

    // データを含むレスポンス生成（内部用）
    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null)
            result.setData(data);
        return result;
    }

    // カスタムステータスとメッセージを指定してレスポンス生成
    public static <T> Result<T> build(T body, Integer code, String message) {
        Result<T> result = build(body);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    // Enumを指定してレスポンス生成
    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    /**
     * 操作成功時のレスポンス
     *
     * @param data レスポンスデータ
     * @param <T>  データ型
     * @return 結果オブジェクト
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }

    // メッセージを手動設定
    public Result<T> message(String msg) {
        this.setMessage(msg);
        return this;
    }

    // コードを手動設定
    public Result<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
