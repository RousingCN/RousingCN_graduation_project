<template>
  <div style="margin: 50px auto 20px;width: 1400px;border: 1px solid #ccc;padding: 50px 50px 20px;border-radius: 20px"
       v-loading="pageLoading">
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
    <div style="height: 40px;">
      <el-row :gutter="20">
        <el-col :span="8">
          <div class="grid-content ep-bg-purple" style="padding: 12px;height: 16px">
            <el-popover :width="250" placement="top"
                        popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;">
              <template #reference>
                <el-link target="_blank"
                         style="margin-left: 6px;margin-top: -6px;font-size: 16px" :icon="icon.Avatar">
                  {{ articleData.artAuthor.username }}
                </el-link>
              </template>
              <template #default>
                <div style="display: flex; gap: 16px; flex-direction: column;text-align: center">
                  <el-avatar :size="60" :src="articleData.artAuthor.userAvatar"
                             style="margin: 0 auto 8px"/>
                  <div>
                    <p style="margin: 0; font-weight: bold">{{ articleData.artAuthor.username }}</p>
                    <p style="margin: 0; font-size: 14px; color: var(--el-color-info)">
                      #{{ articleData.artAuthor.userid }}</p>
                  </div>
                  <p style="margin: 0" v-if="articleData.artAuthor.userinfo!==''">{{
                      articleData.artAuthor.userinfo
                    }}</p>
                  <p style="margin: 0;color: #bebebe" v-if="articleData.artAuthor.userinfo===''">
                    用户还没有设置个人签名</p>
                </div>
              </template>
            </el-popover>
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
                点赞
                <el-badge class="mark" :value="count_like" type="primary" :max="99" style="margin-top: -10px"/>
              </el-button>
              <el-button :type="userCommentIt?'primary':''" size="large" text style="margin-right: 20px"
                         @click="clickComment" :loading="commentLoading">
                <el-icon>
                  <ChatLineRound/>
                </el-icon>
                评论
                <el-badge class="mark" :value="count_comment" type="primary" :max="99" style="margin-top: -10px"/>
              </el-button>
              <el-button :type="userCollectIt?'primary':''" size="large" text style="margin-right: 20px"
                         @click="clickCollect" :loading="collectLoading">
                <el-icon>
                  <Star/>
                </el-icon>
                收藏
                <el-badge class="mark" :value="count_collect" type="primary" :max="99" style="margin-top: -10px"/>
              </el-button>
            </el-button-group>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="grid-content ep-bg-purple">
            <span style="height: 16px;position: absolute;padding: 12px;right: 20px;">
              <el-link target="_blank"
                       style="margin-left: 6px;margin-top: -6px;font-size: 16px" :icon="icon.Timer" :underline="false"
                       disabled>
              {{ articleData.artCreate }}
            </el-link>
            </span>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
  <el-drawer v-model="commentVisible" :show-close="false">

    <!--    抽屉头部-->
    <template #header="{ close, titleId, titleClass }" style="margin-bottom: 0">
      <h4 :id="titleId" :class="titleClass" style="line-height: 30px">评论 {{ count_comment }}</h4>
      <el-button type="danger" @click="close">
        <el-icon class="el-icon--left">
          <CircleCloseFilled/>
        </el-icon>
        关闭
      </el-button>
    </template>
    <el-scrollbar style="height: calc(100vh - 124px);">
      <!--    抽屉主体————编写评论-->
      <div class="common-layout" style=";padding: 10px">
        <el-container>
          <el-aside width="60px">
            <div>
              <el-image style="width: 60px; height: 60px;border-radius: 50%"
                        :src="userData.userAvatar" :fit="'cover'">
                <template #error>
                  <div class="image-slot">
                    <el-icon>
                      <PictureFilled/>
                    </el-icon>
                  </div>
                </template>
              </el-image>
            </div>
          </el-aside>
          <el-container>
            <el-header style="height: 20px;">
              <el-row class="row-bg" justify="space-between">
                <el-col :span="9">
                  <a href="#" style="text-decoration: none;color: #656565;font-size: 18px;line-height: 20px"> {{
                      userData.username
                    }}</a>
                </el-col>
                <el-col :span="9">
                  <el-button type="primary" style="position: absolute;right: 0;" @click="postComment"
                             :loading="commentLoading">发布评论
                  </el-button>
                </el-col>
              </el-row>
            </el-header>
            <el-main>
              <!--            输入面板-->
              <el-input
                  v-model="loginUserCommentContext"
                  maxlength="250"
                  placeholder="分享你的观点吧"
                  show-word-limit
                  type="textarea"
                  :autosize="{ minRows: 3}"
                  style="font-size: 14px"
              />
            </el-main>
          </el-container>
        </el-container>
        <el-divider/>
      </div>
      <!--    如果没有评论-->
      <div v-if="commentList.length===0">
        <p style="text-align: center;color:#a1a1a1;">暂无评论</p>
      </div>
      <!--      如果有评论，显示评论-->
      <div v-if="commentList.length>0">
        <div v-for="comData in commentList" :key="comData" class="common-layout" style="margin-bottom: 20px">
          <el-container>
            <el-aside width="50px">
              <div>
                <el-image style="width: 50px; height: 50px;border-radius: 50%"
                          :src="comData.comUser.userAvatar" :fit="'cover'">
                  <template #error>
                    <div class="image-slot">
                      <el-icon :size="50">
                        <Avatar/>
                      </el-icon>
                    </div>
                  </template>
                </el-image>
              </div>
            </el-aside>
            <el-container>
              <el-header style="height: 20px;color: #656565">
                <el-row class="row-bg" justify="space-between">
                  <el-col :span="9">
                    <el-tooltip class="box-item" effect="light" placement="right">
                      <template #content> {{ comData.comUser.username }}<span
                          style="color: #969696"> #{{ comData.comUser.userid }}</span></template>
                      <a href="#"
                         style="text-decoration: none;color: #2a2a2a;font-size: 16px;margin-right: 40px;line-height: 20px">
                        {{ comData.comUser.username }}
                      </a>
                    </el-tooltip>
                  </el-col>
                  <el-col :span="9">
                    <span style="position: absolute;right: 10px;font-size: 12px;line-height: 20px">
                      {{ comData.comCreate }}
                    </span>
                  </el-col>
                </el-row>
              </el-header>
              <el-main style="font-size: 14px;padding-top: 5px">{{ comData.comContext }}</el-main>
            </el-container>
          </el-container>
        </div>
      </div>
    </el-scrollbar>
  </el-drawer>
  <el-backtop :bottom="100"/>
