<template>
  <div class="home" style="text-align: center" v-loading="loadingPage">
    <div style="margin: 200px auto">
      <transition name="el-zoom-in-center">
        <p v-show="show" style="font-size: 100px;margin-bottom: 20px;">
          <el-icon style="color: #efef40">
            <Sunny/>
          </el-icon>
          午时论坛
        </p>
      </transition>
      <transition name="el-zoom-in-top">
        <p v-show="show" style="font-size: 30px;color: #656565">基于 <span style="color: #42b883">Vue3</span> 与
          <span style="color: #409EFF">Element Plus</span> ，一个简洁强大的论坛</p>
      </transition>
      <transition name="el-fade-in-linear">
        <div v-show="show" style="margin: 100px auto 0;">
          <el-link
              style="font-size: 22px;padding: 10px 20px;background-color:#409EFF;color:#fff; border: 1px solid #aaceef;border-radius: 6px"
              href="/module"
              :underline="false"><el-icon style="margin-right: 10px"><Promotion /></el-icon> 进入论坛
          </el-link>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>

export default {
  name: 'HomeView',
  components: {},
  data() {
    return {
      show: false,
      loadingPage: true
    }
  },
  mounted() {
    this.loadingPage = false;
    setTimeout(() => {
      this.show = true;
    }, 1000)
  },
  methods: {}
}
</script>
