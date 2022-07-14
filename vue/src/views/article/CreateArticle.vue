<template>
  <div
      style="border: 1px solid #ccc;margin: 50px auto;padding: 50px;border-radius: 20px;max-width: 1200px;height: 100%;flex: 1">
    <h1>创建帖子</h1>
    <el-divider/>
    <el-form label-width="80px" style="margin-top: 50px">
      <el-form-item label="所属板块" prop="name" style="width: 30%;margin-bottom: 30px">
        <el-input v-model="moduleName" disabled/>
      </el-form-item>
      <el-form-item label="帖子标题" prop="name" style="width: 50%;margin-bottom: 30px">
        <el-input v-model="artTitle" clearable/>
      </el-form-item>
      <el-form-item label="帖子内容" prop="name" style="width: 90%;margin-bottom: 30px">
        <el-input v-model="artContext" type="textarea"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="create">发帖</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";
import {ElMessage} from "element-plus";

export default {
  name: "CreateArticle",
  data() {
    return {
      moduleName: JSON.parse(sessionStorage.getItem('module')).moduleName,
      artTitle: "",
      artContext: "",
    };
  },
  methods: {
    create() {
      request.post("/article/createArticle", {
        artTitle: this.artTitle,
        artContext: this.artContext,
        artAuthor: JSON.parse(sessionStorage.getItem("user")),
        artModule: JSON.parse(sessionStorage.getItem('module')).moduleId
      }).then(res => {
        if (res.code === '1') {
          ElMessage({
            message: '发帖成功',
            type: 'success',
          });
          this.$router.back();
        } else {
          ElMessage.error(res.msg);
        }
      })
    },
  },
}
</script>

<style scoped>

</style>