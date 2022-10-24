<template>
  <div
      style="flex: 1;margin: 20px auto;border: 1px solid var(--el-border-color);padding: 50px;border-radius: 20px;max-width: 1500px">
    <h1>评论管理</h1>
    <el-divider/>
    <div style="margin: 100px auto;text-align: center;">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="评论id">
          <el-input v-model="formInline.comId" placeholder="0"/>
        </el-form-item>
        <el-form-item label="评论用户id">
          <el-input v-model="formInline.comUserId" placeholder="0"/>
        </el-form-item>
        <el-form-item label="帖子id">
          <el-input v-model="formInline.comArticle" placeholder="0"/>
        </el-form-item>
        <el-form-item label="评论状态">
          <el-select v-model="formInline.comStatus" clearable placeholder="所有类型">
            <el-option label="正常" value="1"/>
            <el-option label="禁用" value="2"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="queryComment">查询评论</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div style="margin: 100px auto;text-align: center;max-width: 1300px">
      <el-table v-loading="tableLoading" :data="tableData" max-height="350" style="width: 100%" border :stripe="true"
                @cell-mouse-enter="getRowData" :default-sort="{ prop: 'comId', order: 'ascending' }">
        <el-table-column prop="comId" label="评论id" sortable/>
        <el-table-column prop="comUser.username" label="评论用户" sortable>
          <template #default="scope">
            <el-popover :width="250" placement="top"
                        popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;">
              <template #reference>
                <el-link :underline="false">{{ scope.row.comUser.username }}</el-link>
              </template>
              <template #default>
                <div style="display: flex; gap: 16px; flex-direction: column;text-align: center">
                  <el-avatar :size="60" :src="scope.row.comUser.userAvatar"
                             style="margin: 0 auto 8px"/>
                  <div>
                    <p style="margin: 0; font-weight: bold">{{scope.row.comUser.username}}</p>
                    <p style="margin: 0; font-size: 14px; color: var(--el-color-info)">
                      #{{ scope.row.comUser.userid }}</p>
                  </div>
                  <p style="margin: 0" v-if="scope.row.comUser.userinfo!==''">{{ scope.row.comUser.userinfo }}</p>
                  <p style="margin: 0;color: #bebebe" v-if="scope.row.comUser.userinfo===''">
                    用户还没有设置个人签名</p>
                </div>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="comArticle" label="帖子" sortable/>
        <el-table-column prop="comCreate" label="评论时间" sortable/>
        <el-table-column prop="comStatus" label="评论状态" sortable/>
        <el-table-column fixed="right" label="操作">
          <template #default>
            <el-button link type="primary" size="small" @click="updateCommentStatus">
              修改状态
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>

import {ref} from "vue";
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

let selectRowData = null;

export default {
  name: "CommentManagement",
  data() {
    return {
      formInline: {comUser: {}},
      tableData: [],
      tableLoading: ref(false)
    }
  },
  methods: {
    queryComment() {
      this.tableLoading = ref(true);
      request.post("/api/admin/selectComment", {
        comId: this.formInline.comId,
        comUser: {userid: this.formInline.comUserId},
        comArticle: this.formInline.comArticle,
        comStatus: this.formInline.comStatus
      }).then(res => {
        // 服务器是否返回空信息
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === '1') {
          const resData = res.data;
          for (let i = 0; i < resData.length; i++) {
            resData[i].comCreate = resData[i].comCreate.substring(0, 10) + " " + resData[i].comCreate.substring(11, 19)
          }
          this.tableData = resData;

          ElMessage({
            message: '评论列表已更新',
            type: 'success',
          })
        } else {
          ElMessage.error(res.msg);
        }
        this.tableLoading = ref(false)
      })
    },
    getRowData(rowData) {
      selectRowData = rowData;
    },
    updateCommentStatus: function () {
      ElMessageBox.prompt('1：正常   2：禁用', selectRowData.comId + '号评论状态修改', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern:
            /^([1-2][1]*)$/,
        inputErrorMessage: '请输入数字1、2',
      })
          .then(({value}) => {
            request.put("/api/admin/updateComment", {
              comId: selectRowData.comId,
              comUser: selectRowData.comUser,
              comArticle: selectRowData.comArticle,
              comStatus: parseInt({value}.value)
            }).then(res => {
              // 服务器是否返回空信息
              if (res.code === undefined) {
                ElMessage.error("登录已过期，请重新登录后再试");
                this.$router.push('/')
              } else if (res.code === '1') {
                ElMessage({
                  type: 'success',
                  message: selectRowData.comId + `号评论的状态已经修改成功`,
                });
                this.queryComment()
              } else {
                ElMessage.error(res.msg)
              }
            })

          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '取消',
            })
          })
    },
  }
}
</script>

<style scoped>

</style>