<template>
  <div
      style="border: 1px solid #ccc;margin: 50px auto;padding: 50px;border-radius: 20px;width: 1600px;height: 100%;flex: 1">
    <h1>创建帖子</h1>
    <el-divider/>
    <el-form label-width="80px" style="margin-top: 50px;width: 1600px;">
      <el-form-item label="所属板块" prop="name" style="width: 30%;margin-bottom: 30px">
        <el-input v-model="moduleName" disabled/>
      </el-form-item>
      <el-form-item label="帖子标题" prop="name" style="width: 50%;margin-bottom: 30px">
        <el-input v-model="artTitle" clearable/>
      </el-form-item>
      <el-form-item label="帖子内容" prop="name" style="width: 1600px;margin-bottom: 30px">
        <div style="border: 1px solid var(--el-border-color);border-radius: 2px;width: 1200px;">
          <Toolbar
              style="border-bottom: 1px solid #ccc"
              :editor="editorRef"
              :defaultConfig="toolbarConfig"
              :mode="mode"
          />
          <Editor
              style="height: 300px; overflow-y: hidden;"
              v-model="artContext"
              :defaultConfig="editorConfig"
              :mode="mode"
              @onCreated="handleCreated"
          />
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="create">发帖</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import '@wangeditor/editor/dist/css/style.css' // 引入 css

import request from "@/utils/request";
import {ElMessage} from "element-plus";
import {Editor, Toolbar} from "@wangeditor/editor-for-vue";
import {onBeforeUnmount, onMounted, ref, shallowRef} from "vue";

export default {
  name: "CreateArticle",
  data() {
    return {
      moduleName: JSON.parse(sessionStorage.getItem('module')).moduleName,
      artTitle: "",
      artContext: "",
    };
  },
  components: {Editor, Toolbar},
  setup() {
// 编辑器实例，必须用 shallowRef
    const editorRef = shallowRef()

    // 内容 HTML
    const artContext = ref('')

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

    return {
      editorRef,
      artContext,
      mode: 'default', // 或 'simple'
      toolbarConfig,
      editorConfig,
      handleCreated
    };
  },
  methods: {
    create() {
      request.post("/article/createArticle", {
        artTitle: this.artTitle,
        artContext: this.artContext,
        artAuthor: JSON.parse(sessionStorage.getItem("user")),
        artModule: JSON.parse(sessionStorage.getItem('module')).moduleId
      }).then(res => {
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        }
        if (res.code === '1') {
          ElMessage({
            message: '发帖成功',
            type: 'success',
          });
          this.$router.back();
        } else {
          ElMessage.error(res.msg);
        }
      })
    },
  },
}
</script>

<style scoped>

</style>