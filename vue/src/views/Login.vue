<template>
  <div style="margin: 100px auto;width: 400px;border: 1px solid #ccc;padding: 50px;border-radius: 20px">
    <h1>用户登录</h1>
    <el-divider/>
    <el-form :model="form" label-width="60px" style="margin-top: 50px">
      <el-form-item label="用户名" prop="name" style="margin-bottom: 40px">
        <el-input v-model="form.username" maxlength="16" clearable/>
      </el-form-item>
      <el-form-item label="密码" prop="pwd" style="margin-bottom: 40px">
        <el-input v-model="form.userPassword" maxlength="20" clearable show-password/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login">登录</el-button>
        <el-button @click="toRegister">前往注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import request from "@/utils/request";
import {ElMessage} from "element-plus";


export default {
  name: "Login",
  data() {
    return {
      form: {
        username: '',
        userPassword: ''
      }
    }
  },
  methods: {
    login() {
      // 发送post请求
      request.post("/user/login", this.form).then(res => {
        // 接收数据后进行处理
        if (res.code === "1") {
          // 判断用户状态
          if (res.data.userStatus === 1) {
            // 保存用户信息
            sessionStorage.setItem("user", JSON.stringify(res.data));
            // 提示
            ElMessage({
              message: '登录成功',
              type: 'success',
            })
            // 跳转至首页
            this.$router.push("/index");
          } else if (res.data.userStatus === 2) {
            ElMessage.error("该用户不可用");
          } else if (res.data.userStatus === 3) {
            sessionStorage.setItem("user", JSON.stringify(res.data));
            // 跳转至管理员首页
            this.$router.push("/admin/userManagement");
          } else {
            ElMessage.error("该账户状态异常，请尝试联系管理员");
          }
        } else {
          ElMessage.error(res.msg);
        }
      })
    },
    toRegister() {
      // 跳转到注册
      this.$router.push("/register");
    }
  }
}
</script>

<style scoped>

</style>