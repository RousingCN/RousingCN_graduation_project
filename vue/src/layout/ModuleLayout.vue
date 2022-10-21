<template>
  <el-scrollbar height="100vh">
    <Header/>
    <div style="display: flex;">
      <ModuleAside/>
      <router-view/>
    </div>
  </el-scrollbar>
</template>

<script>
import Header from "@/components/Header";
import ModuleAside from "@/components/ModuleAside";
import UserShow from "@/components/UserShow";

export default {
  name: "ModuleLayout",
  components: {UserShow, Header, ModuleAside}
}
</script>

<style scoped>

</style>