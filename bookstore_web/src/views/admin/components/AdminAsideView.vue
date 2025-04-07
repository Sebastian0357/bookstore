<template>
  <div class="d-flex flex-column" style="height: 100%; background-color: #545c64;">
    <div class="d-flex align-items-center justify-content-center" style="height: 60px; color: white;">
      <i class="bi bi-house-door-fill" style="font-size: 24px;"></i>
      <router-link to="/adminindex/adminhome" class="text-white ms-2" style="text-decoration: none;">首页</router-link>

    </div>

    <!-- 固定菜单项 -->
    <div class="d-flex align-items-center px-3 py-2">
      <i class="bi bi-person-lines-fill" style="font-size: 20px;"></i>
      <router-link to="/adminindex/usermanage" class="text-white ms-2" style="text-decoration: none;">用户管理</router-link>
    </div>
    <div class="d-flex align-items-center px-3 py-2">
      <i class="bi bi-book-fill" style="font-size: 20px;"></i>
      <router-link to="/adminindex/booksmanage" class="text-white ms-2" style="text-decoration: none;">图书管理</router-link>
    </div>
    <div class="d-flex align-items-center px-3 py-2">
      <i class="bi bi-book-fill" style="font-size: 20px;"></i>
      <router-link to="/adminindex/linemanage" class="text-white ms-2" style="text-decoration: none;">线路管理</router-link>
    </div>
    <div class="d-flex align-items-center px-3 py-2">
      <i class="bi bi-book-fill" style="font-size: 20px;"></i>
      <router-link to="/adminindex/foodmanage" class="text-white ms-2" style="text-decoration: none;">美食管理</router-link>
    </div>
    <div class="d-flex align-items-center px-3 py-2">
      <i class="bi bi-file-earmark-text-fill" style="font-size: 20px;"></i>
      <router-link to="/adminindex/newsmanage" class="text-white ms-2"
        style="text-decoration: none;">新闻管理</router-link>
    </div>
    <div class="d-flex align-items-center px-3 py-2">
      <i class="bi bi-chat-left-text-fill" style="font-size: 20px;"></i>
      <router-link to="/adminindex/commentManage" class="text-white ms-2"
        style="text-decoration: none;">评论管理</router-link>
    </div>
    <div class="d-flex align-items-center px-3 py-2">
      <i class="bi bi-chat-left-text-fill" style="font-size: 20px;"></i>
      <router-link to="/adminindex/reviewManage" class="text-white ms-2"
        style="text-decoration: none;">留言管理</router-link>
    </div>
    <div class="d-flex align-items-center px-3 py-2">
      <i class="bi bi-cart-fill" style="font-size: 20px;"></i>
      <router-link to="/adminindex/orderManage" class="text-white ms-2"
        style="text-decoration: none;">订单管理</router-link>
    </div>
    <!-- 退出登录 -->
    <div class="d-flex align-items-center px-3 py-2 logout-btn" @click="logout">
      <i class="bi bi-box-arrow-right" style="font-size: 20px;"></i>
      <span class="text-white ms-2" style="cursor: pointer;">退出登录</span>
    </div>

  </div>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
export default {
  name: "AdminAsideView",
  props: {
    isCollapse: Boolean // 导航栏菜单伸缩
  },
  setup() {
    const store = useStore();
    const router = useRouter();

    const logout = () => {
      store.dispatch("logout"); // Vuex 清除用户状态
      localStorage.removeItem("token"); // 清除本地存储
      localStorage.removeItem("roleId");
      router.push("/user/login/"); // 跳转到登录页
    };

    return {
      logout,
    };
  },
}
</script>

<style scoped>
/* 侧边栏样式 */
.d-flex {
  display: flex;
}

.flex-column {
  flex-direction: column;
}

.ms-2 {
  margin-left: 0.5rem;
}

.px-3 {
  padding-left: 1rem;
  padding-right: 1rem;
}

.py-2 {
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
}

.text-white {
  color: #fff;
}

.text-decoration-none {
  text-decoration: none;
}

.bg-dark {
  background-color: #545c64;
}
</style>
