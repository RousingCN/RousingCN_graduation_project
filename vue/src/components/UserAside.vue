<template>
  <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      style="height: calc(100vh - 62px);width: 200px;"
      :collapse="false"
      router
      :unique-opened="true"
  >
    <el-sub-menu index="1">
      <template #title>
        <el-icon>
          <Clock/>
        </el-icon>
        <span>用户记录</span>
      </template>
      <el-menu-item index="/likeHistory">
        <el-icon>
          <Sunny/>
        </el-icon>
        <template #title>点赞记录</template>
      </el-menu-item>
      <el-menu-item index="/commentHistory">
        <el-icon>
          <ChatLineRound/>
        </el-icon>
        <template #title>评论记录</template>
      </el-menu-item>
      <el-menu-item index="/collectHistory">
        <el-icon>
          <Star/>
        </el-icon>
        <template #title>收藏记录</template>
      </el-menu-item>
      <el-menu-item index="/viewHistory">
        <el-icon>
          <View/>
        </el-icon>
        <template #title>浏览记录</template>
      </el-menu-item>
    </el-sub-menu>


    <el-sub-menu index="2">
      <template #title>
        <el-icon>
          <location/>
        </el-icon>
        <span>修改信息</span>
      </template>
      <el-menu-item index="/userAvatar">
        <el-icon>
          <Avatar/>
        </el-icon>
        <template #title>用户头像</template>
      </el-menu-item>
      <el-menu-item index="/userInfo">
        <el-icon>
          <Tickets/>
        </el-icon>
        <template #title>个人信息</template>
      </el-menu-item>
      <el-menu-item index="/userPassword">
        <el-icon>
          <Lock/>
        </el-icon>
        <template #title>用户密码</template>
      </el-menu-item>
    </el-sub-menu>


    <el-menu-item index="/userSetting">
      <el-icon>
        <setting/>
      </el-icon>
      <template #title>用户设置</template>
    </el-menu-item>
  </el-menu>
</template>

<script setup>

</script>

<style>
</style>