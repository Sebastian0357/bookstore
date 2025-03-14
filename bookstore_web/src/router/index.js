import { createRouter, createWebHistory } from 'vue-router';
import store from '../store/index';  // Vuex store (假设你使用 Vuex)

import OrderBookIndexView from '@/views/order/OrderBookIndexView.vue';
import BookShelfIndexView from '@/views/bookshelf/BookShelfIndexView.vue';
import NotFoundView from '@/views/error/NotFoundView.vue';
import HomeIndexView from '@/views/home/HomeIndexView.vue';
import UserAccountLoginView from '@/views/user/UserAccountLoginView.vue';
import UserAccountRegisterView from '@/views/user/UserAccountRegisterView.vue';
import UserIndexView from '@/views/user/UserIndexView.vue';
import BookDetailView from '@/views/book/BookDetailView.vue';
import AdminIndexView from '@/views/admin/components/AdminIndexView.vue';
import AdminHomeView from '@/views/admin/components/AdminHomeView.vue';
import AdminManage from '@/views/admin/components/admin/AdminManage.vue'
import BooksManage from '@/views/admin/components/books/BooksManage.vue'
import GoodstypeManage from '@/views/admin/components/goodstype/GoodstypeManage.vue'
import RecordManage from '@/views/admin/components/record/RecordManage.vue'
import StorageManage from '@/views/admin/components/storage/StorageManage.vue'
import UserManage from '@/views/admin/components/user/UserManage.vue'

const routes = [
  {
    path: '/user/login/',
    name: 'user_login',
    component: UserAccountLoginView,
  },
  {
    path: '/',
    name: 'index',
    redirect: '/home/',
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/home/',
    name: 'home',
    component: HomeIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/order/',
    name: 'order_index',
    component: OrderBookIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/shelf/',
    name: 'shelf_index',
    component: BookShelfIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/user/',
    name: 'user_index',
    component: UserIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: '/user/register/',
    name: 'user_register',
    component: UserAccountRegisterView,
  },
  {
    path: '/book/:id',
    name: 'book',
    component: BookDetailView,
  },
  
  {
    path: '/adminindex',
    name: 'admin_index',
    component: AdminIndexView,
    children: [
      {
        path: 'adminhome',  // 不需要斜杠开头
        name: 'admin_home',
        meta: {
          title: '首页',
        },
        component: AdminHomeView,
      },
      {
        path: 'adminmanage',
        name: 'AdminManage',
        component: AdminManage,
      },
      {
        path: 'booksmanage',
        name: 'books_manage',
        component: BooksManage,
      },
      {
        path: 'goodstypemanage',
        name: 'GoodstypeManage',
        component: GoodstypeManage,
      },
      {
        path: 'recordmanage',
        name: 'RecordManage',
        component: RecordManage,
      },
      {
        path: 'storage-manage',
        name: 'StorageManage',
        component: StorageManage,
      },
      {
        path: 'user-manage',  // 修改路径为相对路径
        name: 'UserManage',
        component: UserManage,
      }
    ]
  },
  {
    path: '/error/',
    name: 'error',
    component: NotFoundView,
  },
  {
    path: '/:catchAll(.*)',
    redirect: '/error/',
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const isLogin = store.state.user.is_login;
  const roleId = parseInt(localStorage.getItem("roleid"));

  if (to.meta.requestAuth && !isLogin) {
    next({ name: 'user_login' });
  } else if (to.path.startsWith('/adminindex') && roleId !== 1) {
    next('/error'); // 如果不是管理员，访问后台跳转错误页
  } else {
    next();
  }
});

export default router;
