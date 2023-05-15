import { createRouter, createWebHashHistory } from 'vue-router'

import Index from '../views/index.vue'
import Login from '../views/login.vue'
import Test from '../template/test.vue'

import Controller from '../views/controller/controller.vue'
import ControllerUser from '../views/controller/user/user.vue'


const routes = [
    { path: '/', component: Index },
    { path: '/login', name: 'Login', component: Login },
    { path: '/test', component: Test, meta: { requireAuth: true } },
    { path: '/controller', component: Controller, meta: { requireAuth: true } },
    { path: '/controller/user', component: ControllerUser, meta: { requireAuth: true } },
]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
})

// 路由守卫
router.beforeEach((to,from,next) => {
    // 如果目标路由需要登录，则检查是否存在 token
    if(to.meta.requireAuth && !localStorage.getItem('token')) {
        next({ name: 'Login' })
    } else {
        next()
    }
})

export default router