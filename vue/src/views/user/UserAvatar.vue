<template>
  <div
      style="border: 1px solid #ccc;margin: 50px auto;padding: 50px;border-radius: 20px;max-width: 1200px;height: 100%">
    <h1>头像</h1>
    <div style="width: 120px;height: 120px;margin: 20px auto">
      <el-image style="width: 120px; height: 120px;" :src="url" :fit="'cover'">
        <template #error>
          <div class="image-slot">暂无头像</div>
        </template>
      </el-image>
    </div>
    <div>
      <el-upload
          action="/api/file/avatar"
          :limit="1"
          :on-success="uploadSuccess"
          :on-error="uploadError"
      >
        <el-button type="primary">上传头像</el-button>
      </el-upload>
    </div>
  </div>
</template>

<script>

import {ElMessage} from "element-plus";

let url = JSON.parse(sessionStorage.getItem("user")).userImg
console.log(url)

export default {
  name: "UserCenter",
  data() {
    return {
      url: url === null ? '' : url,
    }
  },
  methods: {
    uploadSuccess(res) {
      console.log(res)
      if (res.code === "1") {
        ElMessage({
          message: '信息修改成功',
          type: 'success',
        });
        sessionStorage.setItem("user", res.data)

      } else {
        ElMessage.error(res.msg);
      }
    },
    uploadError() {
      ElMessage.error("头像上传失败");
    }
  }
}
</script>

<style scoped>
.image-slot {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: var(--el-fill-color-light);
  color: var(--el-text-color-secondary);
  font-size: 14px;
}
</style>