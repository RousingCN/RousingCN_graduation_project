<template>
  <div style="margin: 100px auto;width: 400px;border: 1px solid #ccc;padding: 50px;border-radius: 20px">
    <h1>用户登录</h1>
    <el-divider/>
    <el-form :model="form" label-width="60px" style="margin-top: 50px">
      <el-form-item label="用户名" prop="name" style="margin-bottom: 40px">
        <el-input v-model="form.username" clearable/>
      </el-form-item>
      <el-form-item label="密码" prop="pwd" style="margin-bottom: 40px">
        <el-input v-model="form.userPassword" clearable show-password/>
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
      request.post("/user/login", this.form).then(res => {
        if (res.code === "1") {
          if (res.data.userStatus === 1) {
            sessionStorage.setItem("user", JSON.stringify(res.data));

            request.post("/Achievement/user", {userid: res.data.userid}).then(re => {
              if (res.code === undefined) {
                ElMessage.error("登录已过期，请重新登录后再试");
                this.$router.push('/')
              } else if (re.code === "1") {
                sessionStorage.setItem("userAchievement", JSON.stringify(re.data));
              } else {
                sessionStorage.setItem("userAchievement", JSON.stringify({
                  fans: 0,
                  attention: 0,
                  collect: 0,
                  like: 0,
                  view: 0,
                  userid: -1
                }))
              }
            })

            ElMessage({
              message: '登录成功',
              type: 'success',
            })
            this.$router.push("/index");
          } else if (res.data.userStatus === 2) {
            ElMessage.error("该用户不可用");
          } else if (res.data.userStatus === 3) {
            sessionStorage.setItem("user", JSON.stringify(res.data));
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
      this.$router.push("/register");
    }
  }
}
</script>

<style scoped>

</style>