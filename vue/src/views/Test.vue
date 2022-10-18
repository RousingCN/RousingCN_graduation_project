<template>
  <el-button type="primary" @click="clickBtn">获取内容</el-button>
  <div style="border: 1px solid #ccc;width: 80%;line-height: 800px;margin: 50px auto">
    <Toolbar
        style="border-bottom: 1px solid #ccc"
        :editor="editorRef"
        :defaultConfig="toolbarConfig"
        :mode="mode"
    />
    <Editor
        style="height: 500px; overflow-y: hidden;"
        v-model="valueHtml"
        :defaultConfig="editorConfig"
        :mode="mode"
        @onCreated="handleCreated"
    />
  </div>
  <div class="test"></div>
  <button @click="jump">新建标签页</button>
</template>

<script>
import '@wangeditor/editor/dist/css/style.css' // 引入 css

import {onBeforeUnmount, ref, shallowRef, onMounted} from 'vue'
import {Editor, Toolbar} from '@wangeditor/editor-for-vue'
import {DomEditor} from '@wangeditor/editor'
import Router from "@/router";
import router from "@/router";
import {useRoute} from "vue-router";

export default {
  name: "Test",
  components: {Editor, Toolbar},
  setup() {
    // 编辑器实例，必须用 shallowRef
    const editorRef = shallowRef()

    // 内容 HTML
    const valueHtml = ref('<p>hello</p>')

    // 模拟 ajax 异步获取内容
    // onMounted(() => {
    //   setTimeout(() => {
    //     valueHtml.value = '<p>模拟 Ajax 异步设置内容</p>'
    //   }, 1500)
    // })

    const toolbarConfig = {}
    toolbarConfig.excludeKeys = [
      "fullScreen",
      "group-video",
      "group-image"
    ];

    const editorConfig = {placeholder: '请输入内容...'}

    // 组件销毁时，也及时销毁编辑器
    onBeforeUnmount(() => {
      const editor = editorRef.value
      if (editor == null) return
      editor.destroy()
    })

    const handleCreated = (editor) => {
      editorRef.value = editor // 记录 editor 实例，重要！
    }

    //接收query方式传来的参数
    const Route = useRoute();
    onMounted(()=>{
      console.log(Route.query)
    })

    return {
      editorRef,
      valueHtml,
      mode: 'default', // 或 'simple'
      toolbarConfig,
      editorConfig,
      handleCreated
    };
  },
  methods: {
    clickBtn() {
      // const toolbar = DomEditor.getToolbar(this.editorRef)
      // console.log(toolbar.getConfig().toolbarKeys)
      console.log(this.editorRef.getHtml())
      document.getElementsByClassName("test")[0].innerHTML = this.editorRef.getHtml()
      console.log(useRoute())
    },
    jump() {
      //通过query方式传递请求
      const routerUrl = this.$router.resolve({
        path: '/test',
        query: {
          valueHtml: '跳转测试'
        },
      });
      window.open(routerUrl.href, '_blank')
    },
  }
}
</script>

<style scoped>

</style>