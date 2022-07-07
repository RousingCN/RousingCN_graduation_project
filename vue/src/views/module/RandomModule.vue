<template>
  <div style="flex: 1;margin: 20px;border: 1px solid var(--el-border-color);padding: 50px;border-radius: 20px">
    <h1>推荐板块</h1>
    <el-divider/>
    <div>
      <el-table :data="tableData" :table-layout="'fixed'">
        <el-table-column prop="moduleName" label="名称" width="300px"/>
        <el-table-column prop="moduleInfo" label="介绍" width=""/>
        <el-table-column prop="moduleAuthor.username" label="创建者" width="150px"/>
        <el-table-column prop="moduleCreate" label="创建时间" width="120px"/>
      </el-table>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "RecommendModule",
  data() {
    return {
      tableData: [],
    }
  },
  created() {
    this.load();
  },
  updated() {
  },
  methods: {
    load() {
      request.get("/module/random").then(res => {
        let rows = [];
        // 从时间截取日期
        for (let i = 0; i < res.data.length; i++) {
          const row = res.data[i];
          row.moduleCreate = row.moduleCreate.substring(0, 10);
          rows[i] = row;
        }
        this.tableData = rows;
      })
    },
  }
}
</script>

<style scoped>

</style>