<template>
  <el-scrollbar height="100vh">
    <Header/>
    <div style="display: flex;">
      <ModuleAside/>
      <router-view/>
      <UserShow/>
    </div>
  </el-scrollbar>
</template>

<script>
export default {
  name: "ArticleLayout",
  components:{

  }
}
</script>

<style scoped>

</style>