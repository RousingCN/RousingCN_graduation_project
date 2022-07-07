<template>
  <el-row style="width: 400px;margin: 20px">
    <el-col>
      <el-card :body-style="{ padding: '0px' }">
        <div style="font-size: 18px;color: #656565;text-align: center;margin: 10px">{{ user.username }}</div>
        <div style="text-align: center;">
          <el-image style="width: 100px; height: 100px;border-radius: 50%;" :src="user.userImg" :fit="'contain'">
            <template #placeholder>
              <div style="text-align: center">
                <el-icon>
                  <PictureFilled/>
                </el-icon>
              </div>
            </template>
          </el-image>
        </div>
        <el-divider/>
        <div style="">
          <el-row :gutter="20">
            <el-col :span="8">
              <div style="text-align: center">
                <div>关注</div>
                {{ user.userid }}
              </div>
            </el-col>
            <el-col :span="8">
              <div style="text-align: center">
                <div>粉丝</div>
                {{ user.userid }}
              </div>
            </el-col>
            <el-col :span="8">
              <div style="text-align: center">
                <div>点赞</div>
                {{ user.userid }}
              </div>
            </el-col>
          </el-row>
        </div>
        <el-divider border-style="dashed"/>
        <div style="text-align: center">
          <div style="padding: 0 10px 10px">
            {{ user.userinfo === "" ? "你好，我是" + user.username : user.userinfo }}
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>

export default {
  name: "UserShow",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem("user")),
    }
  }

}
</script>

<style>

</style>