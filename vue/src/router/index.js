import {createRouter, createWebHistory} from 'vue-router'
import IndexLayout from "@/layout/IndexLayout";
import UserLayout from "@/layout/UserLayout";
import ModuleLayout from "@/layout/ModuleLayout";
import AdminLayout from "@/layout/AdminLayout";

const routes = [
    {
        path: '/',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/admin',
        name: 'Admin',
        component: AdminLayout,
        children: [
            {
                path: '/admin/userManagement',
                name: 'UserManagement',
                component: () => import('../views/admin/UserManagement')
            },
            {
                path: '/admin/moduleManagement',
                name: 'ModuleManagement',
                component: () => import('../views/admin/ModuleManagement')
            },
            {
                path: '/admin/articleManagement',
                name: 'ArticleManagement',
                component: () => import('../views/admin/ArticleManagement')
            },
            {
                path: '/admin/commentManagement',
                name: 'CommentManagement',
                component: () => import('../views/admin/CommentManagement')
            },
        ]
    },
    {
        path: "/index",
        name: 'Layout',
        component: IndexLayout,
        children: [
            {
                path: '/index',
                name: 'Index',
                component: () => import('../views/Index')
            },
            {
                path: '/articleInfo',
                name: 'ArticleInfo',
                component: () => import('../views/article/ArticleInfo')
            },
            {
                path: '/createArticle',
                name: 'CreateArticle',
                component: () => import('../views/article/CreateArticle')
            },
            {
                path: '/about',
                name: 'About',
                component: () => import('../views/About')
            }
        ]
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue')
    },
    {
        path: '/userCenter',
        name: 'UserLayout',
        redirect: '/userAvatar',
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
    }, {
        path: '/test',
        name: 'Test',
        component: () => import('../views/Test')
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
