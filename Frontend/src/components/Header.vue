<template>
  <div class="headerContainer">
    <!-- ヘッダーの左エリア -->
    <div class="left">
      <ul>
        <li @click="HighlightHandler(index)" v-for="(item,index) in findAllTypeList" :key="item.tid">
          <a :class="{ active: item.isHighlight }" href="javascript:;">{{item.tname}}</a>
        </li>
      </ul>
    </div>
    <!-- ヘッダーの右エリア -->
    <div class="right">
      <div class="rightInput" style="margin-right: 50px;">
        <el-input v-model="keywords" placeholder="最新ニュースを検索"></el-input>
      </div>

      <!-- ログイン済みの表示 -->
      <div class="btn-dropdown">
        <!-- 未ログイン時の表示 -->
        <div v-if="nickName" style="display: flex; justify-content: center; align-items: center;">
          <el-dropdown>
            <el-button type="primary">
              こんにちは:{{ nickName }}<el-icon class="el-icon--right"><arrow-down /></el-icon>
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handlerNews">ニュースを投稿する</el-dropdown-item>
                <el-dropdown-item>マイページ</el-dropdown-item>
                <el-dropdown-item>閲覧履歴</el-dropdown-item>
                <el-dropdown-item @click="Logout">ログアウト</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
        <div v-else class="containerButton">
          <el-button size="small" style="background: #212529; color: #aea7a2" @click="toLogin">ログイン</el-button>
          <el-button size="small" style="background: #ffc107; color: #684802" @click="toRegister">新規登録</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
export default defineComponent({
  name: 'Header'
})
</script>

<script setup>
import { getfindAllTypes, isUserOverdue } from '../api/index'
import { ref, onMounted , getCurrentInstance ,watch, onUpdated } from "vue"
import { useRouter } from 'vue-router'
import { ArrowDown } from '@element-plus/icons-vue'
import { removeToken } from '../utils/token-utils' 
import pinia from '../stores/index';
import { useUserInfoStore } from '../stores/userInfo'

const userInfoStore = useUserInfoStore(pinia)
const nickName = ref("")
const { Bus } = getCurrentInstance().appContext.config.globalProperties
const router = useRouter()
const keywords = ref("") // 最新ニュース検索のキーワード
// 検索キーワードを監視し、HeadlineNewsコンポーネントへ送信
watch(keywords, (newVal) => {
  Bus.emit('keyword', newVal)
})

const findAllTypeList = ref([]) // 全てのカテゴリを取得

// ログインページへ遷移
const toLogin = () => {
  router.push({ name: "Login" });
}

// 新規登録ページへ移動
const toRegister = () => {
  router.push({ name: "Register" });
}

// カテゴリ一覧を取得して表示
const getList = async () => {
  let result = await getfindAllTypes()
  result.forEach((item) => {
    item.tid = item.tid
    item.tname = item.tname
    item.isHighlight = false
  })
  // 「ミニニュース」を先頭に追加
  result.unshift({
    isHighlight: true,
    tid: 0,
    tname: "ミニニュース"
  })
  findAllTypeList.value = result
}

// ページマウント時の処理
onUpdated(() => {
  nickName.value = userInfoStore.nickName
})
onMounted(() => {
  getList()
})

// ハイライト切り替え処理（排他）
const HighlightHandler = (index) => {
  findAllTypeList.value.forEach((item) => {
    item.isHighlight = false
  })
  findAllTypeList.value[index].isHighlight = true
  Bus.emit('tid', findAllTypeList.value[index].tid)
}

// ログアウト処理
const Logout = () => {
  removeToken()
  userInfoStore.initUserInfo()
  nickName.value = ""
  router.go({ name: "HeadlineNews" });
}

// ニュース投稿ボタンの処理
const handlerNews = async () => {
  await isUserOverdue()
  router.push({ name: "addOrModifyNews" });
}
</script>

<style>
.el-dropdown {
  vertical-align: top;
  width: 100px;
}
.el-dropdown+.el-dropdown {
  margin-left: 15px;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>

<style lang="less" scoped>
.headerContainer {
  width: 100%;
  height: 60px;
  background: #212529;
  display: flex;
  justify-content: space-around;
  .left {
    ul {
      display: flex;
      li {
        list-style: none;
        margin-left: 20px;
        a:-webkit-any-link {
          text-decoration: none;
          color: #59646b;
          &.active {
            color: #c0adab;
          }
        }
      }
    }
  }
  .right {
    .containerButton {
      display: flex;
      align-items: center;
    }
    line-height: 60px;
    display: flex;
    flex-wrap: nowrap;
    .rightInput {
      display: flex;
      align-items: center;
      :deep(.el-input__inner) {
        height: 30px;
        width: 150px;
      }
    }
    .btn-dropdown {
      display: flex;
      align-items: center;
    }
    :deep(.el-button) {
      margin: 0 0 0 10px;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
}
.example-showcase .el-dropdown + .el-dropdown {
  margin-left: 15px;
}
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>
