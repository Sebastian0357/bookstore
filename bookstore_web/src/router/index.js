import AudioBookIndexView from '@/views/audio/AudioBookIndexView.vue'
import BookShelfIndexView from '@/views/bookshelf/BookShelfIndexView.vue'
import NotFoundView from '@/views/error/NotFoundView.vue'
import HomeIndexView from '@/views/home/HomeIndexView.vue'
import UserAccountLoginView from '@/views/user/UserAccountLoginView.vue'
import UserAccountRegisterView from '@/views/user/UserAccountRegisterView.vue'
import UserIndexView from '@/views/user/UserIndexView.vue'
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: "/",
    name:"index",
    redirect: "/home/",
  },
  {
    path: "/home/",
    name: "home",
    component: HomeIndexView,
  },
  {
    path: "/audio/",
    name: "audio_index",
    component: AudioBookIndexView,
  },
  {
    path: "/shelf/",
    name: "shelf_index",
    component: BookShelfIndexView,
  },
  {
    path: "/user/",
    name: "user_index",
    component: UserIndexView,
  },
  {
    path: "/user/login/",
    name: "user_login",
    component: UserAccountLoginView,
  },
  {
    path: "/user/register/",
    name: "user_register",
    component: UserAccountRegisterView,
  },
  {
    path: "/error/",
    name: "error",
    component: NotFoundView,
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/error/",
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
