<template>
  <div class="register-container">
    <el-form
      :model="registerForm"
      ref="formRef"
      label-width="80px"
      class="register-form"
      :rules="registerRules"
    >
      <h2>ユーザー登録</h2>
      <el-form-item label="氏名" prop="nickName">
        <el-input
          v-model="registerForm.nickName"
          autocomplete="off"
          ref="nickName"
          name="nickName"
          placeholder="氏名を入力してください"
        ></el-input>
      </el-form-item>
      <el-form-item label="ユーザー名" prop="username">
        <el-input
          v-model="registerForm.username"
          autocomplete="off"
          ref="username"
          name="username"
          placeholder="ユーザー名を入力してください"
        ></el-input>
      </el-form-item>
      <el-form-item label="パスワード" prop="userPwd">
        <el-input
          type="password"
          v-model="registerForm.userPwd"
          ref="userPwd"
          name="userPwd"
          autocomplete="off"
          placeholder="パスワードを入力してください"
        ></el-input>
      </el-form-item>
      <el-form-item label="パスワード確認" prop="confirmPassword">
        <el-input
          type="password"
          v-model="registerForm.confirmPassword"
          autocomplete="off"
          ref="confirmPassword"
          name="confirmPassword"
          placeholder="もう一度パスワードを入力してください"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="register">登録</el-button>
        <el-button type="danger" @click="resetForm">リセット</el-button>
        <el-button type="success" @click="goLogin">ログインへ</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
export default defineComponent({
  name: 'Register'
})
</script>

<script lang="ts" setup>
import { ref } from "vue"
import { useRouter } from 'vue-router'
import { ElMessage, FormInstance } from 'element-plus'
import { registerValidateApi, registerApi } from "../../api/index"

const router = useRouter()

// 登録フォームの初期値
const registerForm = ref({
  username: "",
  userPwd: "",
  confirmPassword: "",
  nickName: ''
})

const formRef = ref<FormInstance>()

// ユーザー名のバリデーション
const validateUsername = (rule: any, value: any, callback: any) => {
  if (value.length < 4) {
    callback(new Error('ユーザー名は4文字以上で入力してください'))
  } else {
    callback()
  }
}

// パスワードのバリデーション
const validatePasswo
