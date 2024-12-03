<template>
  <NavBar v-if="!$store.state.user.is_admin"></NavBar>
  <AdminHeader v-if="$store.state.user.is_admin"></AdminHeader>
  <router-view />
</template>

<script>
import NavBar from "./components/NavBar.vue";
import AdminHeader from "./components/AdminHeader.vue";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap"
export default {
  components: {
    NavBar,
    AdminHeader
  }
};
</script>
<style>
body {
  background-image: url("@/assets/images/back.png");
  background-size: cover;
  height: 100%;
}
</style>
