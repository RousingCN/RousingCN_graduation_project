<template>
  <div>
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="筛选方式">
        <el-select v-model="formInline.type" placeholder="">
          <el-option label="Zone one" value="shanghai" />
          <el-option label="Zone two" value="beijing" />
        </el-select>
      </el-form-item>
      <el-form-item label="用户id">
        <el-input v-model="formInline.userid" placeholder="Approved by"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="queryUser">Query</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script >
import request from "@/utils/request";

export default {
  name: "ArticleManagement",
  data() {
    return {
      formInline: {
        userid: ''
      }
    }
  },
  methods: {
    queryUser() {
      request.post("/user/login", this.formInline).then(res => {

      })
    }
  }
}

</script>

<style scoped>

</style>