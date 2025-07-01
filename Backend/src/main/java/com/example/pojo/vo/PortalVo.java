package com.example.pojo.vo;

import lombok.Data;

/**
 * プロジェクト名: com.example.pojo.vo
 * 説明:
 */

@Data
public class PortalVo {

    private String keyWords;  // キーワード
    private int type = 0;      // カテゴリーID

    private int pageNum = 1;   // 現在のページ番号

    private int pageSize = 10; // 1ページの表示数
}
