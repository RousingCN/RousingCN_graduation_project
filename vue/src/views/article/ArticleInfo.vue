<template>
  <div style="margin: 100px auto;width: 80%;border: 1px solid #ccc;padding: 50px 50px 20px;border-radius: 20px">
    <h2/>
    <el-divider/>
    <Editor
        style="min-height: 500px; overflow-y: hidden;"
        v-model="valueHtml"
        :defaultConfig="editorConfig"
        :mode="mode"
        @onCreated="handleCreated"
    />
    <el-divider/>
    <div>
      <el-row :gutter="20">
        <el-col :span="8">
          <div class="grid-content ep-bg-purple" style="padding: 12px;">
            <el-icon>
              <Avatar/>
            </el-icon>
            <a :href="'/userCenter/'+articleData.artAuthor.userid"
               style="text-decoration: none;color: #a1a1a1;margin-left: 6px">{{ articleData.artAuthor.username }}</a>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="grid-content ep-bg-purple" style="text-align: center">
            <el-button-group class="ml-4" >
              <el-button :type="likeType" size="large" text style="margin-right: 20px">
                <el-icon>
                  <Sunny/>
                </el-icon>
                点赞
              </el-button>
              <el-button :type="CommentType" size="large" text style="margin-right: 20px">
                <el-icon>
                  <ChatLineRound/>
                </el-icon>
                评论
              </el-button>
              <el-button :type="CollectType" size="large" text style="margin-right: 20px">
                <el-icon>
                  <Star/>
                </el-icon>
                收藏
              </el-button>
            </el-button-group>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="grid-content ep-bg-purple">
            <span style="line-height: 100%;position: absolute;right: 20px;padding: 12px;color: #a1a1a1"><el-icon
                style="margin-right: 6px"><EditPen/></el-icon>{{ articleData.artCreate }}</span>
          </div>
        </el-col>
      </el-row>
    </div>
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
      articleData: JSON.parse(sessionStorage.getItem('article')),
      likeType: "",
      CommentType: "",
      CollectType: ""
    }
  },
  components: {Editor},
  setup() {
    // 编辑器实例，必须用 shallowRef
    const editorRef = shallowRef()

    // 内容 HTML
    const valueHtml = ref('')

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
      mode: 'simple',
      editorConfig,
      handleCreated
    };
  },
  updated() {
    this.articleData = JSON.parse(sessionStorage.getItem('article'))
    // console.log(this.articleData)
    // console.log(this.articleData.artContext)
    // this.valueHtml="<p>test</p>"
    document.getElementsByTagName("h2")[0].innerText = this.articleData.artTitle;
    this.valueHtml = this.articleData.artContext
    this.editorRef.disable()
  }
};
</script>
