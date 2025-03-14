<template>
  <NavBar></NavBar>
  <!-- / v-if="showNavBar" -->
  <router-view />
</template>

<script>
import NavBar from "./components/NavBar.vue";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap"
export default {
  components: {
    NavBar,
  },
  data() {
    return {
      showNavBar: false
    };
  },
  created() {
    this.checkRole();
  },
  watch: {
    // 监听路由变化和登录状态
    $route() {
      this.checkRole();
    },
    'store.state.user.is_login'(newVal) {
      if (!newVal) this.checkRole(); // 退出后刷新导航栏状态
    },
  },
  methods: {
    checkRole() {
      const roleid = localStorage.getItem('roleid');
      this.showNavBar = roleid !== "1"; // roleid 为 "1" 隐藏 NavBar
    },
  },

};
</script>
<style>
body {
  background-image: url("@/assets/images/back.png");
  background-size: cover;
  height: 100%;
}
</style>
