<template>
  <div style="margin: 100px auto 20px;width: 1400px;border: 1px solid #ccc;padding: 50px 50px 20px;border-radius: 20px">
    <h2/>
    <el-divider/>
    <div style="width: 1200px;margin: 0 auto">
      <Editor
          style="min-height: 500px; overflow-y: hidden;"
          v-model="valueHtml"
          :defaultConfig="editorConfig"
          :mode="mode"
          @onCreated="handleCreated"
      />
    </div>

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
            <el-button-group class="ml-4">
              <el-button :type="userLikeIt?'primary':''" size="large" text style="margin-right: 20px"
                         @click="clickLike" :loading="likeLoading">
                <el-icon>
                  <Sunny/>
                </el-icon>
                点赞 {{ count_like }}
              </el-button>
              <el-button :type="userCommentIt?'primary':''" size="large" text style="margin-right: 20px"
                         @click="clickComment">
                <el-icon>
                  <ChatLineRound/>
                </el-icon>
                评论 {{ count_comment }}
              </el-button>
              <el-button :type="userCollectIt?'primary':''" size="large" text style="margin-right: 20px"
                         @click="clickCollect" :loading="collectLoading">
                <el-icon>
                  <Star/>
                </el-icon>
                收藏 {{ count_collect }}
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
  <div style="margin: 0 auto 50px;width: 1400px;border: 1px solid #ccc;padding: 50px 50px 20px;border-radius: 20px">
    <h3>评论</h3>
    <el-divider/>
    <div>
      <div style="margin: 0 auto;line-height: 100%;text-align: center;color: #a1a1a1">暂无评论</div>
    </div>
  </div>
</template>

<script>
import '@wangeditor/editor/dist/css/style.css' // 引入 css

import {Editor} from "@wangeditor/editor-for-vue";
import {onBeforeUnmount, ref, shallowRef} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";


export default {
  name: 'ArticleInfo',
  data() {
    return {
      articleData: JSON.parse(sessionStorage.getItem('article')),
      count_like: 0,
      count_comment: 0,
      count_collect: 0,
      count_view: 0,
      userLikeIt: false,
      userCommentIt: false,
      userCollectIt: false,
      likeLoading: false,
      collectLoading: false
    };
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
  created() {

  },
  activated() {
    console.log("enter.")
  },
  deactivated() {
    console.log("exit.")
  },
  updated() {
    this.articleData = JSON.parse(sessionStorage.getItem('article'))
    document.getElementsByTagName("h2")[0].innerText = this.articleData.artTitle;
    this.valueHtml = this.articleData.artContext
    this.editorRef.disable()
    request.post("/Achievement/article", {
      artId: this.articleData.artId
    }).then(re => {
      if (re.code === '1') {
        this.count_view = re.data.view;
        this.count_like = re.data.like;
        this.count_comment = re.data.comment;
        this.count_collect = re.data.collect;
        this.userLikeIt = re.data.like_it;
        this.userCommentIt = re.data.comment_it;
        this.userCollectIt = re.data.collect_it;
        // ElMessage({
        //   message: '数据已更新',
        //   type: 'success',
        // })
      } else {
        ElMessage.error(re.msg);
      }
    })
  },
  methods: {
    clickLike() {
      this.likeLoading = true
      //取消点赞
      request.post('/Achievement/userLikeArticle', {
        articleId: this.articleData.artId,
        like_it: this.userLikeIt
      }).then(res => {
        if (res.code === '1') {
          this.userLikeIt = res.data;
          if (this.userLikeIt) {
            this.count_like++;
          } else {
            this.count_like--;
          }
        } else {
          ElMessage.error(res.msg)
        }
        this.likeLoading = false
      })
    },
    clickComment() {
      //跳转至编写评论页面
    },
    clickCollect() {
      this.collectLoading = true;
      //取消收藏
      request.post('/Achievement/userCollectArticle', {
        articleId: this.articleData.artId,
        collect_it: this.userCollectIt
      }).then(res => {
        if (res.code === '1') {
          this.userCollectIt = res.data;
          if (this.userCollectIt) {
            this.count_collect++;
          } else {
            this.count_collect--;
          }
        } else {
          ElMessage.error(res.msg)
        }
        this.collectLoading = false;
      })
    },
  }
};
</script>
