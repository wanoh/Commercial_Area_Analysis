import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/components/home/Home.vue'
// import About from '@/components/about/About.vue'
// import Result from '@/components/result/Result.vue'
// import ResultSearch from '@/components/result/ResultSearch.vue'
import map from '@/components/bizmap/kakaomap/KakaoMap.vue'
import MyPage from '@/components/mypage/MyPage.vue'
import Modal from '@/components/modal/Modal.vue'
import tap from '@/components/countstore/CountStores.vue'
import result from '@/components/modal/result/Result.vue'
import loading from '@/components/bizmap/loading/Loading.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/mypage',
    name: 'mypage',
    component: MyPage
  },
  {
    path: '/bizmap',
    name: 'bizmap',
    component: map
  },
  {
    path: '/modal',
    name: 'modal',
    component: Modal
  },
  {
    path: '/tap',
    name: 'tap',
    component: tap
  },
  {
    path: '/result',
    name: 'result',
    component: result
  },
  {
    path: '/loading',
    name: 'loading',
    component: loading
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
