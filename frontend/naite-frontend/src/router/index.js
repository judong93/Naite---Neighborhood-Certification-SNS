import Vue from 'vue'
import VueRouter from 'vue-router'
import Sign from '@/views/Sign/Sign'
import MainBoard from '@/views/Board/MainBoard'
import Board from '@/views/Board/Board'
import BoardDetail from '@/views/Board/BoardDetail'
import Profile from '@/views/Profile/Profile'
import MarketBoard from '@/views/MarketBoard/MarketBoard'
import MarketBoardDetail from '@/views/MarketBoard/MarketBoardDetail'
import ReviewBoard from '@/views/ReviewBoard/ReviewBoard'
import ReviewBoardDetail from '@/views/ReviewBoard/ReviewBoardDetail'
import PostingForm from '@/views/PostingForm'
import Posting from '@/views/Posting'
import MobileMessage from '@/views/MobileMessage'
import Test from '@/views/Test'
import ProfileSettings from '@/views/Profile/ProfileSettings'


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
    path:'/board/detail/:boardNo',
    name: 'BoardDetail',
    component: BoardDetail,
  },
  {
    path:'/profile/:userNo',
    name: 'Profile',
    component: Profile,
  },
  {
    path:'/marketboard',
    name: 'MarketBoard',
    component: MarketBoard,
  },
  {
    path:'/marketboard/detail/:marketNo',
    name: 'MarketBoardDetail',
    component: MarketBoardDetail,
  },
  {
    path:'/reviewboard',
    name: 'ReviewBoard',
    component:ReviewBoard,
  },
  {
    path:'/review/detail/:reviewNo/:boardNo?',
    name: 'ReviewBoardDetail',
    component:ReviewBoardDetail,
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
    path:'/',
    name: 'Main',
    component: Test,
  },
  {
    path:'/profilesettings',
    name: 'ProfileSettings',
    component: ProfileSettings,
  },
  {
    path:'/mobileMessage',
    name: 'MobileMessage',
    component:MobileMessage,
  }
]
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
export default router
