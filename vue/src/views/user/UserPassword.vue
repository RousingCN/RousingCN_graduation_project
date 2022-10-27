<template>
  <div style="border: 1px solid #ccc;margin: 50px auto;padding: 50px;border-radius: 20px;width: 40%;height: 100%">
    <h1>修改密码</h1>
    <el-divider/>
    <el-form :model="form" label-width="120px" style="margin: 50px auto" :rules="rules" ref="formRef" status-icon>
      <el-form-item label="旧密码" style="width: 80%;margin-bottom: 40px" prop="password">
        <el-input v-model="form.password" show-password maxlength="20"/>
      </el-form-item>
      <el-form-item label="新密码" style="width: 80%;margin-bottom: 40px" prop="newPassword">
        <el-input v-model="form.newPassword" show-password maxlength="20"/>
      </el-form-item>
      <el-form-item label="新密码" style="width: 80%;margin-bottom: 40px" prop="checkNewPassword">
        <el-input v-model="form.checkNewPassword" show-password maxlength="20"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {reactive} from "vue";
import {ElMessage} from "element-plus";
import request from "@/utils/request";

let oldPwd = '';
let newPwd1 = '';
let newPwd2 = '';
let input1 = false;
let input2 = false;
let input3 = false;


const checkOld = (rule, value, callback) => {
  input1 = false;
  if (!value) {
    callback(new Error('请输入旧密码'));
  } else {
    if (value.length >= 6 && value.length <= 20) {
      oldPwd = value;
      input1 = true;
      callback();
    } else {
      callback(new Error('密码格式为6-20位，大小写字母、数字或字符'))
    }
  }
}
const checkNew = (rule, value, callback) => {
  input2 = false;
  newPwd1 = value;
  if (!value) {
    callback(new Error('请输入新密码'));
  } else if (value === oldPwd) {
    callback(new Error('新密码不能与旧密码一致'));
  } else if (value !== newPwd2 && newPwd2 !== '') {
    callback(new Error('两次输入的新密码不一致'))
  } else {
    if (value.length >= 6 && value.length <= 20) {
      input2 = true;
      callback();
    } else {
      callback(new Error('密码格式为6-20位，大小写字母、数字或字符'))
    }
  }
}

const checkNewAgain = (rule, value, callback) => {
  newPwd2 = value;
  input3 = false;
  if (!value) {
    callback(new Error('请再次输入新密码'))
  } else if (value === oldPwd) {
    callback(new Error('新密码不能与旧密码一致'));
  } else {
    if (value !== newPwd1 && newPwd1 !== '') {
      callback(new Error('两次输入的新密码不一致'))
    } else {
      input3 = true;
      callback()
    }
  }
}

export default {
  name: "UserPwd",
  data() {
    return {
      form: {
        password: '',
        newPassword: '',
        checkNewPassword: ''
      },
      rules: reactive({
        password: [{validator: checkOld, trigger: 'blur'}],
        newPassword: [{validator: checkNew, trigger: 'blur'}],
        checkNewPassword: [{validator: checkNewAgain, trigger: 'blur'}],
      })
    }
  },
  methods: {
    onSubmit() {
      this.$refs.formRef.validate((valid) => {
        // 表单验证是否全部通过
        if (!valid) {
          // 提示消息并取消注册
          this.$message.warning('验证不通过，请重新填写数据')
          return false
        }
        // 三个验证全部通过才允许发起请求
        if (input1 && input2 && input3) {
          // 发送请求
          request.put("/user/update/password", {
            oldPassword: this.form.password,
            newPassword: this.form.newPassword,
            userid: JSON.parse(sessionStorage.getItem("user")).userid
          }).then(res => {
            // 服务器是否返回空信息
            if (res.code === undefined) {
              ElMessage.error("登录已过期，请重新登录后再试");
              this.$router.push('/')
            } else if (res.code === '1') {
              ElMessage({
                message: '密码修改成功,请重新登录',
                type: 'success',
              });
              // 修改密码后会清除前端里所有保存的数据，并需要重新登陆
              sessionStorage.clear()
              this.$router.push("/")
            } else {
              ElMessage.error(res.msg);
            }
          })
        } else {
          ElMessage.error("请重新检查输入的数据");
        }
      })
    }
  }
}
</script>

<style scoped>

</style>