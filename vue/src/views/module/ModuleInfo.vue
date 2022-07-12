<template>
  <div style="flex: 1;margin: 20px;border: 1px solid var(--el-border-color);padding: 50px;border-radius: 20px">
    <h1>{{ module.moduleName }}</h1>
    <el-divider border-style="dashed" style="margin: 10px"/>
    <p style="color: #494949">{{ module.moduleInfo }}</p>
    <el-divider/>
    <div style="padding: 20px">
      <div v-for="item in 15" :key="item">
        {{ articles[item] }}
      </div>
      <div v-if="articles.length===0" style="text-align: center;color: #9d9d9d"><h3>暂无帖子</h3></div>
      <div>
        <el-pagination style="margin-top: 20px" background hide-on-single-page layout="prev, pager, next"
                       :total="moduleCount" @current-change="changPage"/>
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
      moduleCount: 0,
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
      request.post("/article", {
        pageNum: pageNum,
        moduleId: this.module.moduleId
      }).then(res => {
        if (res.code === '1') {
          this.articles = res.data;
          console.log(this.articles)
        } else {
          ElMessage.error(res.data);
        }
      });
    },
  }
}
</script>

<style scoped>

</style>