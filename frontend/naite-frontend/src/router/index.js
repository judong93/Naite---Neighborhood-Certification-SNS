import Vue from 'vue'
import VueRouter from 'vue-router'
import Firstpage from '@/views/Firstpage'
import Sign from '@/views/Sign'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Firstpage',
    component: Firstpage
  },
  {
    path:'/sign',
    name: 'Sign',
    component: Sign,

  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
