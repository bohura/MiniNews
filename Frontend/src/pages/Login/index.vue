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
        <el-button type="primary" @click="toRegister">登録</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue"
import { useUserInfoStore } from '../../stores/userInfo';
import type { FormInstance } from 'element-plus';
import { useRouter } from 'vue-router'

const userInfoStore = useUserInfoStore()
const router = useRouter()
const formRef = ref<FormInstance>()
const loading = ref(false)
// アカウント・パスワードパラメータ
const loginForm = ref({
  username: "zhangsan",
  userPwd: ""
})
</script>
