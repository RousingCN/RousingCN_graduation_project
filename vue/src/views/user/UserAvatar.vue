<template>
  <div
      style="border: 1px solid #ccc;margin: 50px auto;padding: 50px;border-radius: 20px;width: 40%;height: 100%">
    <h1>修改头像</h1>
    <el-divider/>
    <div style="width: 120px;height: 120px;margin: 50px auto">
      <el-image style="width: 120px; height: 120px;margin-bottom: 40px" :src="url" :fit="'cover'"
                :preview-src-list="[url]"
                :initial-index="0">
        <template #error>
          <div class="image-slot" style="color: #a1a1a1">暂无头像</div>
        </template>
      </el-image>
    </div>
    <div style="width: 88px; margin: 50px auto">
      <el-upload
          action="/api/file/avatar/save"
          :accept="'.png'"
          :before-upload="beforeAvatarUpload"
          :limit="1"
          :on-success="uploadSuccess"
          :on-error="uploadError"
          :show-file-list="false"
      >
        <el-button type="primary">上传头像</el-button>
      </el-upload>
    </div>
    <p style="text-align: center;color: #a1a1a1;font-size: 10px">
      仅支持 PNG 格式文件，图片大小不超过 2MB，建议分辨率为 120 x 120
    </p>
  </div>
</template>

<script>

import {ElMessage} from "element-plus";

let url = JSON.parse(sessionStorage.getItem("user")).userAvatar

export default {
  name: "UserCenter",
  data() {
    return {
      url: url === null ? '' : url,
    }
  },
  methods: {
    uploadSuccess(res) {
      // 服务器是否返回空信息
      if (res.code === undefined) {
        ElMessage.error("登录已过期，请重新登录后再试");
        this.$router.push('/')
      } else if (res.code === "1") {
        ElMessage({
          message: '头像修改成功',
          type: 'success',
        });
      } else {
        ElMessage.error(res.msg);
      }
    },
    uploadError() {
      ElMessage.error("头像上传失败");
    },
    beforeAvatarUpload(avatar) {
      if (avatar.type !== 'image/png') {
        ElMessage.error('图片格式必须为 PNG 格式!')
        return false
      } else if (avatar.size / 1024 / 1024 > 2) {
        ElMessage.error('图片大小必须小于 2MB!')
        return false
      }
      return true
    },
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