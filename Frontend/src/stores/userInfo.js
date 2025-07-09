
import { defineStore } from 'pinia';
import { getToken, removeToken, setToken } from '../utils/token-utils';
import { getLogin,getUserInfo } from '../api/index';

// ユーザー情報ストア
export const useUserInfoStore = defineStore('userInfo', {

  state: () => ({
    token: getToken(),
    nickName: '',
    uid: '',
  }),

  actions: {
    // ログインの非同期アクション
    async login (loginForm) {
      const result = await getLogin(loginForm)
      const token = result.token
      this.token = token
      setToken(token)
    },
    async getInfo () {
      const result = await getUserInfo()
      this.nickName = result.loginUser.nickName
      this.uid = result.loginUser.uid
    },
    initUserInfo(){
      removeToken()
      this.nickName = ""
      this.uid = ""
      console.log('1111111111');
    }
  },
});
