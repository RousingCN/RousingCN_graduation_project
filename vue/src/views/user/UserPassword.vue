<template>
  <div style="border: 1px solid #ccc;margin: 50px auto;padding: 50px;border-radius: 20px;max-width: 600px;height: 100%">
    <h1>密码</h1>
    <el-form :model="form" label-width="120px" style="margin: 50px auto" :rules="rules" status-icon>
      <el-form-item label="旧密码" style="width: 80%;margin-bottom: 40px" prop="password">
        <el-input v-model="form.password" show-password/>
      </el-form-item>
      <el-form-item label="新密码" style="width: 80%;margin-bottom: 40px" prop="newPassword">
        <el-input v-model="form.newPassword" show-password/>
      </el-form-item>
      <el-form-item label="新密码" style="width: 80%;margin-bottom: 40px" prop="checkNewPassword">
        <el-input v-model="form.checkNewPassword" show-password/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import request from "@/utils/request";

let oldPwd = '';
let newPwd = '';
let input1 = false;
let input2 = false;
let input3 = false;


const checkOld = (rule, value, callback) => {
  input1 = false;
  if (!value) {
    return callback(new Error('请输入旧密码'));
  } else {
    if (value.length >= 6 && value.length <= 15) {
      oldPwd = value;
      input1 = true;
      callback();
    } else {
      callback(new Error('密码格式为6-15位，大小写字母、数字或字符'))
    }
  }
}
const checkNew = (rule, value, callback) => {
  input2 = false;
  if (!value) {
    return callback(new Error('请输入新密码'));
  } else if (value === oldPwd) {
    return callback(new Error('新密码不能与旧密码一致'));
  } else {
    if (value.length >= 6 && value.length <= 15) {
      newPwd = value;
      input2 = true;
      callback();
    } else {
      callback(new Error('密码格式为6-15位，大小写字母、数字或字符'))
    }
  }

}

const checkNewAgain = (rule, value, callback) => {
  input3 = false;
  if (!value) {
    return callback(new Error('请再次输入新密码'))
  } else if (value === oldPwd) {
    return callback(new Error('新密码不能与旧密码一致'));
  } else {
    if (!value === newPwd) {
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
      form: {},
      rules: reactive({
        password: [{validator: checkOld, trigger: 'blur'}],
        newPassword: [{validator: checkNew, trigger: 'blur'}],
        checkNewPassword: [{validator: checkNewAgain, trigger: 'blur'}],
      })
    }
  },
  methods: {
    onSubmit() {
      console.log(this.form.password);
      console.log(this.form.newPassword);
      console.log(JSON.parse(sessionStorage.getItem("user")).userid);
      if (input1 && input2 && input3) {
        request.put("/user/update/password", {
          oldPassword: this.form.password,
          newPassword: this.form.newPassword,
          userid: JSON.parse(sessionStorage.getItem("user")).userid
        }).then(res => {
          if (res.code === '1') {
            ElMessage({
              message: '密码修改成功,请重新登录',
              type: 'success',
            });
            sessionStorage.clear()
            this.$router.push("/login")
          } else {
            ElMessage.error(res.msg);
          }
        })
      } else {
        ElMessage.error("请重新检查输入的数据");
      }
    }
  }
}
</script>

<style scoped>

</style>