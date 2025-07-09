import request from "../utils/request/"

// portal/findAllTypes
// カテゴリ一覧を取得
export const getfindAllTypes = () => {
  return request.get("portal/findAllTypes");
};

// 条件付きでニュース一覧をページング取得
export const getfindNewsPageInfo = (info) => {
  return request.post("portal/findNewsPage", info);
};

// ニュース詳細を取得
export const getshowHeadlineDetail = (id) => {
  return request({
    method: "post",
    url: "portal/showHeadlineDetail",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
    },
    data: `hid=${id}`
  });
};

// ニュース削除処理
// headline/removeByHid
export const removeByHid = (id) => {
  return request({
    method: "post",
    url: "headline/removeByHid",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
    },
    data: `hid=${id}`
  });
};

// ログインAPI
export const getLogin = (info) => {
  return request.post("user/login", info);
};

// ユーザー情報を取得
export const getUserInfo = () => {
  return request.get("user/getUserInfo");
};

// ユーザー名重複チェックAPI
// user/checkUserName
export const registerValidateApi = (username) => {
  return request({
    method: "post",
    url: "user/checkUserName",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
    },
    data: `username=${username}`
  });
};

// ユーザー登録API
export const registerApi = (userInfo) => {
  return request.post("user/regist", userInfo)
};

// ログイン状態確認（期限切れチェック）
export const isUserOverdue = () => {
  return request.get("user/checkLogin")
};

// 編集用のニュース取得API
export const getFindHeadlineByHid = (id) => {
  return request({
    method: "post",
    url: "headline/findHeadlineByHid",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
    },
    data: `hid=${id}`
  });
};

// ニュース保存・更新API
// headline/update
export const saveOrAddNews = (news) => {
  return request.post("headline/update", news)
};

// ニュース公開API
// headline/publish
export const issueNews = (news) => {
  return request.post("headline/publish", news)
};
