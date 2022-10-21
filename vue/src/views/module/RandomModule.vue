<template>
  <div style="flex: 1;margin: 20px;border: 1px solid var(--el-border-color);padding: 50px;border-radius: 20px">
    <h1>发现板块</h1>
    <el-divider/>
    <div>
      <el-table :data="tableData" :table-layout="'fixed'" @cell-click="click">
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
import {ElMessage} from "element-plus";

export default {
  name: "RandomModule",
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
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === '1') {
          let rows = [];
          // 从时间截取日期
          for (let i = 0; i < res.data.length; i++) {
            if (res.data[i] === null) {
              continue;
            }
            const row = res.data[i];
            row.moduleCreate = row.moduleCreate.substring(0, 10);
            rows[i] = row;
          }
          this.tableData = rows;
        } else {
          ElMessage.error(res.msg)
        }
      })
    },
    click(a) {
      sessionStorage.setItem("module", JSON.stringify(a));
      this.$router.push("/moduleInfo");
    },
  }
}
</script>

<style scoped>

</style>