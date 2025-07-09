import { defineStore } from 'pinia';
import { getToken, removeToken, setToken } from '../utils/token-utils';
import { getLogin, getUserInfo } from '../api/index';

/**
 * ユーザー情報ストア
 * @methods setUserInfos ユーザー情報を設定する
 */
export const useUserInfoStore = defineStore('userInfo', {
  state: () => ({
    token: getToken(),
    nickName: '',
    uid: '',
  }),

  actions: {
    // ログイン処理の非同期アクション
    async login(loginForm) {
      // ログインリクエストを送信
      const result = await getLogin(loginForm)
      // リクエスト成功後、トークンを取得して pinia と localStorage に保存
      const token = result.token
      
      this.token = token
      setToken(token)
    },

    async getInfo() {
      const result = await getUserInfo()
      this.nickName = result.loginUser.nickName
      this.uid = result.loginUser.uid
    },

    // ユーザー情報の初期化
    initUserInfo() {
      removeToken()
      this.nickName = ""
      this.uid = ""
      console.log('1111111111');
    }
  },
});
