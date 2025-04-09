<template>
  <div>
    <div style="margin-bottom: 5px;">
      <div class="input-group">
        <input v-model="name" type="text" class="form-control" placeholder="请输入名字" @keyup.enter="loadPost"
          style="max-width: 200px;">
        <button class="btn btn-primary" style="margin-left: 5px;" @click="loadPost">查询</button>
        <button class="btn btn-success" style="margin-left: 5px;" @click="resetParam">重置</button>
      </div>
    </div>

    <!-- Bootstrap 表格 -->
    <table class="table table-bordered">
      <thead>
        <tr>
          <th>ID</th>
          <th>用户名</th>
          <th>生日</th>
          <th>性别</th>
          <th>角色</th>
          <th>电话</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="row in tableData" :key="row.id">
          <td>{{ row.id }}</td>
          <td>{{ row.username }}</td>
          <td>{{ row.birthday }}</td>
          <td>
            <span :class="row.sex === '男' ? 'badge bg-primary' : 'badge bg-success'">
              {{ row.sex }}
            </span>
          </td>
          <td>
            <span :class="row.role === '0' ? 'badge bg-warning' : 'badge bg-success'">
              {{ row.role === '1' ? '管理员' : '用户' }}
            </span>
          </td>
          <td>{{ row.phonenumber }}</td>
          <td>
            <button class="btn btn-sm" :class="row.role === '1' ? 'btn-warning' : 'btn-primary'"
              @click="toggleRole(row)">
              {{ row.role === '1' ? '移除管理员' : '设为管理员' }}
            </button>
            <button class="btn btn-danger btn-sm ms-2" @click="del(row.id)">删除</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 分页 -->
    <nav>
      <ul class="pagination justify-content-center">
        <li class="page-item" :class="{ disabled: pageNum === 1 }">
          <a class="page-link" href="#" @click="handleCurrentChange(pageNum - 1)">上一页</a>
        </li>
        <li class="page-item" v-for="n in Math.ceil(total / pageSize)" :key="n" :class="{ active: pageNum === n }">
          <a class="page-link" href="#" @click="handleCurrentChange(n)">{{ n }}</a>
        </li>
        <li class="page-item" :class="{ disabled: pageNum === Math.ceil(total / pageSize) }">
          <a class="page-link" href="#" @click="handleCurrentChange(pageNum + 1)">下一页</a>
        </li>
      </ul>
    </nav>

  </div>
  <!-- 成功/失败消息 -->
  <MessageBox ref="messageBox" />
</template>

<script>
import axios from 'axios';
import MessageBox from '@/components/MessageBox.vue';// 引入MessageBox组件

export default {
  components: {
    MessageBox
  },
  name: "UserManage",
  data() {
    return {
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name: '',
      sex: '',
      form: {
        id: '',
        no: '',
        name: '',
        password: '',
        age: '',
        phone: '',
        sex: '',
        roleId: ''
      }
    }
  },
  methods: {
    resetParam() {
      this.name = '';
      this.loadPost();
    },
    loadPost() {
      axios.post('http://localhost:1118/user/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name
        }
      }, {
        headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") },
      }).then(res => {
        if (res.data.code === 200) {
          this.tableData = res.data.data;
          this.total = res.data.total;
        } else {
          alert('获取数据失败');
        }
      });
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.loadPost();
    },
    toggleRole(user) {
      axios
        .post(
          'http://localhost:1118/user/updateRole', user, {
          headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") },
        }).then(res => {
          if (res.data.code === 200) {
            this.$refs.messageBox.showFeedback('success', '修改成功');
            this.loadPost()
          } else {
            this.$refs.messageBox.showFeedback('error', '修改失败');

          }
        });
    },
    del(id) {
      axios.post('http://localhost:1118/user/delete', { id: id }, {  // 传 JSON 对象
        headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") },
      }).then(res => {
        if (res.data.code === 200) {
          this.$refs.messageBox.showFeedback('success', '删除数据成功');
          this.loadPost();
        } else {
          this.$refs.messageBox.showFeedback('error', '删除数据失败');

        }
      });
    },
  },
  beforeMount() {
    this.loadPost();
  }
}
</script>

<style scoped></style>