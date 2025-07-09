import axios from "axios";
import { ElMessage } from 'element-plus';
import pinia from '../stores/index';
import { useUserInfoStore } from '../stores/userInfo';
import NProgress from "nprogress";
import "nprogress/nprogress.css";
// 新しいaxiosインスタンスを作成
const service = axios.create({
  baseURL: "/app-dev/",
  timeout: 50000,
});

// リクエストインターセプター追加
service.interceptors.request.use((config) => {
  NProgress.start()
  // tokenがあればリクエストヘッダーに付与
  const userInfoStore = useUserInfoStore(pinia)
  const token = userInfoStore.token
  if (token) {
    (config.headers)['token'] = token
  }
  return config;
});

// レスポンスインターセプター追加
service.interceptors.response.use(
  (response) => {
    NProgress.done()
    if(response.data.code !== 200){
      if (response.data.code == 501)  return  Promise.reject(ElMessage.error("ユーザー名が正しくありません"))
      else if (response.data.code == 503) return  Promise.reject(ElMessage.error("パスワードが正しくありません"))
      else if (response.data.code == 504) return  Promise.reject(ElMessage.error("ログインの有効期限が切れました"))
      else if (response.data.code == 505) return  Promise.reject(ElMessage.error("ユーザー名は既に使用されています"))
    } else {
      return response.data.data;
    }
  },
  (error) => {
    NProgress.done()
    return Promise.reject(error.message);
  }
);

export default service;
