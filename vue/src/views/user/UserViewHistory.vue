<template>
  <div
      style="border: 1px solid #ccc;margin: 50px auto;padding: 50px;border-radius: 20px;width: 80%;height: 100%">
    <h1>浏览历史</h1>
    <el-divider/>
    <div style="margin: 50px auto 10px;text-align: center;">
      <el-form :inline="true" :model="formInline">
        <el-form-item label="浏览日期">
          <el-date-picker v-model="formInline.date" type="date" placeholder="全部记录" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="load">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-table :data="viewList" stripe style="width: 100%"
                :default-sort="{ prop: 'viewArticle.artId', order: 'descending' }" v-loading="loadingData"
                @cell-click="click">
        <el-table-column prop="viewArticle.artTitle" label="帖子标题" sortable>
          <template #default="scope">
            <el-link :underline="false">
              {{ scope.row.viewArticle.artTitle }}
            </el-link>
          </template>
        </el-table-column>
        <el-table-column prop="viewArticle.artAuthor.username" label="作者" :align="'center'" sortable>
          <template #default="scope">
            <el-popover :width="250" placement="top"
                        popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;">
              <template #reference>
                <el-link :underline="false">{{ scope.row.viewArticle.artAuthor.username }}</el-link>
              </template>
              <template #default>
                <div style="display: flex; gap: 16px; flex-direction: column;text-align: center">
                  <el-avatar :size="60" :src="scope.row.viewArticle.artAuthor.userAvatar"
                             style="margin: 0 auto 8px"/>
                  <div>
                    <p style="margin: 0; font-weight: bold">{{ scope.row.viewArticle.artAuthor.username }}</p>
                    <p style="margin: 0; font-size: 14px; color: var(--el-color-info)">
                      #{{ scope.row.viewArticle.artAuthor.userid }}</p>
                  </div>
                  <p style="margin: 0" v-if="scope.row.viewArticle.artAuthor.userinfo!==''">{{
                      scope.row.viewArticle.artAuthor.userinfo
                    }}</p>
                  <p style="margin: 0;color: #bebebe" v-if="scope.row.viewArticle.artAuthor.userinfo===''">
                    用户还没有设置个人签名</p>
                </div>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="viewTime" label="浏览日期" :align="'right'" sortable>
          <template #default="scope">
            {{ scope.row.viewTime.substring(0, 10) + " " + scope.row.viewTime.substring(11, 19) }}
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
  name: "UserViewHistory",
  data() {
    return {
      formInline: {},
      viewList: [],
      loadingData: true
    }
  },
  mounted() {
    setTimeout(() => {
      this.load()
    }, 1000)
  },
  methods: {
    load() {
      // 发送请求
      request.post('/history/view', {
        viewContext: this.formInline.context,
        viewCreate: this.formInline.date
      }).then(res => {
        // 服务器是否返回空信息
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === '1') {
          // 保存数据
          this.viewList = res.data
        } else {
          ElMessage.error(res.msg)
        }
        // 取消加载中状态
        this.loadingData = false;
      })
    },
    click(data) {
      sessionStorage.setItem("article", JSON.stringify(data.viewArticle));
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