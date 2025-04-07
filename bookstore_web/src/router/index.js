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
import RecordManage from '@/views/admin/components/record/RecordManage.vue'
import LineManage from '@/views/admin/components/line/LineManage.vue';
import FoodManage from '@/views/admin/components/food/FoodManage.vue';
import NewsManage from '@/views/admin/components/news/NewsManage.vue';
import CommentManage from '@/views/admin/components/comment/CommentManage.vue';
import ReviewManage from '@/views/admin/components/review/ReviewManage.vue';
import OrderManage from '@/views/admin/components/order/OrderManage.vue';
import BookManage from '@/views/admin/components/BookManage.vue';
import UserManage from '@/views/admin/components/UserManage.vue';
import HotRankView from '@/views/rank/HotRankView.vue';
import BestSellerRank from '@/views/rank/BestSellerRank.vue';
import NewBookRank from '@/views/rank/NewBookRank.vue';
import RatingRank from '@/views/rank/RatingRank.vue';


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
        path: '/hotrank/',
        name: 'hot_rank',
        component: HotRankView,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: '/newbookrank/',
        name: 'new_book_rank',
        component: NewBookRank,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: '/bestsellerrank/',
        name: 'best_seller_rank',
        component: BestSellerRank,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: '/ratingrank/',
        name: 'rating_rank',
        component: RatingRank,
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
                path: 'booksManage',
                name: 'books_manage',
                component: BookManage,
            },
            {
                path: 'lineManage',
                name: 'line_manage',
                component: LineManage,
            },
            {
                path: 'foodManage',
                name: 'food_manage',
                component: FoodManage,
            },
            {
                path: 'newsManage',
                name: 'news_manage',
                component: NewsManage,
            },
            {
                path: 'commentManage',
                name: 'comment_manage',
                component: CommentManage,
            },
            {
                path: 'reviewManage',
                name: 'review_manage',
                component: ReviewManage,
            },
            {
                path: 'recordManage',
                name: 'record_Manage',
                component: RecordManage,
            },
            {
                path: 'orderManage',
                name: 'order_manage',
                component: OrderManage,
            },
            {
                path: 'userManage',
                name: 'user_manage',
                component: UserManage,
            }
        ],
        meta: {
            requestAuth: true,
            requestroleId: 1,
        }
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
    console.log(store.state.user.roleId + "id")
    console.log(store.state.user.is_login+ "id")
    if (to.meta.requestAuth && !store.state.user.is_login) {
      next({ name: 'user_login' });
    } else if (to.meta.requestroleId && store.state.user.roleId !== "1") {
      
      next({ name: 'error' });
    } else {
      next();
    }
  });
  
  export default router;
