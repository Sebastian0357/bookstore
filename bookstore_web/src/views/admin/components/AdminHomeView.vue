<template>
    <div style="text-align: center; background-color: #f1f1f3; height: 100%; padding: 0; margin: 0;">
      <h1 style="font-size: 50px;">{{ '欢迎你！' + user.name }}</h1>
      
      <div class="container mt-4">
        <h3>个人中心</h3>
        <div class="row justify-content-center">
          <div class="col-md-6">
            <ul class="list-group">
              <li class="list-group-item d-flex justify-content-between align-items-center">
                账号
                <span>{{ user.no }}</span>
              </li>
              <li class="list-group-item d-flex justify-content-between align-items-center">
                电话
                <span>{{ user.phone }}</span>
              </li>
              <li class="list-group-item d-flex justify-content-between align-items-center">
                性别
                <span>
                  <span :class="user.sex === '1' ? 'text-primary' : 'text-danger'">
                    <i :class="user.sex === '1' ? 'bi bi-gender-male' : 'bi bi-gender-female'"></i>
                    {{ user.sex === '1' ? "男" : "女" }}
                  </span>
                </span>
              </li>
              <li class="list-group-item d-flex justify-content-between align-items-center">
                角色
                <span>
                  <span class="badge" :class="user.roleId === 0 ? 'bg-success' : (user.roleId === 1 ? 'bg-info' : 'bg-secondary')">
                    {{ user.roleId === 0 ? "超级管理员" : (user.roleId === 1 ? "管理员" : "用户") }}
                  </span>
                </span>
              </li>
            </ul>
          </div>
        </div>
      </div>
      
      <DateUtils></DateUtils>
    </div>
  </template>
  
  <script>
  import DateUtils from '@/views/admin/components/DateUtils.vue';
  
  export default {
    name: "AdminHomeView",
    components: { DateUtils },
    data() {
      return {
        user: {}
      };
    },
    methods: {
      init() {
        this.user = {
          no: "jack",
          name: "Jack",
          phone: "1234567890",
          sex: "1",  // 1 represents male, 2 represents female
          roleId: 0  // 0 = Super Admin, 1 = Admin, 2 = User
        };
      }
    },
    created() {
      this.init();
    }
  };
  </script>
  
  <style scoped>
  .el-descriptions {
    width: 90%;
    margin: 0 auto;
    text-align: center;
  }
  </style>
  