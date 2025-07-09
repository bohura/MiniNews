import axios from "axios";
import { ElMessage } from 'element-plus';
import pinia from '../stores/index';
import { useUserInfoStore } from '../stores/userInfo';
import NProgress from "nprogress";
import "nprogress/nprogress.css";

// 新しい axios インスタンスを設定
const service = axios.create({
  baseURL: "/app-dev/",
  timeout: 50000,
});

// リクエストインターセプターを追加
service.interceptors.request.use((config) => {
  NProgress.start() // プログレスバーを開始

  // トークンが存在する場合は、リクエストヘッダーに含めてバックエンドに送信
  const userInfoStore = useUserInfoStore(pinia) // コンポーネント外で呼び出す場合は pinia を渡す必要がある
  const token = userInfoStore.token
  if (token) {
    // config.headers['token'] = token  // エラー: headers オブジェクトに token が定義されていないため、直接追加できない
    (config.headers)['token'] = token
  }
  return config;
});

// レスポンスインターセプターを追加
service.interceptors.response.use(
  (response) => {
    NProgress.done() // プログレスバーを終了

    // レスポンスステータスコードを判定
    if (response.data.code !== 200) {
      if (response.data.code == 501) return Promise.reject(ElMessage.error("ユーザー名が間違っています"))
      else if (response.data.code == 503) return Promise.reject(ElMessage.error("パスワードが間違っています"))
      else if (response.data.code == 504) return Promise.reject(ElMessage.error("ログインの有効期限が切れました"))
      else if (response.data.code == 505) return Promise.reject(ElMessage.error("ユーザー名はすでに使用されています"))
    } else {
      return response.data.data; // 成功したレスポンスの data プロパティを返す
    }
  },
  (error) => {
    NProgress.done() // プログレスバーを終了
    return Promise.reject(error.message);
  }
);

export default service;
