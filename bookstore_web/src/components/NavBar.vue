<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
      <router-link class="navbar-brand" :to="{ name: 'home' }"
        >乡村书屋</router-link
      >
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarText"
        aria-controls="navbarText"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarText">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'home' }"
            active-class="active"
              >首页</router-link
            >
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'hot_rank' }"
            active-class="active"
              >热度榜</router-link
            >
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'new_book_rank' }"
            active-class="active"
              >新书榜</router-link
            >
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'best_seller_rank' }"
            active-class="active"
              >畅销榜</router-link 
            >
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'rating_rank' }"
            active-class="active"
              >评分榜</router-link
            >
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'shelf_index' }"
            active-class="active"
              >个人书架</router-link
            >
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'order_index' }"
            active-class="active"
              >我的订单</router-link
            >
          </li>
        </ul>
        <ul class="navbar-nav" v-if="$store.state.user.is_login">
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="navbarDropdown"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              {{ $store.state.user.username }}
            </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <li>
                <router-link
                  class="dropdown-item"
                  :to="{ name: 'user_index' }"
                  >个人中心</router-link
                >
              </li>
              <li><hr class="dropdown-divider" /></li>
              <li>
                <a class="dropdown-item" href="#" @click="logout">退出</a>
              </li>
            </ul>
          </li>
        </ul>
        <ul class="navbar-nav" v-else-if="!$store.state.user.pulling_info">
          <li class="nav-item">
            <!-- 在相关页面使用router-link （其中的to中的内容是指转向的页面） -->
            <router-link
              class="nav-link"
              :to="{ name: 'user_login' }"
              role="button"
            >
              登录
            </router-link>
          </li>
          <li class="nav-item">
            <router-link
              class="nav-link"
              :to="{ name: 'user_register' }"
              role="button"
            >
              注册
            </router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
import { useRoute } from "vue-router";
import { computed } from "vue";
import { useStore } from "vuex";
export default {
  setup() {
    const store = useStore();
    const route = useRoute();
    let route_name = computed(() => route.name);

    const logout = () => {
      store.dispatch("logout");
    };

    return {
      route_name,
      logout,
    };
  },
};
</script>

<!-- scoped使组件样式不影响其他组件 -->
<style scoped>
</style>