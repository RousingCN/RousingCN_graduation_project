<template>
  <div style="margin: 80px auto;border: 1px solid var(--el-border-color);padding: 50px;border-radius: 20px;width: 80%;">
    <el-page-header @back="this.$router.back()" style="margin-bottom: 30px;" :icon="ArrowLeft">
      <template #content>
        <span class="text-large font-600 mr-3" style="font-weight: bold"> {{ module.moduleName }} </span>
      </template>
    </el-page-header>

    <p style="color: #a1a1a1">{{ module.moduleInfo }}</p>
    <el-divider/>
    <div>
      <el-button type="primary" @click="createArticle">发帖</el-button>
    </div>
    <div style="padding: 20px">
      <el-table :data="articles" :table-layout="'fixed'" @cell-click="click" :stripe="true" v-loading="loadingData"
                :default-sort="{ prop: 'artCreate', order: 'descending' }">
        <el-table-column prop="artTitle" label="标题" sortable/>
        <el-table-column prop="artAuthor.username" label="作者" :align="'center'" sortable>
          <template #default="scope">
            <el-popover :width="250" placement="top"
                        popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;">
              <template #reference>
                <el-link :underline="false">{{ scope.row.artAuthor.username }}</el-link>
              </template>
              <template #default>
                <div style="display: flex; gap: 16px; flex-direction: column;text-align: center">
                  <el-avatar :size="60" :src="scope.row.artAuthor.userAvatar"
                             style="margin: 0 auto 8px"/>
                  <div>
                    <p style="margin: 0; font-weight: bold">{{ scope.row.artAuthor.username }}</p>
                    <p style="margin: 0; font-size: 14px; color: var(--el-color-info)">
                      #{{ scope.row.artAuthor.userid }}</p>
                  </div>
                  <p style="margin: 0" v-if="scope.row.artAuthor.userinfo!==''">{{ scope.row.artAuthor.userinfo }}</p>
                  <p style="margin: 0;color: #bebebe" v-if="scope.row.artAuthor.userinfo===''">
                    用户还没有设置个人签名</p>
                </div>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="artCreate" label="创建时间" :align="'right'" sortable>
          <template #default="scope">
            {{ scope.row.artCreate.substring(0, 10) + " " + scope.row.artCreate.substring(11, 19) }}
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top: 20px" background hide-on-single-page layout="prev, pager, next"
                     :total="articleCount" @current-change="changPage"/>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import {ArrowLeft} from "@element-plus/icons-vue";

export default {
  name: "ModuleInfo",
  data() {
    return {
      ArrowLeft: ArrowLeft,
      module: {},
      articles: [],
      articleCount: 0,
      loadingData:true
    };
  },
  mounted() {
    this.load(1);
  },
  methods: {
    changPage(pageNum) {
      this.loadingData = true;
      this.load(pageNum)
    },
    load(pageNum) {
      // 获取当前的板块id，用作请求参数
      this.module = JSON.parse(sessionStorage.getItem("module"));
      let module_id = this.module.moduleId
      // 发送请求
      request.post("/module/articles/" + pageNum, {
        moduleId: module_id
      }).then(res => {
        // 服务器是否返回空信息
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === '1') {
          // 保存信息
          this.articles = res.data.list;
          this.articleCount = res.data.count
        } else {
          ElMessage.error(res.data);
        }
        // 取消加载中状态
        this.loadingData = false;
      });
    },
    click(data) {
      sessionStorage.setItem("article", JSON.stringify(data));
      const routerUrl = this.$router.resolve({
        path: '/articleInfo'
      });
      window.open(routerUrl.href, '_blank')
    },
    createArticle() {
      this.$router.push("/createArticle")
    }
  }
}
</script>

<style scoped>

</style>