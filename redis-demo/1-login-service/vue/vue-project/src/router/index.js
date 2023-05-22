import { createRouter, createWebHistory } from 'vue-router'
import Template from '../views/template/Template.vue'


const routes = [
    { path: '/', name: 'home', component: () => import('../views/home/Home.vue') },
    { path: '/login', name: 'login', component: () => import('../views/login/Login.vue') },
    { path: '/controller', name: 'controller', component: () => import('../views/controller/Controller.vue'), meta: { requireAuth: true } },
    { path: '/template', name: 'template', component: Template },
  ]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

// 路由守卫
router.beforeEach((to,from,next) => {
  // 如果目标路由需要登录，则检查是否存在 token
  if(to.meta.requireAuth && !localStorage.getItem('token')) {
      next({ name: 'login' })
  } else {
      next()
  }
})

export default router
