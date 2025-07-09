<template>
  <div class="container">
    <div class="listItem">
      <!-- 各ヘッドラインリスト -->
      <div class="containerItem" v-for="item in pageData" :key="item.hid">
        <div>
          <span class="text">{{ item.title }}</span>
        </div>
        <div class="detail">
          <span>{{ item.type == 1 ? "ニュース":item.type == 2 ? "スポーツ": item.type == 3 ? "エンタメ": item.type == 4 ? "テクノロジー" : "その他" }}</span>
          <span>{{item.pageViews}}閲覧</span>
          <span>{{item.pastHours}}時間前</span>
        </div>
        <div>
          <el-button @click="toDetail(item.hid)" size="small"
            style="background: #198754; margin-left: 15px; color: #bbd3dc">全文を見る</el-button>
          <el-popconfirm v-if="item.publisher == type" @confirm="handlerDelete(item.hid)" :title="`本当に${item.title}を削除しますか?`">
            <template #reference>
              <el-button size="small" style="background: #dc3545; color: #bbd3dc">削除</el-button>
            </template>
          </el-popconfirm>
          <el-button @click="Modify(item.hid)" v-if="item.publisher == type"  size="small" style="background: #212529; color: #bbd3dc">修正</el-button>
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

<script >
import { getfindNewsPageInfo , removeByHid } from "../../api/index"
import { defineComponent } from 'vue'
export default  defineComponent({
  name:'HeadlineNews'
})
</script>
<script  setup>
import { ref, onMounted, getCurrentInstance, watch } from "vue"
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import pinia from '../../stores/index';
import { useUserInfoStore } from '../../stores/userInfo'
const  { Bus } = getCurrentInstance().appContext.config.globalProperties
const userInfoStore = useUserInfoStore(pinia)
const router = useRouter()
const type = userInfoStore.uid
const findNewsPageInfo = ref(
  {
    keyWords: "",
    type: 0,
    pageNum: 1,
    pageSize: 5,
  }
)
const totalSize = ref(0)
// 初期リストデータ
const pageData = ref([{
  hid: null,
  pageViews: null,
  pastHours: null,
  publisher: null,
  title: "",
  type: null
}])
// headerコンポーネントから検索データを受信
Bus.on('keyword', (keywords) => {
  findNewsPageInfo.value.keyWords = keywords
})
// headerで高亮切替時にtidを受信
Bus.on('tid', (type) => {
  findNewsPageInfo.value.type = type
})
// typeの変化を監視し、リストデータを再取得
watch(() => findNewsPageInfo.value, () => {
  getPageList()
}, {
  deep: true,
})
// ページネーションデータ取得
const getPageList = async () => {
  let result = await getfindNewsPageInfo(findNewsPageInfo.value)
  pageData.value = result.pageInfo.pageData
 findNewsPageInfo.value.pageNum = result.pageInfo.pageNum
 findNewsPageInfo.value.pageSize = result.pageInfo.pageSize
 totalSize.value = +result.pageInfo.totalSize
}
onMounted(() => {
  getPageList()
})
// 詳細ページへ遷移
const toDetail = (hid) => {
  router.push({ name: "Detail" ,query:{ hid }});
}
// 削除ボタンのコールバック
const handlerDelete = async (id) => {
  await removeByHid(id)
  ElMessage.success('削除成功!')
  getPageList()
}
// 修正ボタンのコールバック
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
