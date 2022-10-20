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
          <el-input v-model="formInline.comUser" placeholder="0"/>
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
                @cell-mouse-enter="getRowData">
        <el-table-column prop="comId" label="评论id"/>
        <el-table-column prop="comUser" label="评论用户"/>
        <el-table-column prop="comArticle" label="帖子"/>
        <el-table-column prop="comCreate" label="评论时间"/>
        <el-table-column prop="comStatus" label="评论状态"/>
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
      formInline: {},
      tableData: [],
      tableLoading: ref(false)
    }
  },
  methods: {
    queryComment() {
      this.tableLoading = ref(true);
      request.post("/api/admin/selectComment", {
        comId: this.formInline.comId,
        comUser: this.formInline.comUser,
        comArticle: this.formInline.comArticle,
        comStatus: this.formInline.comStatus
      }).then(res => {
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        }
        if (res.code === '1') {
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
              if (res.code === undefined) {
                ElMessage.error("登录已过期，请重新登录后再试");
                this.$router.push('/')
              }
              if (res.code === '1') {
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