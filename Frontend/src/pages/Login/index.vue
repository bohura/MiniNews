<template>
  <div class="login-container">
    <el-form
      :model="loginForm"
      ref="formRef"
      label-width="80px"
      class="login-form"
      :rules="loginRules"
    >
      <h2>ユーザーログイン</h2>
      <el-form-item label="ユーザー名" prop="username">
        <el-input
          v-model="loginForm.username"
          ref="username"
          name="username"
          autocomplete="off"
          placeholder="ユーザー名を入力してください"
        ></el-input>
      </el-form-item>
      <el-form-item label="パスワード" prop="userPwd">
        <el-input
          type="password"
          v-model="loginForm.userPwd"
          autocomplete="off"
          placeholder="パスワードを入力してください"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click.native.prevent="login">ログイン</el-button>
        <el-button type="primary" @click="toRegister">新規登録</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({
  name: 'Login'
})
</script>

<script lang="ts" setup>
import { ref } from "vue"
import { useUserInfoStore } from '../../stores/userInfo'

import type { FormInstance } from 'element-plus'
import { useRouter } from 'vue-router'

const userInfoStore = useUserInfoStore()
const router = useRouter()
const formRef = ref<FormInstance>()
const loading = ref(false)

// アカウントとパスワードのパラメータ
const loginForm = ref({
  username: "zhangsan",
  userPwd: "123456",
})

// ユーザー名のバリデーションルール
const validateUsername = (rule: any, value: any, callback: any) => {
  if (value.length < 4) {
    callback(new Error('ユーザー名は4文字以上で入力してください'))
  } else {
    callback()
  }
}

// パスワードのバリデーションルール
const validatePassword = (rule: any, value: any, callback: any) => {
  if (value.length < 6) {
    callback(new Error('パスワードは6文字以上で入力してください'))
  } else {
    callback()
  }
}

// フォームバリデーションルール定義
const loginRules = {
  username: [{ required: true, validator: validateUsername }],
  userPwd: [{ required: true, trigger: 'blur', validator: validatePassword }]
}

// 「ログイン」ボタンの処理
const login = async () => {
  await formRef.value?.validate()
  loading.value = true
  try {
    await userInfoStore.login(loginForm.value)
    router.push({ name: "HeadlineNews" })
  } finally {
    loading.value = false
  }
}

// 「新規登録」ボタンの処理
const toRegister = () => {
  router.push({ name: "Register" })
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
.login-form {
  width: 400px;
  text-align: center;
}
</style>
