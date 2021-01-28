import Vue from 'vue'
import VueRouter from 'vue-router'
import Firstpage from '@/views/Firstpage'
import Sign from '@/views/Sign'
import Main from '@/views/Main'
import Profile from '@/views/Profile'
import GroupBuying from '@/views/GroupBuying'
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
  },
  {
    path:'/main',
    name: 'Main',
    component: Main,
  },
  {
    path:'/profile',
    name: 'Profile',
    component: Profile,
  },
  {
    path:'/groupbuying',
    name: 'GroupBuying',
    component: GroupBuying,
  }
]
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
export default router
