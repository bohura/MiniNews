import request from "../utils/request/"

// カテゴリ一覧を取得
export const getfindAllTypes = () => {
  return request.get("portal/findAllTypes");
};
// ページネーションと条件付きで全てのヘッドラインを取得
export const getfindNewsPageInfo = (info) => {
  return request.post("portal/findNewsPage",info);
};
// ヘッドライン詳細を表示
export const getshowHeadlineDetail = (id) => {
    return request({
        method: "post",
        url: "portal/showHeadlineDetail",
        headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
        },
         data:`hid=${id}`
  });
};

// 削除のコールバック
export const removeByHid = (id) => {
    return request({
         method: "post",
            url: "headline/removeByHid",
            headers: {
            "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
            },
            data:`hid=${id}`
  })
};

// ログインAPI
export const getLogin = (info) => {
  return request.post("user/login",info);
};
// ユーザー情報取得API
export const getUserInfo = (info) => {
  return request.get("user/getUserInfo");
};

// ユーザー名重複チェックAPI
export const registerValidateApi = (username) => {
    return request({
         method: "post",
            url: "user/checkUserName",
            headers: {
            "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
            },
            data:`username=${username}`
  })
};

// 登録API
export const registerApi = (userInfo) => {
  return request.post("user/regist",userInfo)
}
// ユーザーログイン有効性チェックAPI
export const isUserOverdue = () => {
  return request.get("user/checkLogin")
}

// ヘッドライン編集用データ取得API
export const getFindHeadlineByHid = (id) => {
    return request({
        method: "post",
        url: "headline/findHeadlineByHid",
        headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
        },
         data:`hid=${id}`
  });
};

// 保存または追加のコールバック
export const saveOrAddNews = (news) => {
  return request.post("headline/update",news)
}

// ニュース公開API
export const issueNews = (news) => {
  return request.post("headline/publish",news)
}
