<template>
    <div>
      <div style="margin-bottom: 5px;">
        <input v-model="name" class="form-control" placeholder="请输入书名" style="width: 200px;" @keyup.enter="loadPost" />
        <input v-model="id" class="form-control" placeholder="请输入作者" style="margin-left: 5px; width: 200px;" @keyup.enter="loadPost" />
        <button class="btn btn-primary" style="margin-left: 5px;" @click="loadPost">查询</button>
        <button class="btn btn-success" style="margin-left: 5px;" @click="resetParam">重置</button>
        <button class="btn btn-primary" style="margin-left: 5px;" @click="add">新增</button>
      </div>
  
      <!-- Bootstrap 表格 -->
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>图书编号</th>
            <th>类型</th>
            <th>书名</th>
            <th>作者</th>
            <th>价格</th>
            <th>库存</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(row, index) in tableData" :key="index">
            <td>{{ row.id }}</td>
            <td>{{ row.type }}</td>
            <td>{{ row.name }}</td>
            <td>{{ row.author }}</td>
            <td>{{ row.price }}</td>
            <td>{{ row.count }}</td>
            <td>
              <span v-if="row.status == '0'" class="badge bg-success">上线</span>
              <span v-if="row.status == '1'" class="badge bg-danger">下线</span>
            </td>
            <td>
              <button class="btn btn-warning" @click="doCheck(row)">
                {{ row.status == '0' ? '下线' : '上线' }}
              </button>
              <button class="btn btn-info btn-sm" @click="mod(row)">编辑</button>
              <button class="btn btn-danger btn-sm" @click="del(row.id)">删除</button>
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
  
      <!-- Bootstrap 模态框 -->
      <div class="modal fade" id="bookModal" tabindex="-1" aria-labelledby="bookModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="bookModalLabel">图书信息</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <form @submit.prevent="save">
                <div class="mb-3">
                  <label for="type" class="form-label">类型</label>
                  <input v-model="form.type" type="text" class="form-control" id="type" />
                </div>
                <div class="mb-3">
                  <label for="name" class="form-label">书名</label>
                  <input v-model="form.name" type="text" class="form-control" id="name" />
                </div>
                <div class="mb-3">
                  <label for="author" class="form-label">作者</label>
                  <input v-model="form.author" type="text" class="form-control" id="author" />
                </div>
                <div class="mb-3">
                  <label for="price" class="form-label">价格</label>
                  <input v-model="form.price" type="text" class="form-control" id="price" />
                </div>
                <div class="mb-3">
                  <label for="count" class="form-label">库存</label>
                  <input v-model="form.count" type="number" class="form-control" id="count" />
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
  export default {
    name: "StorageManage",
    data() {
      return {
        tableData: [],
        pageSize: 10,
        pageNum: 1,
        total: 0,
        name: '',
        id: '',
        status: '',
        centerDialogVisible: false,
        user: JSON.parse(sessionStorage.getItem('CurUser')),
        form: {
          id: '',
          name: '',
          type: '',
          price: '',
          status: 0,
          count: 0
        },
        sta : '',
        rules: {
          name: [
            { required: true, message: '请输入书名', trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      resetForm() {
        this.$refs.form.resetFields();
      },
      del(id) {
        this.$axios.get(this.$httpUrl + '/storage/del?id=' + id).then(res => res.data).then(res => {
          if (res.code === 200) {
            this.$message({
              message: '操作成功！',
              type: 'success'
            });
            this.loadPost();
          } else {
            this.$message({
              message: '操作失败！',
              type: 'error'
            });
          }
        });
      },
      mod(row) {
        this.centerDialogVisible = true;
        this.$nextTick(() => {
          this.form.id = row.id;
          this.form.name = row.name;
          this.form.price = row.price;
          this.form.author = row.author;
          this.form.count = row.count;
          this.form.type = row.type;
        });
      },
      add() {
        this.centerDialogVisible = true;
        this.$nextTick(() => {
          this.resetForm();
        });
      },
      doCheck(row) {
        let t;
        if (row.status === '0') { t = 1, this.sta = "下线"; } else { t = 0, this.sta = "上线"; }
        this.$axios.post(this.$httpUrl + '/storage/update', {
          id: row.id,
          status: t,
        }).then(res => res.data).then(res => {
          if (res.code === 200) {
            this.$message({
              message: '操作成功！',
              type: 'success'
            });
            this.loadPost();
          } else {
            alert('获取数据失败');
          }
        });
      },
      doSave() {
        this.$axios.post(this.$httpUrl + '/storage/save', this.form).then(res => res.data).then(res => {
          if (res.code === 200) {
            this.$message({
              message: '操作成功！',
              type: 'success'
            });
            this.centerDialogVisible = false;
            this.loadPost();
            this.resetForm();
          } else {
            this.$message({
              message: '操作失败！',
              type: 'error'
            });
          }
        });
      },
      doMod() {
        this.$axios.post(this.$httpUrl + '/storage/update', this.form).then(res => res.data).then(res => {
          if (res.code === 200) {
            this.$message({
              message: '操作成功！',
              type: 'success'
            });
            this.centerDialogVisible = false;
            this.loadPost();
            this.resetForm();
          } else {
            this.$message({
              message: '操作失败！',
              type: 'error'
            });
          }
        });
      },
      save() {
        this.$refs.form.validate((valid) => {
          if (valid) {
            if (this.form.id) {
              this.doMod();
            } else {
              this.doSave();
            }
          } else {
            return false;
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
      resetParam() {
        this.name = '';
        this.id = '';
        this.loadPost();
      },
      loadPost() {
        this.$axios.post(this.$httpUrl + '/storage/listPageC1', {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            name: this.name,
            id: this.id
          }
        }).then(res => res.data).then(res => {
          if (res.code === 200) {
            this.tableData = res.data;
            this.total = res.total;
          } else {
            alert('获取数据失败');
          }
        });
      }
    },
    beforeMount() {
      this.loadPost();
    }
  };
  </script>
  
  <style scoped>
  </style>
  