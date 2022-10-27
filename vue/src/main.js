import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus, {ElMessage} from 'element-plus'
import 'element-plus/dist/index.css'
import '@/assets/css/global.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
import zhCn from 'element-plus/es/locale/lang/zh-cn'

app.use(ElementPlus, {
    locale: zhCn,
})
app.use(store).use(router).use(ElementPlus).mount('#app')


router.beforeEach(function (to, from, next) {
    //页面是否登录
    if (to.meta.needLogin) {
        //本地存储中是否有token(uid)数据
        if (sessionStorage.getItem("user")) {
            next(); //表示已经登录
        } else {
            ElMessage.error('请先登录再试')
            //next可以传递一个路由对象作为参数 表示需要跳转到的页面
            next({
                name: "Login"
            });
        }
    } else {
        //表示不需要登录
        next(); //继续往后走
    }
});