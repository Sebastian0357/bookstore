<template>
    <div>
      <div style="margin-bottom: 5px;">
        <div class="input-group">
          <input v-model="name" type="text" class="form-control" placeholder="请输入名字" @keyup.enter="loadPost">
          <select v-model="sex" class="form-select ms-2">
            <option v-for="item in sexs" :key="item.value" :value="item.value">{{ item.label }}</option>
          </select>
          <button class="btn btn-primary ms-2" @click="loadPost">查询</button>
          <button class="btn btn-secondary ms-2" @click="resetParam">重置</button>
          <button class="btn btn-success ms-2" @click="add">新增</button>
        </div>
      </div>
  
      <!-- Bootstrap 表格 -->
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>ID</th>
            <th>账号</th>
            <th>姓名</th>
            <th>年龄</th>
            <th>性别</th>
            <th>角色</th>
            <th>电话</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(row, index) in tableData" :key="index">
            <td>{{ row.id }}</td>
            <td>{{ row.no }}</td>
            <td>{{ row.name }}</td>
            <td>{{ row.age }}</td>
            <td>
              <span :class="row.sex === 1 ? 'badge bg-primary' : 'badge bg-success'">
                {{ row.sex === 1 ? '男' : '女' }}
              </span>
            </td>
            <td>
              <span :class="row.roleId === 0 ? 'badge bg-danger' : (row.roleId === 1 ? 'badge bg-primary' : 'badge bg-success')">
                {{ row.roleId === 0 ? '超级管理员' : (row.roleId === 1 ? '管理员' : '用户') }}
              </span>
            </td>
            <td>{{ row.phone }}</td>
            <td>
              <button class="btn btn-success btn-sm" @click="mod(row)">编辑</button>
              <button class="btn btn-danger btn-sm ms-2" @click="del(row.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
  
      <!-- 分页 -->
      <nav>
        <ul class="pagination justify-content-center">
          <li class="page-item" :class="{ disabled: pageNum === 1 }">
            <button class="page-link" @click="handleCurrentChange(pageNum - 1)">上一页</button>
          </li>
          <li class="page-item" v-for="page in totalPages" :key="page" :class="{ active: pageNum === page }">
            <button class="page-link" @click="handleCurrentChange(page)">{{ page }}</button>
          </li>
          <li class="page-item" :class="{ disabled: pageNum === totalPages }">
            <button class="page-link" @click="handleCurrentChange(pageNum + 1)">下一页</button>
          </li>
        </ul>
      </nav>
  
      <!-- Bootstrap Modal -->
      <div class="modal fade" id="userModal" tabindex="-1" aria-labelledby="userModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="userModalLabel">用户信息</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <form @submit.prevent="save">
                <div class="mb-3">
                  <label for="no" class="form-label">账号</label>
                  <input v-model="form.no" type="text" class="form-control" id="no" required />
                </div>
                <div class="mb-3">
                  <label for="name" class="form-label">名字</label>
                  <input v-model="form.name" type="text" class="form-control" id="name" required />
                </div>
                <div class="mb-3">
                  <label for="password" class="form-label">密码</label>
                  <input v-model="form.password" type="password" class="form-control" id="password" required />
                </div>
                <div class="mb-3">
                  <label for="age" class="form-label">年龄</label>
                  <input v-model="form.age" type="number" class="form-control" id="age" required />
                </div>
                <div class="mb-3">
                  <label for="sex" class="form-label">性别</label>
                  <div>
                    <input v-model="form.sex" type="radio" id="male" value="1" /> 男
                    <input v-model="form.sex" type="radio" id="female" value="0" /> 女
                  </div>
                </div>
                <div class="mb-3">
                  <label for="phone" class="form-label">电话</label>
                  <input v-model="form.phone" type="text" class="form-control" id="phone" required />
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                  <button type="submit" class="btn btn-primary">保存</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
    import axios from 'axios';
  export default {
    name: "UserManage",
    data() {
      return {
        tableData: [],
        pageSize: 10,
        pageNum: 1,
        total: 0,
        name: '',
        sex: '',
        sexs: [
          { value: '1', label: '男' },
          { value: '0', label: '女' }
        ],
        form: {
          id: '',
          no: '',
          name: '',
          password: '',
          age: '',
          phone: '',
          sex: '0',
          roleId: '2'
        }
      }
    },
    methods: {
      resetParam() {
        this.name = '';
        this.sex = '';
        this.loadPost();
      },
      loadPost() {
        axios.post('http://localhost:1118/user/listPageC1', {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            name: this.name,
            sex: this.sex,
            roleId: '2'
          }
        },{
            headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") },  
        }).then(res => res.data).then(res => {
          if (res.data.code === 200) {
            this.tableData = res.data;
            this.total = res.total;
          } else {
            alert('获取数据失败');
          }
        });
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.pageNum = 1;
        this.loadPost();
      },
      handleCurrentChange(val) {
        this.pageNum = val;
        this.loadPost();
      },
      mod(row) {
        this.form = { ...row };
        // new bootstrap.Modal(document.getElementById('userModal')).show();
      },
      save() {
        if (this.form.id) {
          this.doMod();
        } else {
          this.doSave();
        }
      },
      doSave() {
        this.$axios.post(this.$httpUrl + '/user/save', this.form).then(res => res.data).then(res => {
          if (res.code == 200) {
            this.$message({ message: '操作成功！', type: 'success' });
            this.loadPost();
          } else {
            this.$message({ message: '操作失败！', type: 'error' });
          }
        });
      },
      doMod() {
        this.$axios.post(this.$httpUrl + '/user/update', this.form).then(res => res.data).then(res => {
          if (res.code == 200) {
            this.$message({ message: '操作成功！', type: 'success' });
            this.loadPost();
          } else {
            this.$message({ message: '操作失败！', type: 'error' });
          }
        });
      },
      del(id) {
        this.$axios.get(this.$httpUrl + '/user/del?id=' + id).then(res => res.data).then(res => {
          if (res.code == 200) {
            this.$message({ message: '删除成功！', type: 'success' });
            this.loadPost();
          } else {
            this.$message({ message: '删除失败！', type: 'error' });
          }
        });
      }
    },
    beforeMount() {
      this.loadPost();
    }
  }
  </script>
  
  <style scoped>
  </style>
  