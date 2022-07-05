<template>
  <Header></Header>
  <router-view/>
</template>

<script>
import Header from "@/components/Header";

export default {
  name: "IndexLayout",
  components: {Header}
}
</script>

<style scoped>

</style>