<template>
  <div class="container">
    <div class="listItem">
      <!-- 各ヘッドラインニュースのリスト項目 -->
      <div class="containerItem" v-for="item in pageData" :key="item.hid">
        <div>
          <span class="text">{{ item.title }}</span>
        </div>
        <div class="detail">
          <span>{{ item.type == 1 ? "ニュース" : item.type == 2 ? "スポーツ" : item.type == 3 ? "エンタメ" : item.type == 4 ? "テクノロジー" : "その他" }}</span>
          <span>{{ item.pageViews }}閲覧</span>
          <span>{{ item.pastHours }}時間前</span>
        </div>
        <div>
          <el-button @click="toDetail(item.hid)" size="small"
            style="background: #198754; margin-left: 15px; color: #bbd3dc">全文を見る</el-button>
          <el-popconfirm v-if="item.publisher == type" @confirm="handlerDelete(item.hid)" :title="`「${item.title}」を削除してもよろしいですか？`">
            <template #reference>
              <el-button size="small" style="background: #dc3545; color: #bbd3dc">削除</el-button>
            </template>
          </el-popconfirm>

          <el-button @click="Modify(item.hid)" v-if="item.publisher == type" size="small" style="background: #212529; color: #bbd3dc">編集</el-button>
        </div>
      </div>

      <!-- ページネーション -->
      <div style="margin-top: 20px">
        <el-pagination 
          v-model:current-page="findNewsPageInfo.pageNum"
          v-model:page-size="findNewsPageInfo.pageSize" 
          @size-change="getPageList"
          @current-change="getPageList"
          :page-sizes="[5,7,10]" 
          background
          layout="prev, pager, next , ->, sizes, total" 
          :total="totalSize" />
      </div>
    </div>
  </div>
</template>

<script>
import { getfindNewsPageInfo, removeByHid } from "../../api/index"
import { defineComponent } from 'vue'
export default defineComponent({
  name: 'HeadlineNews'
})
</script>

<script setup>
import { ref, onMounted, getCurrentInstance, watch } from "vue"
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import pinia from '../../stores/index';
import { useUserInfoStore } from '../../stores/userInfo'

const { Bus } = getCurrentInstance().appContext.config.globalProperties
const userInfoStore = useUserInfoStore(pinia)
const router = useRouter()
const type = userInfoStore.uid

const findNewsPageInfo = ref({
  keyWords: "", // タイトルのキーワード検索
  type: 0,      // ニュースの種類
  pageNum: 1,   // ページ番号
  pageSize: 5,  // ページサイズ
})

const totalSize = ref(0) // ページ全体の件数

// 初期リストデータ
const pageData = ref([{
  hid: null,
  pageViews: null,
  pastHours: null,
  publisher: null,
  title: "",
  type: null
}])

// Headerコンポーネントからキーワードを受信
Bus.on('keyword', (keywords) => {
  findNewsPageInfo.value.keyWords = keywords
})

// HeaderからタイプID（tid）を受信
Bus.on('tid', (type) => {
  findNewsPageInfo.value.type = type
})

// 検索条件の変更を監視し、変更時に再取得
watch(() => findNewsPageInfo.value, () => {
  getPageList()
}, {
  deep: true,
})

// ページネーション対応のリストデータ取得
const getPageList = async () => {
  let result = await getfindNewsPageInfo(findNewsPageInfo.value)
  pageData.value = result.pageInfo.pageData
  findNewsPageInfo.value.pageNum = result.pageInfo.pageNum
  findNewsPageInfo.value.pageSize = result.pageInfo.pageSize
  totalSize.value = +result.pageInfo.totalSize
}

// マウント時にリスト取得
onMounted(() => {
  getPageList()
})

// 「全文を見る」ボタンの処理
const toDetail = (hid) => {
  router.push({ name: "Detail", query: { hid } });
}

// 削除処理
const handlerDelete = async (id) => {
  await removeByHid(id)
  ElMessage.success('削除に成功しました！')
  getPageList()
}

// 編集ボタンの処理
const Modify = (hid) => {
  router.push({ name: "addOrModifyNews", query: { hid } });
}
</script>

<style lang="less" scoped>
.container {
  width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;

  // リストスタイル
  .listItem {
    .containerItem {
      margin-top: 20px;
      border-radius: 10px;
      border: 2px solid #ebebeb;
      width: 600px;
      height: 120px;

      div {
        margin-top: 10px;
      }

      .text {
        margin-left: 15px;
        color: #353a3f;
      }

      .detail {
        span {
          margin-left: 15px;
          color: #8b778a;
          font-size: 14px;
        }
      }
    }
  }
}
</style>
