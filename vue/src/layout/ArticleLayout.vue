<template>
  <el-scrollbar height="100vh">
    <Header/>
    <div>
      <router-view/>
    </div>
  </el-scrollbar>
</template>

<script>
import Header from "@/components/Header";
export default {
  name: "ArticleLayout",
  components: {Header}
}
</script>

<style scoped>

</style>