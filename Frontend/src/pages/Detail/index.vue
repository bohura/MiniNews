<template>
  <div class="seeDetails">
    <div>
      <h4>{{ detailList.title }}</h4>
    </div>
    <div style="margin-right: 250px">
      <span>{{ detailList.typeName }}</span>
      <span>{{ detailList.pageViews }}閲覧</span>
      <span>{{ detailList.pastHours }}時間前</span>
    </div>
    <div style="width: 500px; margin: 20px 0px 0px 70px">
      <p>
        {{ detailList.article }}
      </p>
    </div>
  </div>
</template>

<script >
import { defineComponent } from 'vue'
export default  defineComponent({
  name:'Detail'
})
</script>
<script  setup>
import { getshowHeadlineDetail } from "../../api/index"
import { ref , onMounted } from "vue"
import { useRoute } from 'vue-router'
const route = useRoute()
const detailList = ref({})
// 詳細データ取得
const getDetailList = async () => {
  let result = await getshowHeadlineDetail(route.query.hid)
  detailList.value = result.headline
}
onMounted(() => {
  getDetailList()
})
</script>

<style lang="less" scoped>
.seeDetails {
  width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  div {
    span {
      padding-right: 15px;
      font-size: 14px;
      color: #8d91aa;
    }
    p {
      font-size: 14px;
      color: #2b2e30;
    }
  }
}
</style>
