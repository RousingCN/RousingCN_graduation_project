<template>
  <div
      style="border: 1px solid #ccc;margin: 50px auto;padding: 50px;border-radius: 20px;width: 80%;height: 100%">
    <h1>浏览历史</h1>
    <el-divider/>
    <div style="margin: 100px auto;text-align: center;">
      <el-form :inline="true" :model="formInline">
        <el-form-item label="日期">
          <el-date-picker v-model="formInline.date" type="date" placeholder="默认从现在开始"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="queryView">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div></div>
  </div>
</template>

<script>
export default {
  name: "UserViewHistory",
  data(){
    return{
      formInline: {},
      loading: false,
      viewList: []
    }
  }
}
</script>

<style scoped>

</style>