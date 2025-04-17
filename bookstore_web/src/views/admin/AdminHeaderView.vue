<template>
    <div class="d-flex align-items-center" style="line-height: 60px;">
      <!-- 菜单伸缩 -->
      <div style="margin-top: 8px;">
        <i :class="icon" class="fs-4 cursor-pointer" @click="collapse"></i>
      </div>
      
      <!-- 欢迎字样 -->
      <div class="flex-grow-1 text-center" style="font-size: 34px;">
        <span>乡村书屋</span>
      </div>
      
      <!-- 用户信息及下拉菜单 -->
      <!-- <div class="dropdown">
        <span>{{ this.$store.state.user.username }}</span>
        <i class="bi bi-chevron-down" style="margin-left: 5px;"></i>
        
        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
          <li><a class="dropdown-item" @click="toUser">个人中心</a></li>
          <li><a class="dropdown-item" @click="logout">退出登录</a></li>
        </ul>
      </div> -->
    </div>
  </template>
  
  <script>
  export default {
    name: "AdminHeaderView",
    data() {
      return {
      }
    },
    props: {
      icon: String
    },
    methods: {
      logout() {
        console.log('logout');
  
        // Bootstrap confirmation (for demo purposes)
        if (window.confirm('您确定要退出登录吗？')) {
          this.$message({
            type: 'success',
            message: '退出登录成功！'
          });
  
          this.$router.push("/");
          sessionStorage.clear();
        } else {
          this.$message({
            type: 'info',
            message: '已取消退出登录！'
          });
        }
      },
      collapse() {
        this.$emit('doCollapse');
      }
    },
  }
  </script>
  
  <style scoped>
  /* Additional custom styling if needed */
  </style>
  