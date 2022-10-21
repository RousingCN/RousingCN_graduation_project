<template>
  <div
      style="border: 1px solid #ccc;margin: 50px auto;padding: 50px;border-radius: 20px;width: 600px;height: 100%;">
    <h1>板块创建</h1>
    <el-divider/>
    <el-form :model="form" label-width="80px" style="margin-top: 50px">
      <el-form-item label="板块名称" prop="name" style="width: 50%;">
        <el-input v-model="form.moduleName" clearable/>
      </el-form-item>
      <el-form-item label="板块介绍" style="width: 90%;margin: 50px 0">
        <el-input v-model="form.moduleInfo" type="textarea"/>
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
      request.post("/module/add", {
        moduleName: this.form.moduleName,
        moduleInfo: this.form.moduleInfo,
        moduleAuthor: JSON.parse(sessionStorage.getItem("user")),
      }).then(res => {
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === "1") {
          ElMessage({
            message: '板块创建成功',
            type: 'success',
          });
        } else {
          ElMessage.error(res.msg);
        }
      })
    },
  }
}
</script>

<style scoped>

</style>