</template>

<script>
import '@wangeditor/editor/dist/css/style.css' // 引入 css

import {Editor} from "@wangeditor/editor-for-vue";
import {createApp, onBeforeUnmount, ref, shallowRef} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import {Avatar, Timer} from '@element-plus/icons-vue'


export default {
  name: 'ArticleInfo',
  data() {
    return {
      icon: {
        Avatar: Avatar,
        Timer: Timer
      },
      pageLoading: true,
      userData: JSON.parse(sessionStorage.getItem('user')),
      articleData: JSON.parse(sessionStorage.getItem('article')),
      count_like: 0,
      count_comment: 0,
      count_collect: 0,
      count_view: 0,
      userLikeIt: false,
      userCommentIt: false,
      userCollectIt: false,
      likeLoading: false,
      commentLoading: false,
      collectLoading: false,
      commentVisible: false,
      commentList: [],
      loginUserCommentContext: ""
    };
  },
  components: {Editor},
  setup() {
    const app = createApp(this)
    for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
      app.component(key, component)
    }
    // 编辑器实例，必须用 shallowRef
    const editorRef = shallowRef()

    // 内容 HTML
    const valueHtml = ref('')

    const editorConfig = {placeholder: '帖子内容获取中...'}

    // 组件销毁时，也及时销毁编辑器
    onBeforeUnmount(() => {
      const editor = editorRef.value
      if (editor == null) return
      editor.destroy()
    })

    const handleCreated = (editor) => {
      editorRef.value = editor
    }

    return {
      editorRef,
      valueHtml,
      mode: 'simple',
      editorConfig,
      handleCreated
    };
  },
  mounted() {
    setTimeout(() => {
      this.updateArtAchievement();
    }, 500);
  },
  methods: {
    updateArtAchievement() {
      this.articleData = JSON.parse(sessionStorage.getItem('article'))
      document.getElementsByTagName("h2")[0].innerText = this.articleData.artTitle;
      this.editorRef.setHtml(this.articleData.artContext)

      this.articleData.artCreate = this.articleData.artCreate.substring(0, 10) + " " + this.articleData.artCreate.substring(11, 19)

      request.post("/Achievement/article", {
        artId: this.articleData.artId
      }).then(res => {
        // 服务器是否返回空信息
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === '1') {
          this.count_view = res.data.view;
          this.count_like = res.data.like;
          this.count_comment = res.data.comment;
          this.count_collect = res.data.collect;
          this.userLikeIt = res.data.like_it;
          this.userCommentIt = res.data.comment_it;
          this.userCollectIt = res.data.collect_it;
        } else {
          ElMessage.error(res.msg);
        }
        this.editorRef.disable();
        this.pageLoading = false;
      })

      this.userViewArticle()
    },
    updateArtComment() {
      request.post("/comment/all", {artId: this.articleData.artId}).then(res => {
        // 服务器是否返回空信息
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        }
        if (res.code === '1') {
          for (let i = 0; i < res.data.length; i++) {
            res.data[i].comCreate = res.data[i].comCreate.substring(0, 10) + " " + res.data[i].comCreate.substring(11, 19)
          }
          this.commentList = res.data;
        } else {
          ElMessage.error(res.msg);
        }
        this.commentLoading = false;
        this.commentVisible = true;
      })
    },
    clickLike() {
      this.likeLoading = true
      //取消点赞
      request.post('/Achievement/userLikeArticle', {
        articleId: this.articleData.artId,
        like_it: this.userLikeIt
      }).then(res => {
        // 服务器是否返回空信息
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === '1') {
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
      this.commentLoading = true;
      this.updateArtComment()
    },
    clickCollect() {
      this.collectLoading = true;
      //取消收藏
      request.post('/Achievement/userCollectArticle', {
        articleId: this.articleData.artId,
        collect_it: this.userCollectIt
      }).then(res => {
        // 服务器是否返回空信息
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === '1') {
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
    postComment() {
      //判断内容是否为空
      if (this.loginUserCommentContext.trim().length < 4) {
        ElMessage.error("有效评论内容不能少于4个字")
        return;
      }
      this.commentLoading = true;
      request.post('/comment/add', {
        comContext: this.loginUserCommentContext,
        comArticle: {
          artId: this.articleData.artId
        }
      }).then(res => {
        // 服务器是否返回空信息
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === '1') {
          this.loginUserCommentContext = '';
          this.updateArtComment();
          this.count_comment++;
          this.userCommentIt = true;
          ElMessage({
            message: '发布成功',
            type: 'success',
          });
        } else {
          ElMessage.error(res.msg);
        }
      })
    },
    userViewArticle() {
      request.post('Achievement/userViewArticle', {
        articleId: this.articleData.artId
      }).then(res => {
        // 服务器是否返回空信息
        if (res.code === undefined) {
          ElMessage.error("登录已过期，请重新登录后再试");
          this.$router.push('/')
        } else if (res.code === '1') {
          console.log('已记录浏览历史,当前帖子id：' + this.articleData.artId);
        } else {
          ElMessage.error(res.msg)
        }
      })
    },
  }
};
</script>
