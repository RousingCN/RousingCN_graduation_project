<template>
  <div
      style="margin: 20px auto;border: 1px solid var(--el-border-color);padding: 50px;border-radius: 20px;width: 80%;">
    <h1>全部板块</h1>
    <el-divider/>
    <div>
      <el-table :data="tableData" @cell-click="click"
                :default-sort="{ prop: 'moduleCreate', order: 'descending' }">
        <el-table-column prop="moduleName" label="名称" sortable/>
        <el-table-column prop="moduleInfo" label="介绍"/>
        <el-table-column prop="moduleAuthor.username" label="创建者" sortable>
          <template #default="scope">
            <el-popover :width="250"
                        popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;">
              <template #reference>
                <el-link :underline="false">{{ scope.row.moduleAuthor.username }}</el-link>
              </template>
              <template #default>
                <div style="display: flex; gap: 16px; flex-direction: column;text-align: center">
                  <el-avatar :size="60" :src="scope.row.moduleAuthor.userAvatar"
                             style="margin: 0 auto 8px"/>
                  <div>
                    <p style="margin: 0; font-weight: bold">{{ scope.row.moduleAuthor.username }}</p>
                    <p style="margin: 0; font-size: 14px; color: var(--el-color-info)">
                      #{{ scope.row.moduleAuthor.userid }}</p>
                  </div>
                  <p style="margin: 0" v-if="scope.row.moduleAuthor.userinfo!==''">{{
                      scope.row.moduleAuthor.userinfo
                    }}</p>
                  <p style="margin: 0;color: #bebebe" v-if="scope.row.moduleAuthor.userinfo===''">
                    用户还没有设置个人签名</p>
                </div>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="moduleCreate" label="创建时间" sortable/>
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
            row.moduleCreate = row.moduleCreate.substring(0, 10) + " " + row.moduleCreate.substring(11, 19);
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