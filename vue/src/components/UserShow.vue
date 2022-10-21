<template>
  <el-row style="width: 400px;margin: 20px">
    <el-col>
      <el-card :body-style="{ padding: '20px' }">
        <div style="text-align: center;">
          <el-image style="width: 100px; height: 100px;border-radius: 50%;" :src="user.userAvatar" :fit="'contain'">
            <template #placeholder>
              <div style="text-align: center">
                <el-icon>
                  <PictureFilled/>
                </el-icon>
              </div>
            </template>
          </el-image>
        </div>
        <div style="font-size: 18px;font-weight:bold;color: #656565;text-align: center;margin: 10px">{{ user.username }}<span style="color:#a1a1a1;font-size: 14px"> #{{user.userid}}</span></div>
        <el-divider/>
        <div style="">
          <el-row :gutter="20">
            <el-col :span="8">
              <div style="text-align: center">
                <div>赞过</div>
                {{ userAchievement.like }}
              </div>
            </el-col>
            <el-col :span="8">
              <div style="text-align: center">
                <div>评论</div>
                {{ userAchievement.comment }}
              </div>
            </el-col>
            <el-col :span="8">
              <div style="text-align: center">
                <div>收藏</div>
                {{ userAchievement.collect }}
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
      userAchievement: JSON.parse(sessionStorage.getItem("userAchievement")),
    }
  }
}
</script>

<style>

</style>