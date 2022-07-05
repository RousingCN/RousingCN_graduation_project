<template>
  <el-scrollbar height="100vh">
    <Header />
    <div style="display: flex;">
      <ModuleAside />
      <router-view style="flex: 1"/>
    </div>
  </el-scrollbar>
</template>

<script>
import Header from "@/components/Header";
import ModuleAside from "@/components/ModuleAside";

export default {
  name: "ModuleLayout",
  components: {Header, ModuleAside}
}
</script>

<style scoped>

</style>