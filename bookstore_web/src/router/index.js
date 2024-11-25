import AudioBookIndexView from '@/views/audio/AudioBookIndexView.vue'
import BookShelfIndexView from '@/views/bookshelf/BookShelfIndexView.vue'
import NotFoundView from '@/views/error/NotFoundView.vue'
import HomeIndexView from '@/views/home/HomeIndexView.vue'
import UserAccountLoginView from '@/views/user/UserAccountLoginView.vue'
import UserAccountRegisterView from '@/views/user/UserAccountRegisterView.vue'
import UserIndexView from '@/views/user/UserIndexView.vue'
import { createRouter, createWebHistory } from 'vue-router'
import store from '../store/index'
import GoodsManage from '@/views/goods/GoodsManage.vue'

const routes = [
  {
    path: "/user/login/",
    name: "user_login",
    component: UserAccountLoginView,
  },
  {
    path: "/",
    name:"index",
    redirect: "/home/",
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/home/",
    name: "home",
    component: HomeIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/audio/",
    name: "audio_index",
    component: AudioBookIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/shelf/",
    name: "shelf_index",
    component: BookShelfIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/user/",
    name: "user_index",
    component: UserIndexView,
    meta: {
      requestAuth: true,
    }
  },

  {
    path: "/user/register/",
    name: "user_register",
    component: UserAccountRegisterView,
  },
  {
    path: "/GoodsManage/",
    name: "good_manage",
    component: GoodsManage,
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

router.beforeEach((to, from, next) => {
  if (to.meta.requestAuth && !store.state.user.is_login) {
    next({name: "user_login"});
  } else {
    next();
  }
})

export default router
