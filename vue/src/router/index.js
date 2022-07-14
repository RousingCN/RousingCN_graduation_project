import {createRouter, createWebHistory} from 'vue-router'
import IndexLayout from "@/layout/IndexLayout";
import UserLayout from "@/layout/UserLayout";
import ModuleLayout from "@/layout/ModuleLayout";

const routes = [
    {
        path: '/',
        name: 'Layout',
        component: IndexLayout,
        redirect: "/index",
        children: [
            {
                path: '/index',
                name: 'Index',
                component: () => import('../views/Index')
            },
            {
                path: '/article',
                name: 'Article',
                component: () => import('../views/article/Article')
            },
            {
                path: '/about',
                name: 'About',
                component: () => import('../views/About')
            },
            {
                path: '/createArticle',
                name: 'CreateArticle',
                component: () => import('../views/article/CreateArticle')
            }
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue')
    },
    {
        path: '/userCenter',
        name: 'UserLayout',
        redirect: '/userInfo',
        component: UserLayout,
        children: [
            {
                path: '/userAvatar',
                name: 'UserAvatar',
                component: () => import('../views/user/UserAvatar')
            },
            {
                path: '/userInfo',
                name: 'UserInfo',
                component: () => import('../views/user/UserInfo')
            },
            {
                path: '/userPassword',
                name: 'UserPassword',
                component: () => import('../views/user/UserPassword')
            },
            {
                path: '/userSetting',
                name: 'UserSetting',
                component: () => import('../views/user/UserSetting')
            }
        ]
    },
    {
        path: '/module',
        name: 'Module',
        redirect: '/randomModule',
        component: ModuleLayout,
        children: [
            {
                path: '/createModule',
                name: 'CreateModule',
                component: () => import('../views/module/CreateModule')
            },
            {
                path: '/randomModule',
                name: 'RandomModule',
                component: () => import('../views/module/RandomModule')
            },
            {
                path: '/allModule',
                name: 'AllModule',
                component: () => import('../views/module/AllModule')
            },
            {
                path: '/moduleInfo',
                name: 'ModuleInfo',
                component: () => import('../views/module/ModuleInfo')
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
