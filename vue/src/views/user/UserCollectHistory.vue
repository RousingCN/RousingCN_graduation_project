<template>
  <div
      style="border: 1px solid #ccc;margin: 50px auto;padding: 50px;border-radius: 20px;width: 80%;height: 100%">
    <h1>收藏历史</h1>
    <el-divider/>
    <div>
      <el-table :data="collectList" stripe style="width: 100%"
                :default-sort="{ prop: 'colArticle.artId', order: 'descending' }" v-loading="loadingData" @cell-click="click">
        <el-table-column prop="colArticle.artId" label="帖子id" sortable/>
        <el-table-column prop="colArticle.artTitle" label="帖子标题" :align="'center'" sortable>
          <template #default="scope">
            <el-link :underline="false">
              {{ scope.row.colArticle.artTitle }}
            </el-link>
          </template>
        </el-table-column>
        <el-table-column prop="colArticle.artAuthor.username" label="作者" :align="'right'" sortable>
          <template #default="scope">
            <el-popover :width="250" placement="top"
                        popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;">
              <template #reference>
                <el-link :underline="false">{{ scope.row.colArticle.artAuthor.username }}</el-link>
              </template>
              <template #default>
                <div style="display: flex; gap: 16px; flex-direction: column;text-align: center">
                  <el-avatar :size="60" :src="scope.row.colArticle.artAuthor.userAvatar"
                             style="margin: 0 auto 8px"/>
                  <div>
                    <p style="margin: 0; font-weight: bold">{{ scope.row.colArticle.artAuthor.username }}</p>
                    <p style="margin: 0; font-size: 14px; color: var(--el-color-info)">
                      #{{ scope.row.colArticle.artAuthor.userid }}</p>
                  </div>
                  <p style="margin: 0" v-if="scope.row.colArticle.artAuthor.userinfo!==''">{{
                      scope.row.colArticle.artAuthor.userinfo
                    }}</p>
                  <p style="margin: 0;color: #bebebe" v-if="scope.row.colArticle.artAuthor.userinfo===''">
                    用户还没有设置个人签名</p>
                </div>
              </template>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import {ElMessage} from "element-plus";

export default {
  name: "UserCollectHistory",
  data() {
    return {
      collectList: [],
      loadingData: true
    };
  },
  mounted() {
    setTimeout(() => {
      this.load()
    }, 1000)
  },
  methods: {
    load() {
      request.post('/history/collect', {
        startIndex: this.collectList.length,
        endIndex: this.collectList.length + 10
      }).then(res => {
        // 服务器是否返回空信息
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === '1') {
          for (let i = 0; i < res.data.length; i++) {
            this.collectList.push(res.data[i]);
          }
        } else {
          ElMessage.error(res.msg)
        }
        this.loadingData = false;
      })
    },
    click(data) {
      sessionStorage.setItem("article", JSON.stringify(data.colArticle));
      const routerUrl = this.$router.resolve({
        path: '/articleInfo'
      });
      window.open(routerUrl.href, '_blank')
    },
  }
}
</script>

<style scoped>

</style>