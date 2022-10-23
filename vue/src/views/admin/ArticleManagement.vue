<template>
  <div
      style="flex: 1;margin: 20px auto;border: 1px solid var(--el-border-color);padding: 50px;border-radius: 20px;max-width: 1500px">
    <h1>帖子管理</h1>
    <el-divider/>
    <div style="margin: 100px auto;text-align: center;">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="帖子id">
          <el-input v-model="formInline.artId" placeholder="0"/>
        </el-form-item>
        <el-form-item label="帖子标题">
          <el-input v-model="formInline.artTitle" placeholder="官方贴"/>
        </el-form-item>
        <el-form-item label="创建者id">
          <el-input v-model="formInline.artAuthor" placeholder="0"/>
        </el-form-item>
        <el-form-item label="帖子状态">
          <el-select v-model="formInline.artStatus" clearable placeholder="所有类型">
            <el-option label="正常" value="1"/>
            <el-option label="禁用" value="2"/>
            <el-option label="置顶" value="3"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="queryArticle">查询帖子</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div style="margin: 100px auto;text-align: center;max-width: 1300px">
      <el-table v-loading="tableLoading" :data="tableData" max-height="350" style="width: 100%" border :stripe="true"
                @cell-mouse-enter="getRowData" :default-sort="{ prop: 'artId', order: 'ascending' }">
        <el-table-column prop="artId" label="帖子id" sortable/>
        <el-table-column prop="artTitle" label="帖子标题" sortable/>
        <el-table-column prop="artAuthor.username" label="创建者" sortable>
          <template #default="scope">
            <el-popover :width="250"
                        popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;">
              <template #reference>
                <el-link :underline="false">{{ scope.row.artAuthor.username }}</el-link>
              </template>
              <template #default>
                <div style="display: flex; gap: 16px; flex-direction: column;text-align: center">
                  <el-avatar :size="60" :src="scope.row.artAuthor.userAvatar"
                             style="margin: 0 auto 8px"/>
                  <div>
                    <p style="margin: 0; font-weight: bold">{{scope.row.artAuthor.username}}</p>
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
        <el-table-column prop="artCreate" label="创建时间" sortable/>
        <el-table-column prop="artStatus" label="帖子状态" sortable/>
        <el-table-column fixed="right" label="操作" width="120">
          <template #default>
            <el-button link type="primary" size="small" @click="updateArticleStatus">
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
  name: "ArticleManagement",
  data() {
    return {
      formInline: {},
      tableData: [],
      tableLoading: ref(false)
    }
  },
  methods: {
    queryArticle() {
      this.tableLoading = ref(true);
      request.post("/api/admin/selectArticle", {
        artId: this.formInline.artId,
        artTitle: this.formInline.artTitle,
        artAuthor: {
          userid: this.formInline.artAuthor
        },
        artStatus: this.formInline.artStatus
      }).then(res => {
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === '1') {
          const resData = res.data;
          for (let i = 0; i < resData.length; i++) {
            resData[i].artCreate = resData[i].artCreate.substring(0, 10) + " " + resData[i].artCreate.substring(11, 19)
          }
          this.tableData = resData;

          ElMessage({
            message: '帖子列表已更新',
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
    updateArticleStatus: function () {
      ElMessageBox.prompt('1：正常   2：禁用   3：置顶', selectRowData.artId + '号帖子状态修改', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern:
            /^([1-3][1]*)$/,
        inputErrorMessage: '请输入数字1、2、3',
      })
          .then(({value}) => {
            request.put("/api/admin/updateArticle", {
              artId: selectRowData.artId,
              artTitle: selectRowData.artTitle,
              artStatus: parseInt({value}.value)
            }).then(res => {
              if (res.code === undefined) {
                ElMessage.error("登录已过期，请重新登录后再试");
                this.$router.push('/')
              } else if (res.code === '1') {
                ElMessage({
                  type: 'success',
                  message: selectRowData.artId + `号帖子的状态已经修改成功`,
                });
                this.queryArticle()
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