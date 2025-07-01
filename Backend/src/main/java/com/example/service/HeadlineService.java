package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.Headline;
import com.example.pojo.vo.PortalVo;
import com.example.utils.Result;

/**
 * ヘッドライン関連のサービスインターフェース
 */
public interface HeadlineService extends IService<Headline> {

    /**
     * トップページのデータ取得
     */
    Result findNewsPage(PortalVo portalVo);

    /**
     * 詳細情報取得
     */
    Result showHeadlineDetail(Integer hid);

    /**
     * ヘッドラインの投稿
     */
    Result publish(Headline headline, String token);

    /**
     * ヘッドラインの更新
     */
    Result updateData(Headline headline);
}
