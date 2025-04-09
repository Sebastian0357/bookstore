<template>
    <div class="d-flex" style="height: 100vh; border: 1px solid #eee;">
      <!-- 侧边栏 -->
      <div :style="{ width: asideWidth }" class="bg-light" style="height: 100vh; border-right: 1px solid #ccc;">
        <AdminAsideView :isCollapse="isCollapse" />
      </div>
  
      <!-- 主体区域 -->
      <div class="flex-fill d-flex flex-column" style="background-color: #f8f9fa;">
        <!-- 顶部栏 -->
        <div class="d-flex justify-content-between align-items-center p-2 border-bottom" style="font-size: 12px;">
          <AdminHeaderView @doCollapse="doCollapse" :icon="icon" />
        </div>
  
        <!-- 主体内容 -->
        <div class="flex-fill p-3" style="overflow-y: auto;">
          <router-view />
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import AdminAsideView from '@/views/admin/AdminAsideView.vue';
  import AdminHeaderView from '@/views/admin/AdminHeaderView.vue';
  
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
  
  .bg-light {
    background-color: #f8f9fa !important;
  }
  
  .el-header {
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
  