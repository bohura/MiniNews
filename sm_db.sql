-- データベース作成
CREATE DATABASE IF NOT EXISTS sm_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- データベース選択
USE sm_db;

-- ヘッドラインテーブル
CREATE TABLE news_headline (
                               hid         INT AUTO_INCREMENT COMMENT 'ヘッドラインID'
        PRIMARY KEY,
                               title       VARCHAR(50)   NOT NULL COMMENT 'タイトル',
                               article     VARCHAR(5000) NOT NULL COMMENT '記事内容',
                               type        INT           NOT NULL COMMENT 'カテゴリID',
                               publisher   INT           NOT NULL COMMENT '投稿ユーザーID',
                               page_views  INT           NOT NULL COMMENT '閲覧数',
                               create_time DATETIME      NULL COMMENT '投稿日時',
                               update_time DATETIME      NULL COMMENT '最終更新日時',
                               version     INT DEFAULT 1 NULL COMMENT '楽観ロック',
                               is_deleted  INT DEFAULT 0 NULL COMMENT '削除フラグ (1: 削除済み, 0: 未削除)'
) ROW_FORMAT = DYNAMIC;

-- カテゴリテーブル
CREATE TABLE news_type (
                           tid        INT AUTO_INCREMENT COMMENT 'カテゴリID'
        PRIMARY KEY,
                           tname      VARCHAR(10)   NOT NULL COMMENT 'カテゴリ名',
                           version    INT DEFAULT 1 NULL COMMENT '楽観ロック',
                           is_deleted INT DEFAULT 0 NULL COMMENT '削除フラグ (1: 削除済み, 0: 未削除)'
) ROW_FORMAT = DYNAMIC;

-- ユーザーテーブル
CREATE TABLE news_user (
                           uid        INT AUTO_INCREMENT COMMENT 'ユーザーID'
        PRIMARY KEY,
                           username   VARCHAR(20)   NOT NULL COMMENT 'ログインユーザー名',
                           user_pwd   VARCHAR(50)   NOT NULL COMMENT 'パスワード (暗号化)',
                           nick_name  VARCHAR(20)   NOT NULL COMMENT 'ニックネーム',
                           version    INT DEFAULT 1 NULL COMMENT '楽観ロック',
                           is_deleted INT DEFAULT 0 NULL COMMENT '削除フラグ (1: 削除済み, 0: 未削除)',
                           CONSTRAINT username_unique UNIQUE (username)
) ROW_FORMAT = DYNAMIC;
