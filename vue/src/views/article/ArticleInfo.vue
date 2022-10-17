<template>
  <div style="border: 1px solid #ccc;width: 80%;margin: 50px auto">
    <Editor
        style="min-height: 500px; overflow-y: hidden;"
        v-model="valueHtml"
        :defaultConfig="editorConfig"
        :mode="mode"
        @onCreated="handleCreated"
    />
  </div>
</template>

<script>
import '@wangeditor/editor/dist/css/style.css' // 引入 css

import {Editor} from "@wangeditor/editor-for-vue";
import {onBeforeUnmount, ref, shallowRef} from "vue";

export default {
  name: 'Article',
  data() {
    return {
      articleData: null
    }
  },
  components: {Editor},
  setup() {
    // 编辑器实例，必须用 shallowRef
    const editorRef = shallowRef()

    // 内容 HTML
    const valueHtml = ref('<p>正在加载内容</p>')

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

    return {
      editorRef,
      valueHtml,
      mode: 'default', // 或 'simple'
      editorConfig,
      handleCreated
    };
  },
  updated() {
    this.articleData = JSON.parse(sessionStorage.getItem('article'))
    console.log(this.articleData)
    this.valueHtml.value = this.articleData.artContext
    this.editorRef.disable()
  }
};
</script>
