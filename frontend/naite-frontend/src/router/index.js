import Vue from 'vue'
import VueRouter from 'vue-router'
import Sign from '@/views/Sign/Sign'
import MainBoard from '@/views/Board/MainBoard'
import Board from '@/views/Board/Board'
import Profile from '@/views/Profile'
import MarketBoard from '@/views/MarketBoard'
import PostingForm from '@/views/PostingForm'
import Posting from '@/views/Posting'
import Test from '@/views/Test'
import ProfileSettings from '@/views/ProfileSettings'

Vue.use(VueRouter)
const routes = [
  {
    path:'/sign',
    name: 'Sign',
    component: Sign,
  },
  {
    path:'/mainboard',
    name: 'MainBoard',
    component: MainBoard,
  },
  {
    path:'/board/:bigCategoryNo',
    name: 'Board',
    component: Board,
  },
  {
    path:'/profile',
    name: 'Profile',
    component: Profile,
  },
  {
    path:'/marketboard',
    name: 'MarketBoard',
    component: MarketBoard,
  },
  {
    path:'/postingform',
    name: 'PostingForm',
    component: PostingForm,
  },
  {
    path:'/posting',
    name: 'Posting',
    component: Posting,
  },
  {
    path:'/test',
    name: 'Test',
    component: Test,
  },
  {
    path:'/profilesettings',
    name: 'ProfileSettings',
    component: ProfileSettings,
  },
]
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
export default router
