<template>
  <div style="margin: 100px auto;width: 400px;border: 1px solid #ccc;padding: 50px;border-radius: 20px">
    <h1>用户注册</h1>
    <el-divider/>
    <el-form :model="form" label-width="70px" style="margin-top: 50px" :rules="rule" ref="formRef" status-icon>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" maxlength="16" autofocus/>
      </el-form-item>
      <el-form-item label="密码" prop="userPassword">
        <el-input v-model="form.userPassword" maxlength="20" show-password/>
      </el-form-item>
      <el-form-item label="邮箱" prop="userMail">
        <el-input v-model="form.userMail" maxlength="20"/>
      </el-form-item>
      <el-form-item label="手机号" prop="userPhone">
        <el-input v-model.number="form.userPhone" maxlength="11"/>
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
import {ElMessage} from 'element-plus'
import {reactive} from "vue";


export default {
  name: "Register",
  data() {
    return {
      form: {
        username: '',
        userPassword: '',
        userMail: '',
        userPhone: ''
      },
      rule: reactive({
        username: [
          {
            required: true, message: '用户名不能为空', trigger: 'blur'
          },
          {
            min: 4, max: 16, message: '用户名的长度为4-16位', trigger: 'change'
          },
          // 如果出现自动跳转至登陆界面问题，则取消验证用户名环节注释下面这三行
          // {
          //   asyncValidator: this.checkUsername, trigger: 'blur'
          // }
        ],
        userPassword: [
          {
            required: true, message: '密码不能为空', trigger: 'blur'
          },
          {
            min: 6, max: 20, message: '密码的长度为6-20位', trigger: 'change'
          }
        ],
        userMail: [
          {
            type: 'email', message: '邮箱格式不正确', trigger: 'change',
          },
          {
            required: true, message: '邮箱不能为空', trigger: 'blur'
          }
        ],
        userPhone: [
          {
            required: true, message: '手机号不能为空', trigger: 'blur'
          }
        ]
      })
    }
  },
  methods: {
    register() {
      this.$refs.formRef.validate((valid) => {
        // 表单验证是否全部通过
        if (!valid) {
          // 提示消息并取消注册
          this.$message.warning('验证不通过，请重新填写数据')
          return false
        }
        // 发送请求
        request.post("/user/register", this.form).then(res => {
          // 服务器是否返回空信息
          if (res.code === undefined) {
            ElMessage.error("页面停留时间过长，请刷新页面后再试");
            this.$router.push('/register')
          } else if (res.code === "1") {
            ElMessage({
              message: '注册成功',
              type: 'success',
            })
            // 注册完成跳转至登录
            this.$router.push("/");
          } else {
            ElMessage.error(res.msg);
          }
        });
      })
    },
    toLogin() {
      this.$router.push("/");
    },
    async checkUsername(rule, value, callback) {
      await request.post('/user/checkUsername', {username: value}).then(res => {
        ElMessage.error(res.code)
        if (res.code === '1') {
          callback();
        } else {
          callback(new Error('用户名已被使用'))
        }
      })
    }
  }
}
</script>

<style scoped>

</style>