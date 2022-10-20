<template>
  <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      :ellipsis="false"
      router
  >
    <span style="line-height: 59px;margin-left: 20px">
      <el-icon><Sunny/></el-icon>
      午时论坛(管理员)
    </span>
    <div class="flex-grow"/>
    <el-menu-item index="/admin/userManagement" style="width: 8%">用户管理</el-menu-item>
    <el-menu-item index="/admin/moduleManagement" style="width: 8%">模块管理</el-menu-item>
    <el-menu-item index="/admin/articleManagement" style="width: 8%">帖子管理</el-menu-item>
    <el-menu-item index="/admin/commentManagement" style="width: 8%">评论管理</el-menu-item>
    <div style="width: 10%;"/>
    <el-sub-menu index="" >
      <template #title>
        <el-avatar v-if="user.userAvatar===null"> user</el-avatar>
        <el-image style="width: 59px;height: 59px;border-radius: 50%" v-if="user.userAvatar!==null" :src="user.userAvatar"
                  :fit="'contain'">
          <template #error >
            <div class="image-slot">{{user.username}}</div>
          </template>
        </el-image>
      </template>
      <el-menu-item index="/userCenter">个人中心</el-menu-item>
      <el-menu-item index="/" @click="loginOut">注销</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script setup>
import {ref} from 'vue'
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import router from "@/router";

const activeIndex = ref('1')
const user = JSON.parse(sessionStorage.getItem("user"))

const loginOut = function () {
  request.delete("/user/loginOut").then(res => {
    if (res.code === undefined) {
      ElMessage.error("登录已过期，请重新登录后再试");
      this.$router.push('/')
    }
    if (res.code === '1') {
      ElMessage({
        message: '注销成功',
        type: 'success',
      });
      sessionStorage.clear();
      router.push("/")
    } else {
      ElMessage.error(res.msg);
    }
  })
};

</script>

<style>
.flex-grow {
  flex-grow: 1;
}
</style>