<template>
  <div
      style="border: 1px solid #ccc;margin: 50px auto;padding: 50px;border-radius: 20px;width: 80%;">
    <h1>点赞历史</h1>
    <el-divider/>
    <div>
      <el-table :data="likeList" stripe style="width: 100%"
                v-loading="loadingData" @cell-click="click">
        <el-table-column prop="likeArticle.artId" label="帖子id" sortable/>
        <el-table-column prop="likeArticle.artTitle" label="帖子标题" :align="'center'" sortable>
          <template #default="scope">
            <el-link :underline="false">
              {{ scope.row.likeArticle.artTitle }}
            </el-link>
          </template>
        </el-table-column>
        <el-table-column prop="likeArticle.artAuthor.username" label="作者" :align="'right'" sortable>
          <template #default="scope">
            <el-popover :width="250" placement="top"
                        popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;">
              <template #reference>
                <el-link :underline="false">{{ scope.row.likeArticle.artAuthor.username }}</el-link>
              </template>
              <template #default>
                <div style="display: flex; gap: 16px; flex-direction: column;text-align: center">
                  <el-avatar :size="60" :src="scope.row.likeArticle.artAuthor.userAvatar"
                             style="margin: 0 auto 8px"/>
                  <div>
                    <p style="margin: 0; font-weight: bold">{{ scope.row.likeArticle.artAuthor.username }}</p>
                    <p style="margin: 0; font-size: 14px; color: var(--el-color-info)">
                      #{{ scope.row.likeArticle.artAuthor.userid }}</p>
                  </div>
                  <p style="margin: 0" v-if="scope.row.likeArticle.artAuthor.userinfo!==''">{{
                      scope.row.likeArticle.artAuthor.userinfo
                    }}</p>
                  <p style="margin: 0;color: #bebebe" v-if="scope.row.likeArticle.artAuthor.userinfo===''">
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
import {ElMessage} from "element-plus";
import request from "@/utils/request";

export default {
  name: "UserLikeHistory",
  data() {
    return {
      likeList: [],
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
      // 发送请求
      request.post('/history/like', {
        startIndex: this.likeList.length,
        endIndex: this.likeList.length + 10
      }).then(res => {
        // 服务器是否返回空信息
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === '1') {
          // 保存数据
          this.likeList = res.data
        } else {
          ElMessage.error(res.msg)
        }
        // 取消加载状态
        this.loadingData = false;
      })
    },
    click(data) {
      sessionStorage.setItem("article", JSON.stringify(data.likeArticle));
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