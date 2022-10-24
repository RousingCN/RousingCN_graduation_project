<template>
  <div
      style="border: 1px solid #ccc;margin: 50px auto;padding: 50px;border-radius: 20px;width: 80%;height: 100%">
    <h1>评论历史</h1>
    <el-divider/>
    <div style="margin: 50px auto 10px;text-align: center;">
      <el-form :inline="true" :model="formInline">
        <el-form-item label="评论内容">
          <el-input v-model="formInline.context" type="textarea" autosize maxlength="250" placeholder="支持模糊关键字" />
        </el-form-item>
        <el-form-item label="评论日期">
          <el-date-picker v-model="formInline.date" type="date" placeholder="全部" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="load">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-table :data="commentList" stripe style="width: 100%"
                :default-sort="{ prop: 'comArticle.artId', order: 'descending' }" v-loading="loadingData"
                @cell-click="click">
        <el-table-column prop="comArticle.artTitle" label="帖子标题" sortable>
          <template #default="scope">
            <el-link :underline="false">
              {{ scope.row.comArticle.artTitle }}
            </el-link>
          </template>
        </el-table-column>
        <el-table-column prop="comArticle.artAuthor.username" label="作者" :align="'center'" sortable>
          <template #default="scope">
            <el-popover :width="250" placement="top"
                        popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;">
              <template #reference>
                <el-link :underline="false">{{ scope.row.comArticle.artAuthor.username }}</el-link>
              </template>
              <template #default>
                <div style="display: flex; gap: 16px; flex-direction: column;text-align: center">
                  <el-avatar :size="60" :src="scope.row.comArticle.artAuthor.userAvatar"
                             style="margin: 0 auto 8px"/>
                  <div>
                    <p style="margin: 0; font-weight: bold">{{ scope.row.comArticle.artAuthor.username }}</p>
                    <p style="margin: 0; font-size: 14px; color: var(--el-color-info)">
                      #{{ scope.row.comArticle.artAuthor.userid }}</p>
                  </div>
                  <p style="margin: 0" v-if="scope.row.comArticle.artAuthor.userinfo!==''">{{
                      scope.row.comArticle.artAuthor.userinfo
                    }}</p>
                  <p style="margin: 0;color: #bebebe" v-if="scope.row.comArticle.artAuthor.userinfo===''">
                    用户还没有设置个人签名</p>
                </div>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="" label="评论内容" :align="'center'">
          <template #default="scope">
            <el-popover :width="250" placement="top"
                        popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;">
              <template #reference>
                <el-link :underline="false" type="primary">
                  预览
                </el-link>
              </template>
              <template #default>
                <div style="display: flex; gap: 16px; flex-direction: column;text-align: center">
                  <p style="font-weight: bold">评论内容：</p>
                  <div style="margin-top: 10px">
                    {{ scope.row.comContext }}
                  </div>
                </div>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="comCreate" label="评论日期" :align="'right'" sortable>
          <template #default="scope">
            {{ scope.row.comCreate.substring(0, 10) + " " + scope.row.comCreate.substring(11, 19) }}
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
  name: "UserCommentHistory",
  data() {
    return {
      formInline: {},
      commentList: [],
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
      request.post('/history/comment', {
        comContext: this.formInline.context,
        comCreate: this.formInline.date
      }).then(res => {
        // 服务器是否返回空信息
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === '1') {
          // 保存数据
          this.commentList = res.data
        } else {
          ElMessage.error(res.msg)
        }
        this.loadingData = false;
      })
    },
    click(data) {
      sessionStorage.setItem("article", JSON.stringify(data.comArticle));
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