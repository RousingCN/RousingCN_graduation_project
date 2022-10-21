<template>
  <div style="margin: 100px auto;width: 400px;border: 1px solid #ccc;padding: 50px;border-radius: 20px">
    <h1>用户注册</h1>
    <el-divider/>
    <el-form :model="form" label-width="60px" style="margin-top: 50px">
      <el-form-item label="用户名" prop="name">
        <el-input v-model="form.username" clearable/>
      </el-form-item>
      <el-form-item label="密码" prop="pwd">
        <el-input v-model="form.userPassword" clearable show-password/>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.userMail" clearable/>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.userPhone" clearable/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="register">注册</el-button>
        <el-button @click="toLogin">返回登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";
import {reactive} from "vue";
import {ElMessage} from 'element-plus'

const form = reactive({
  name: '',
  region: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
})

export default {
  name: "Register",
  data() {
    return {
      form: {}
    }
  },
  methods: {
    register() {
      console.log(this.form)
      request.post("/user/register", this.form).then(res => {
        if (res.code === undefined) {
          ElMessage.error("页面停留时间过长，请刷新页面后再试");
          this.$router.push('/register')
        } else if (res.code === "1") {
          ElMessage({
            message: '注册成功',
            type: 'success',
          })
          this.$router.push("/");
        } else {
          ElMessage.error(res.msg);
        }

      })
    },
    toLogin() {
      this.$router.push("/");
    }
  }
}
</script>

<style scoped>

</style>