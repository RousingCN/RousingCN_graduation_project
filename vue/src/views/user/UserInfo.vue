<template>
  <div
      style="border: 1px solid #ccc;margin: 50px auto;padding: 50px;border-radius: 20px;width: 40%;height: 100%">
    <h1>修改信息</h1>
    <el-divider/>
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
              :clearable="false"
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
        <el-input v-model="form.userinfo" type="textarea" maxlength="255" autosize show-word-limit/>
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


export default {
  name: 'UserInfo',
  data() {
    return {
      form: {}
    }
  },
  methods: {
    load() {
      const user = JSON.parse(sessionStorage.getItem("user"));
      this.form.userid = user.userid === null ? "" : user.userid;
      this.form.username = user.username === null ? "" : user.username;
      this.form.userPhone = user.userPhone === null ? "" : user.userPhone;
      this.form.userMail = user.userMail === null ? "" : user.userMail;
      this.form.userBirth = user.userBirth === null ? "" : user.userBirth;
      this.form.userCreate = user.userCreate === null ? "" : user.userCreate;
      this.form.userSex = user.userSex === null ? "" : user.userSex;
      this.form.userinfo = user.userinfo === null ? "" : user.userinfo;
    },
    onSubmit() {
      request.put("/user/update", this.form).then(res => {
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === "1") {
          ElMessage({
            message: '信息修改成功',
            type: 'success',
          });
          sessionStorage.setItem("user", JSON.stringify(res.data));
        } else {
          ElMessage.error(res.msg);
        }
      })
    },
  },
  created() {
    this.load();
  }
}

</script>

<style scoped>

</style>