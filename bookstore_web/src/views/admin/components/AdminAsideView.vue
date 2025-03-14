<template>
  <div class="d-flex flex-column" style="height: 100%; background-color: #545c64;">
    <div class="d-flex align-items-center justify-content-center" style="height: 60px; color: white;">
      <i class="bi bi-house-door-fill" style="font-size: 24px;"></i>
      <span class="ms-2">首页</span>
    </div>

    <!-- 固定菜单项 -->
    <!-- <div class="d-flex align-items-center px-3 py-2">
      <i class="bi bi-person-fill" style="font-size: 20px;"></i>
      <router-link to="/adminindex/user-management" class="text-white ms-2"
        style="text-decoration: none;">管理员管理</router-link>
    </div> -->
    <div class="d-flex align-items-center px-3 py-2">
      <i class="bi bi-person-lines-fill" style="font-size: 20px;"></i>
      <router-link to="/adminindex/adminmanage" class="text-white ms-2" style="text-decoration: none;">用户管理</router-link>
    </div>
    <div class="d-flex align-items-center px-3 py-2">
      <i class="bi bi-book-fill" style="font-size: 20px;"></i>
      <router-link to="/adminindex/booksmanage" class="text-white ms-2" style="text-decoration: none;">书籍管理</router-link>
    </div>
    <div class="d-flex align-items-center px-3 py-2">
      <i class="bi bi-file-earmark-text-fill" style="font-size: 20px;"></i>
      <router-link to="/adminindex/goodstypemanage" class="text-white ms-2"
        style="text-decoration: none;">书籍分类管理</router-link>
    </div>
    <div class="d-flex align-items-center px-3 py-2">
      <i class="bi bi-chat-left-text-fill" style="font-size: 20px;"></i>
      <router-link to="/adminindex/recordmanage" class="text-white ms-2"
        style="text-decoration: none;">书评管理</router-link>
    </div>
    <div class="d-flex align-items-center px-3 py-2">
      <i class="bi bi-cart-fill" style="font-size: 20px;"></i>
      <router-link to="/adminindex/order-management" class="text-white ms-2"
        style="text-decoration: none;">订单管理</router-link>
    </div>

  </div>
</template>

<script>
export default {
  name: "AdminAsideView",
  props: {
    isCollapse: Boolean // 导航栏菜单伸缩
  }
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
