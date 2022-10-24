<template>
  <div
      style="border: 1px solid #ccc;margin: 80px auto;padding: 50px;border-radius: 20px;width: 600px;height: 100%;">
    <h1>创建板块</h1>
    <el-divider/>
    <el-form :model="form" label-width="80px" style="margin-top: 50px">
      <el-form-item label="板块名称" prop="name" style="width: 70%;">
        <el-input v-model="form.moduleName" clearable maxlength="10" show-word-limit/>
      </el-form-item>
      <el-form-item label="板块介绍" style="width: 90%;margin: 50px 0">
        <el-input v-model="form.moduleInfo" type="textarea" maxlength="50" placeholder="向大家介绍一下你的板块吧" autosize
                  show-word-limit/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="createModule">创建</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";
import {ElMessage} from "element-plus";

export default {
  name: "CreateModule",
  data() {
    return {
      form: {},
    }
  },
  methods: {
    createModule() {
      // 输入框的简单校验
      if (this.form.moduleName === '' || this.form.moduleName === undefined || this.form.moduleInfo === '' || this.form.moduleInfo === undefined) {
        ElMessage.error('板块名称和板块介绍不能为空')
        return;
      }
      // 发送请求
      request.post("/module/add", {
        moduleName: this.form.moduleName,
        moduleInfo: this.form.moduleInfo,
        moduleAuthor: JSON.parse(sessionStorage.getItem("user")),
      }).then(res => {
        // 服务器是否返回空信息
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          // 返回登录界面
          this.$router.push('/')
        } else if (res.code === "1") {
          ElMessage({
            message: '板块创建成功',
            type: 'success',
          });
        } else {
          ElMessage.error(res.msg);
        }
      });
    },
  }
}
</script>

<style scoped>

</style>