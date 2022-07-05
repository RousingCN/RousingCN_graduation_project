<template>
  <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      style="height: calc(100vh - 59px)"
      :collapse="isCollapse"
      router
  >
    <el-menu-item index="/userAvatar">
      <el-icon><Avatar /></el-icon>
      <template #title>头像</template>
    </el-menu-item>
    <el-menu-item index="/userInfo">
      <el-icon><Tickets /></el-icon>
      <template #title>信息</template>
    </el-menu-item>
    <el-menu-item index="/userPassword">
      <el-icon><Lock /></el-icon>
      <template #title>密码</template>
    </el-menu-item>
    <el-menu-item index="/userSetting">
      <el-icon><setting /></el-icon>
      <template #title>设置</template>
    </el-menu-item>
  </el-menu>
</template>

<script setup>
import { ref } from 'vue'

const isCollapse = ref(true)
</script>

<style>
</style>