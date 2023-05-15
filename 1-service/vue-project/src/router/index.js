import { createRouter, createWebHashHistory } from 'vue-router'

import Index from '../views/index.vue'
import Login from '../views/login.vue'
import Test from '../views/test.vue'



const routes = [
    { path: '/', component: Index },
    { path: '/login', component: Login },
    { path: '/test', component: Test },
]

export default createRouter({
    history: createWebHashHistory(),
    routes,
})
