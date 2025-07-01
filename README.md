# MiniNews ニュース管理システム

**MiniNews** は、Spring Boot + MyBatis Plus + Vue3 + Vite 構成の簡易ニュース管理システムです。  
ユーザー登録、ログイン、ニュース閲覧・投稿・編集・削除などの機能を備えています。

---

## 📦 技術スタック

### Backend（APIサーバー）

- Spring Boot 3.0.5
- MyBatis Plus
- MySQL
- Druid コネクションプール
- JWT 認証
- Lombok

### Frontend（Webクライアント）

- Vue3
- Vite
- axios
- element-plus

---

## 🚀 機能一覧

### 一般ユーザー向け

- 🔑 ユーザー登録・ログイン
- 📰 ニュース記事一覧表示（検索・カテゴリ絞り込み）
- 🔍 ニュース詳細閲覧
- 🛡️ 認証トークン管理

### 管理者（ログイン後）

- 📝 ニュース投稿
- ✏️ ニュース編集
- 🗑️ ニュース削除
- 📊 自身が投稿した記事の管理

---