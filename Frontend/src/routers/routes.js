export const staticRoutes = [
  {
    path: "/",
    redirect: "/headlinenews",
  },
  {
    // ヘッドラインニュース
    path: "/headlinenews",
    component: () => import("../pages/HeadlineNews/index.vue"),
    name: "HeadlineNews",
  },
  {
    // ヘッドラインの詳細
    path: "/detail",
    component: () => import("../pages/Detail/index.vue"),
    name: "Detail",
  },
  {
    // ログイン
    path: "/login",
    component: () => import("../pages/Login/index.vue"),
    name: "Login",
  },
  {
    // 新規登録
    path: "/register",
    component: () => import("../pages/Register/index.vue"),
    name: "Register",
  },
  {
    // ニュース投稿・編集ページ
    path: "/addormodifynews",
    component: () => import("../pages/addOrModifyNews/index.vue"),
    name: "addOrModifyNews",
  },
];
