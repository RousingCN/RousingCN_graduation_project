<template>
  <div
      style="border: 1px solid #ccc;margin: 50px auto;padding: 50px;border-radius: 20px;max-width: 800px;height: 100%">
    <h1>信息</h1>
    <el-form :model="form" label-width="120px" style="margin: 50px auto">
      <el-form-item label="用户ID" style="width: 60%;margin-bottom: 20px">
        <el-input v-model="form.userid" disabled/>
      </el-form-item>
      <el-form-item label="用户名" style="width: 60%;margin-bottom: 20px">
        <el-input v-model="form.username" disabled/>
      </el-form-item>
      <el-form-item label="手机号" style="width: 60%;margin-bottom: 20px">
        <el-input v-model="form.userPhone"/>
      </el-form-item>
      <el-form-item label="邮箱" style="width: 80%;margin-bottom: 20px">
        <el-input v-model="form.userMail"/>
      </el-form-item>
      <el-form-item label="生日">
        <el-col :span="11">
          <el-date-picker
              v-model="form.userBirth"
              type="date"
              placeholder="请选择日期"
              style="width: 100%"
          />
        </el-col>
      </el-form-item>
      <el-form-item label="注册时间">
        <el-col :span="11">
          <el-date-picker
              v-model="form.userCreate"
              type="date"
              placeholder="请选择日期"
              style="width: 100%"
              disabled
          />
        </el-col>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="form.userSex">
          <el-radio label="保密"/>
          <el-radio label="男"/>
          <el-radio label="女"/>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="个人签名" style="width: 90%">
        <el-input v-model="form.userinfo" type="textarea"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {ElMessage} from "element-plus";
import request from "@/utils/request";

const user = JSON.parse(sessionStorage.getItem("user"));

export default {
  name: 'UserInfo',
  data() {
    return {
      form: {
        userid: user.userid === null ? "" : user.userid,
        username: user.username === null ? "" : user.username,
        userPhone: user.userPhone === null ? "" : user.userPhone,
        userMail: user.userMail === null ? "" : user.userMail,
        userBirth: user.userBirth === null ? "" : user.userBirth,
        userCreate: user.userCreate === null ? "" : user.userCreate,
        userSex: user.userSex === null ? "" : user.userSex,
        userinfo: user.userinfo === null ? "" : user.userinfo
      }
    }
  },
  methods: {
    onSubmit() {
      request.put("/user/update", this.form).then(res => {
        if (res.code === "1") {
          ElMessage({
            message: '信息修改成功',
            type: 'success',
          });
          sessionStorage.setItem("user", JSON.stringify(res.data));
          this.reload();
        } else {
          ElMessage.error(res.msg);
        }
      })
    }
  },
}

</script>

<style scoped>

</style>