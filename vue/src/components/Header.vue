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
      午时论坛
    </span>
    <div class="flex-grow"/>
    <el-menu-item index="/index" style="width: 8%">首页</el-menu-item>
    <el-menu-item index="/module" style="width: 8%">板块</el-menu-item>
    <el-menu-item index="/article" style="width: 8%">娱乐</el-menu-item>
    <el-menu-item index="/about" style="width: 8%">关于</el-menu-item>
    <div style="width: 10%;"/>
    <el-sub-menu index="" >
      <template #title>
        <el-avatar v-if="user.userImg===null"> user</el-avatar>
        <el-image style="width: 59px;height: 59px;border-radius: 50%" v-if="user.userImg!==null" :src="user.userImg"
                  :fit="'contain'">
          <template #error >
            <div class="image-slot">{{user.username}}</div>
          </template>
        </el-image>
      </template>
      <el-menu-item index="/userCenter">个人中心</el-menu-item>
      <el-menu-item index="/login" @click="loginOut">注销</el-menu-item>
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
    if (res.code === '1') {
      ElMessage({
        message: '注销成功',
        type: 'success',
      });
      sessionStorage.clear();
      router.push("/login")
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