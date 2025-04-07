<template>
  <div style="text-align: center; background-color: #f1f1f3; height: 100%; padding: 0; margin: 0;">
    <h1 style="font-size: 50px;">{{ '欢迎你！' + user.username }}</h1>

    <div class="container mt-4">
      <h3>个人中心</h3>
      <div class="row justify-content-center">
        <div class="col-md-6">
          <ul class="list-group">
            <li class="list-group-item d-flex justify-content-between align-items-center">
              账号
              <span>{{ user.username }}</span>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              电话
              <span>{{ user.phonenumber }}</span>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              性别
              <span>
                <span :class="user.sex === '1' ? 'text-primary' : 'text-danger'">
                  <i :class="user.sex === '1' ? 'bi bi-gender-male' : 'bi bi-gender-female'"></i>
                  {{ user.sex === '1' ? "女" : "男" }}
                </span>
              </span>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              角色
              <span>
                <span class="badge" :class="user.roleId === '1' ? 'bg-info' : 'bg-secondary'">
                  {{ user.roleId === "1" ? "用户" : "管理员" }}
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
import axios from 'axios';

export default {
  name: "AdminHomeView",
  components: { DateUtils },
  data() {
    return {
      user: {}
    };
  },
  methods: {
    loadPost() {
      axios
        .post(
          'http://localhost:1118/user/detail',
          { id: this.$store.state.user.id }, {  // 传 JSON 对象
          headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") },
        }).then(res => {
          if (res.data.code === 200) {
            this.user = res.data.data;
          } else {
            alert("获取个人信息失败")
          }
        });
    },
  },
  mounted() {
    this.loadPost()
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