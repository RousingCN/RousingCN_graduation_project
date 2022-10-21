<template>
  <div style="flex: 1;margin: 20px;border: 1px solid var(--el-border-color);padding: 50px;border-radius: 20px">
    <h1>全部板块</h1>
    <el-divider/>
    <div>
      <el-table :data="tableData" :table-layout="'fixed'" @cell-click="click"
                :default-sort="{ prop: 'moduleCreate', order: 'descending' }">
        <el-table-column prop="moduleName" label="名称" width="300px" sortable/>
        <el-table-column prop="moduleInfo" label="介绍" width=""/>
        <el-table-column prop="moduleAuthor.username" label="创建者" width="150px" sortable/>
        <el-table-column prop="moduleCreate" label="创建时间" width="120px" sortable/>
      </el-table>
      <el-pagination style="margin-top: 20px" background hide-on-single-page layout="prev, pager, next"
                     :total="moduleCount" @current-change="changPage"/>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import {ElMessage} from "element-plus";

export default {
  name: "Module",
  data() {
    return {
      tableData: [],
      moduleCount: 0,

    }
  },
  created() {
    this.load(1);
  },
  updated() {
  },
  methods: {
    load(pageNum) {
      request.get("/module/page/" + pageNum).then(res => {
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === '1') {
          let rows = [];
          // 从时间截取日期
          for (let i = 0; i < (res.data.pageData.length <= 10 ? res.data.pageData.length : 10); i++) {
            const row = res.data.pageData[i];
            row.moduleCreate = row.moduleCreate.substring(0, 10);
            rows[i] = row;
          }
          this.tableData = rows;
          this.moduleCount = res.data.count;
        } else {
          ElMessage.error(res.data);
        }
      });
    },
    changPage(pageNum) {
      this.load(pageNum)
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