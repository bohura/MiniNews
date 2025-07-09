<template>
  <el-card class="box-card AddNewsContainer">
    <el-form :rules="newsRules" :model="formData" ref="formRef" size="default">
      <el-form-item label="記事タイトル" prop="title">
        <el-input v-model="formData.title" placeholder="タイトルを入力してください"></el-input>
      </el-form-item>
      <el-form-item style="margin: 50px 0;" label="記事内容" prop="article">
        <el-input v-model="formData.article" type="textarea" rows="8"></el-input>
      </el-form-item>
      <el-form-item label="記事カテゴリ" prop="type">
        <el-select v-model="formData.type" placeholder="カテゴリを選択してください">
          <el-option v-for="item in article" :label="item.name" :value="item.type"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <el-form-item>
      <el-button @click="handlerCancel">キャンセル</el-button>
      <el-button type="primary" @click="handlerSave">保存</el-button>
    </el-form-item>
  </el-card>
</template>

<script>
import { defineComponent } from 'vue'
import { isUserOverdue } from '../../api/index'
export default defineComponent({
  name: 'AddNews'
})
</script>

<script setup>
import { getFindHeadlineByHid, saveOrAddNews, issueNews } from "../../api/index"
import { ref, onMounted } from "vue"
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter() 
const route = useRoute() 
const formRef = ref()

// バリデーションルール
const validateType = (rule, value, callback) => {
  if (value.length) {
    callback()
  } else {
    callback(new Error('記事カテゴリは必須です'))
  }
}
const validateArticle = (rule, value, callback) => {
  if (value.length) {
    callback()
  } else {
    callback(new Error('記事内容は必須です'))
  }
}
const validateTitle = (rule, value, callback) => {
  if (value.length) {
    callback()
  } else {
    callback(new Error('記事タイトルは必須です'))
  }
}
const newsRules = {
  title: [{ required: true, trigger: 'blur', validator: validateTitle }],
  article: [{ required: true, trigger: 'blur', validator: validateArticle }],
  type: [{ required: true, validator: validateType }],
}

// フォームデータ
const formData = ref({
  hid: null,
  title: "",
  article: "",
  type: ""
})

// 記事カテゴリの初期データ
const article = [
  { type: "1", name: "ニュース" },
  { type: "2", name: "スポーツ" },
  { type: "3", name: "エンタメ" },
  { type: "4", name: "テクノロジー" },
  { type: "5", name: "その他" }
]

// 編集の場合、HIDをもとにデータ取得して表示
const clickModifyEcho = async () => {
  if (!route.query.hid) return
  let result = await getFindHeadlineByHid(route.query.hid)
  formData.value.title = result.headline.title
  formData.value.article = result.headline.article
  formData.value.type = 
    result.headline.type === 1 ? "ニュース" :
    result.headline.type === 2 ? "スポーツ" :
    result.headline.type === 3 ? "エンタメ" :
    result.headline.type === 4 ? "テクノロジー" : "その他"
}

// ページマウント時にデータ取得
onMounted(() => {
  clickModifyEcho()
})

// 「キャンセル」ボタン処理
const handlerCancel = () => {
  router.back()
}

// 「保存」ボタン処理
const handlerSave = async () => {
  await formRef.value?.validate()
  await isUserOverdue()
  const Obj = { ...formData.value }

  // HIDは既存データまたは新規用
  Obj.hid = route.query.hid

  // カテゴリを文字列から数値へ変換
  if (Obj.type === "ニュース") Obj.type = "1"
  if (Obj.type === "スポーツ") Obj.type = "2"
  if (Obj.type === "エンタメ") Obj.type = "3"
  if (Obj.type === "テクノロジー") Obj.type = "4"
  if (Obj.type === "その他") Obj.type = "5"

  // 既存のニュースなら編集、なければ新規追加
  if (route.query.hid) {
    await saveOrAddNews(Obj)
    ElMessage.success("編集が成功しました")
  } else {
    await issueNews(formData.value)
    ElMessage.success("追加が成功しました")
  }
  router.push({ name: "HeadlineNews" })
}
</script>

<style lang="less" scoped>
.AddNewsContainer {
  width: 600px;
  margin: 150px auto;
}
</style>
