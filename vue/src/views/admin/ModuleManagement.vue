<template>
  <div
      style="flex: 1;margin: 20px auto;border: 1px solid var(--el-border-color);padding: 50px;border-radius: 20px;max-width: 1500px">
    <h1>模块管理</h1>
    <el-divider/>
    <div style="margin: 100px auto;text-align: center;">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="模块id">
          <el-input v-model="formInline.moduleId" placeholder="0"/>
        </el-form-item>
        <el-form-item label="模块名称">
          <el-input v-model="formInline.moduleName" placeholder="官方公告"/>
        </el-form-item>
        <el-form-item label="模块作者id">
          <el-input v-model="formInline.moduleName" placeholder="0"/>
        </el-form-item>
        <el-form-item label="模块状态">
          <el-select v-model="formInline.moduleStatus" clearable placeholder="所有类型">
            <el-option label="正常" value="1"/>
            <el-option label="禁用" value="2"/>
            <el-option label="官方" value="3"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="queryUser">查询模块</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div style="margin: 100px auto;text-align: center;max-width: 1000px">
      <el-table v-loading="tableLoading" :data="tableData" max-height="350" style="width: 100%" border :stripe="true"
                @cell-mouse-enter="getRowData">
        <el-table-column prop="moduleId" label="模块id"/>
        <el-table-column prop="moduleName" label="模块名称"/>
        <el-table-column prop="moduleAuthor" label="创建者"/>
        <el-table-column prop="moduleCreate" label="创建时间"/>
        <el-table-column prop="moduleStatus" label="模块状态"/>
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
import {ref} from "vue";
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

let selectRowData = null;

export default {
  name: "ModuleManagement",
  data() {
    return {
      formInline: {},
      tableData: [],
      tableLoading: ref(false)
    }
  },
  methods: {
    queryUser() {
      this.tableLoading = ref(true);
      request.post("/api/admin/selectModule", this.formInline).then(res => {
        if (res.code === '1') {
          const resData = res.data;
          for (let i = 0; i < resData.length; i++) {
            resData[i].userCreate = resData[i].userCreate.substring(0, 10) + " " + resData[i].userCreate.substring(11, 19)
          }
          this.tableData = resData;

          ElMessage({
            message: '用户列表已更新',
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
    updateUserStatus: function () {
      ElMessageBox.prompt('1：普通用户   \n2：封禁用户   \n3：管理员', selectRowData.userid + '号用户状态修改', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern:
            /^([1-3][1]*)$/,
        inputErrorMessage: '请输入数字1、2、3',
      })
          .then(({value}) => {
            request.put("/api/admin/updateModule", {
              userid: selectRowData.userid,
              username: selectRowData.username,
              userStatus: parseInt({value}.value)
            }).then(res => {
              console.log(res)
              if (res.code === '1') {
                ElMessage({
                  type: 'success',
                  message: selectRowData.userid + `号用户的状态已经修改成功`,
                });
                this.queryUser()
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