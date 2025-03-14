<template>
    <div class="container-fluid d-flex p-0" style="height: 100vh; border: 1px solid #eee;">
      <!-- 侧边栏 -->
      <div :style="{ width: asideWidth }" class="bg-light" style="min-height: 100vh;">
        <AdminAsideView :isCollapse="isCollapse" />
      </div>
  
      <!-- 主要内容区域 -->
      <div class="flex-fill">
        <!-- 顶部头部 -->
        <div class="d-flex justify-content-between align-items-center p-2 border-bottom" style="font-size: 12px;">
          <AdminHeaderView @doCollapse="doCollapse" :icon="icon" />
        </div>
  
        <!-- 主体内容 -->
        <div class="main-content p-3" style="height: calc(100vh - 60px);">
          <!-- 路由内容渲染 -->
          <router-view />
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import AdminAsideView from '@/views/admin/components/AdminAsideView.vue';
  import AdminHeaderView from '@/views/admin/components/AdminHeaderView.vue';
  
  export default {
    name: "AdminIndexView",
    components: { AdminAsideView, AdminHeaderView },
    data() {
      return {
        isCollapse: false,
        asideWidth: '200px',
        icon: 'bi bi-caret-right-fill' // 使用 Bootstrap 图标
      };
    },
    methods: {
      doCollapse() {
        this.isCollapse = !this.isCollapse;
        if (!this.isCollapse) {
          this.asideWidth = '200px';
          this.icon = 'bi bi-caret-right-fill'; // 展开
        } else {
          this.asideWidth = '64px';
          this.icon = 'bi bi-caret-left-fill'; // 收起
        }
      }
    }
  };
  </script>
  
  <style scoped>
  /* 自定义一些 Bootstrap 类的样式 */
  .main-content {
    padding-top: 10px;
  }
  
  .el-header {
    background-color: #f8f9fa;
    color: #333;
    line-height: 60px;
  }
  
  .el-main {
    padding: 10px;
  }
  
  .el-aside {
    background-color: #f8f9fa;
    color: #333;
  }
  </style>
  