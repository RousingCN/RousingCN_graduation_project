<template>
  <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      style="height: calc(100vh - 59px);min-width: 64px"
      :collapse="isCollapse"
      router
  >
    <el-menu-item index="/createModule">
      <el-icon><EditPen /></el-icon>
      <template #title>发帖</template>
    </el-menu-item>
    <el-menu-item index="/randomModule">
      <el-icon><StarFilled /></el-icon>
      <template #title>热门帖子</template>
    </el-menu-item>
    <el-menu-item index="/allModule">
      <el-icon><Menu /></el-icon>
      <template #title>全部帖子</template>
    </el-menu-item>
  </el-menu>
</template>

<script setup>
import { ref } from 'vue'

const isCollapse = ref(true)
</script>

<style>
</style>