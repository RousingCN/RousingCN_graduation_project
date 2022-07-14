<template>
  <div style="flex: 1;margin: 20px;border: 1px solid var(--el-border-color);padding: 50px;border-radius: 20px">
    <el-page-header :content="module.moduleName" @back="this.$router.back()" style="margin-bottom: 30px;"/>
    <p style="color: #a1a1a1">{{ module.moduleInfo }}</p>
    <el-divider />
    <div>
      <el-button type="primary" @click="createArticle">发帖</el-button>
    </div>
    <div style="padding: 20px">
      <el-table :data="articles" :table-layout="'fixed'" @cell-click="click" :stripe="true">
        <el-table-column prop="artTitle" label="标题" width="200px" :fixed="'left'" :show-overflow-tooltip="true"/>
        <el-table-column prop="artContext" label="内容" :show-overflow-tooltip="true"/>
        <el-table-column prop="artCreate" label="创建时间" width="200px" :fixed="'right'" :align="'right'"/>
      </el-table>
      <div>
        <el-pagination style="margin-top: 20px" background hide-on-single-page layout="prev, pager, next"
                       :total="articleCount" @current-change="changPage"/>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import {ElMessage} from "element-plus";

export default {
  name: "ModuleInfo",
  data() {
    return {
      module: module,
      articles: [],
      articleCount: 0,
    };
  },
  created() {
    this.load(1);
  },
  methods: {
    changPage(pageNum) {
      this.load(pageNum)
    },
    load(pageNum) {
      this.module = JSON.parse(sessionStorage.getItem("module"));
      let module_id = this.module.moduleId
      request.post("/module/articles/" + pageNum, {
        moduleId: module_id
      }).then(res => {
        if (res.code === '1') {
          let rows = [];
          // 从时间截取日期
          for (let i = 0; i < (res.data.list.length <= 10 ? res.data.list.length : 10); i++) {
            const row = res.data.list[i];
            row.artCreate = row.artCreate.substring(0, 10);
            rows[i] = row;
          }
          this.articles = rows;
          this.articleCount = res.data.count
        } else {
          ElMessage.error(res.data);
        }
      });
    },
    click(data) {
      sessionStorage.setItem("article", JSON.stringify(data));
      console.log(data)
      this.$router.push("/articleInfo");
    },
    createArticle() {
      this.$router.push("/createArticle")
    }
  }
}
</script>

<style scoped>

</style>