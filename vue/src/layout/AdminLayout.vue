<template>
  <AdminHeader></AdminHeader>
  <router-view/>
</template>

<script>
import AdminHeader from "@/components/AdminHeader";

export default {
  name: "AdminLayout",
  components: {AdminHeader}
}
</script>

<style scoped>

</style>