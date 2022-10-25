<template>
  <div
      style="flex: 1;margin: 20px auto;border: 1px solid var(--el-border-color);padding: 50px;border-radius: 20px;max-width: 1500px">
    <h1>用户管理</h1>
    <el-divider/>
    <div style="margin: 100px auto;text-align: center;">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="用户id">
          <el-input v-model="formInline.userid" placeholder="0"/>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="formInline.username" placeholder="张三"/>
        </el-form-item>
        <el-form-item label="用户类型">
          <el-select v-model="formInline.userStatus" clearable placeholder="所有类型">
            <el-option label="普通用户" value="1"/>
            <el-option label="封禁用户" value="2"/>
            <el-option label="管理员" value="3"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="queryUser" :loading="tableLoading">查询用户</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div style="margin: 100px auto;text-align: center;max-width: 1000px">
      <el-table v-loading="tableLoading" :data="tableData" max-height="350" style="width: 100%" border :stripe="true"
                @cell-mouse-enter="getRowData" :default-sort="{ prop: 'userid', order: 'ascending' }">
        <el-table-column prop="userid" label="用户id" sortable/>
        <el-table-column prop="username" label="用户名" sortable/>
        <el-table-column prop="userCreate" label="用户注册时间" sortable>
          <template #default="scope">
            {{ scope.row.userCreate.substring(0, 10) + " " + scope.row.userCreate.substring(11, 19) }}
          </template>
        </el-table-column>
        <el-table-column prop="userStatus" label="用户状态" sortable/>
        <el-table-column fixed="right" label="操作" width="120">
          <template #default>
            <el-button link type="primary" size="small" @click="updateUserStatus">
              修改状态
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import {ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";

let selectRowData = null;

export default {
  name: "UserManagement",
  data() {
    return {
      formInline: {},
      tableData: [],
      tableLoading: ref(false)
    }
  },
  methods: {
    queryUser() {
      // 使表格与查询按钮处于加载状态
      this.tableLoading = ref(true);
      // 发送请求
      request.post("/api/admin/selectUser", this.formInline).then(res => {
        // 服务器是否返回空信息
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === '1') {
          // 保存数据
          this.tableData = res.data;

          ElMessage({
            message: '用户列表已更新',
            type: 'success',
          })
        } else {
          ElMessage.error(res.msg);
        }
        // 取消加载中状态
        this.tableLoading = ref(false)
      })
    },
    getRowData(rowData) {
      selectRowData = rowData;
    },
    updateUserStatus: function () {
      // 显示修改用户状态弹窗
      ElMessageBox.prompt('1：普通用户   2：封禁用户   3：管理员', selectRowData.userid + '号用户状态修改', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern:
            /^([1-3][1]*)$/,
        inputErrorMessage: '请输入数字1、2、3',
      })
          .then(({value}) => {
            // 发送请求
            request.put("/api/admin/updateUser", {
              userid: selectRowData.userid,
              username: selectRowData.username,
              userStatus: parseInt({value}.value)
            }).then(res => {
              // 服务器是否返回空信息
              if (res.code === undefined) {
                ElMessage.error("登录已过期，请重新登录后再试");
                this.$router.push('/')
              } else if (res.code === '1') {
                ElMessage({
                  type: 'success',
                  message: selectRowData.userid + `号用户的状态已经修改成功`,
                });
                // 重新查询数据
                this.queryUser()
              } else {
                ElMessage.error(res.msg)
              }
            })
          })
          .catch(() => {
            // 捕获异常并发出提醒
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