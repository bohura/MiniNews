package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.Type;
import com.example.utils.Result;

/**
 * タイプ情報関連のサービスインターフェース
 */
public interface TypeService extends IService<Type> {

    /**
     * すべてのタイプ情報を取得
     */
    Result findAllTypes();
}
