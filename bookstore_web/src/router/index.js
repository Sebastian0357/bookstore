import OrderBookIndexView from '@/views/order/OrderBookIndexView.vue'
import BookShelfIndexView from '@/views/bookshelf/BookShelfIndexView.vue'
import NotFoundView from '@/views/error/NotFoundView.vue'
import HomeIndexView from '@/views/home/HomeIndexView.vue'
import UserAccountLoginView from '@/views/user/UserAccountLoginView.vue'
import UserAccountRegisterView from '@/views/user/UserAccountRegisterView.vue'
import UserIndexView from '@/views/user/UserIndexView.vue'
import { createRouter, createWebHistory } from 'vue-router'
import store from '../store/index'
import BookDetailView from '@/views/book/BookDetailView.vue'

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
    path: "/order/",
    name: "order_index",
    component: OrderBookIndexView,
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
    path: "/book/",
    name: "book",
    component: BookDetailView,
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
