<template>
    <NavBar v-if="showNavBar"></NavBar>
    <router-view />
</template>

<script>
import NavBar from "./components/NavBar.vue";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";

import { useRoute } from "vue-router"
import { computed } from "vue";

export default {
    components: {
        NavBar,
    },
    setup() {
        const route = useRoute(); // 正确引入路由信息

        // 计算属性：判断是否显示 NavBar
        const showNavBar = computed(() => {
            return !(
                route.path.startsWith("/adminindex/")
            );
        });

        return {
            showNavBar, // 返回变量供模板使用
        };
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