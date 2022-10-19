<template>
  <el-scrollbar height="100vh">
    <Header/>
    <div style="display: flex;">
      <UserAside/>
      <router-view style="flex: 1"/>
    </div>
  </el-scrollbar>
</template>

<script>
import Header from "@/components/Header";
import UserAside from "@/components/UserAside";

export default {
  name: "UserLayout",
  components: {Header, UserAside}
}
</script>

<style scoped>

</style>