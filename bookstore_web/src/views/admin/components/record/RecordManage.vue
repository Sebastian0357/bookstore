<template>
    <div>
      <!-- 搜索框 -->
      <div class="mb-3">
        <input v-model="name" class="form-control" placeholder="请输入房间号" style="width: 200px; display: inline-block;" @keyup.enter="loadPost">
        <button class="btn btn-primary" style="margin-left: 5px;" @click="loadPost">查询</button>
        <button class="btn btn-secondary" style="margin-left: 5px;" @click="resetParam">重置</button>
      </div>
  
      <!-- Bootstrap 表格 -->
      <table class="table table-bordered">
        <thead class="thead-light">
          <tr>
            <th>ID</th>
            <th>图书名</th>
            <th>用户名</th>
            <th>评论时间</th>
            <th>内容</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(row, index) in tableData" :key="index">
            <td>{{ row.id }}</td>
            <td>{{ row.roomname }}</td>
            <td>{{ row.username }}</td>
            <td>{{ formatDateDetail(row, 'createtime') }}</td>
            <td>{{ row.remark }}</td>
          </tr>
        </tbody>
      </table>
  
      <!-- 分页 -->
      <nav aria-label="Page navigation example">
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
    </div>
  </template>
  
  <script>
  export default {
    name: "RecordManage",
    data() {
      return {
        user: JSON.parse(sessionStorage.getItem('CurUser')),
        tableData: [],
        pageSize: 10,
        pageNum: 1,
        total: 0,
        totalPages: 0,
        name: '',
        storage: '',
        goodstype: '',
        form: {
          id: '',
          name: '',
          storage: '',
          remark: ''
        },
      };
    },
    methods: {
      formatDate(row, column) {
        let data = row[column.property];
        if (data == null) return null;
        let dt = new Date(data);
        return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate();
      },
      formatDateDetail(row, column) {
        let data = row[column.property];
        if (data == null) return null;
        let dt = new Date(data);
        return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt.getSeconds();
      },
      resetForm() {
        this.$refs.form.resetFields();
      },
      handleSizeChange(val) {
        this.pageNum = 1;
        this.pageSize = val;
        this.loadPost();
      },
      handleCurrentChange(val) {
        this.pageNum = val;
        this.loadPost();
      },
      resetParam() {
        this.name = '';
        this.storage = '';
        this.goodstype = '';
      },
      loadStorage() {
        this.$axios.get(this.$httpUrl + '/storage/list').then(res => res.data).then(res => {
          if (res.code === 200) {
            this.storageData = res.data;
          } else {
            alert('获取数据失败');
          }
        });
      },
      loadGoodstype() {
        this.$axios.get(this.$httpUrl + '/goodstype/list').then(res => res.data).then(res => {
          if (res.code === 200) {
            this.goodstypeData = res.data;
          } else {
            alert('获取数据失败');
          }
        });
      },
      loadPost() {
        this.$axios.post(this.$httpUrl + '/record/listPageC1', {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            name: this.name,
            goodstype: this.goodstype + '',
            storage: this.storage + '',
          }
        }).then(res => res.data).then(res => {
          if (res.code === 200) {
            this.tableData = res.data;
            this.total = res.total;
            this.totalPages = Math.ceil(this.total / this.pageSize);  // 计算总页数
          } else {
            alert('获取数据失败');
          }
        });
      }
    },
    beforeMount() {
      this.loadStorage();
      this.loadGoodstype();
      this.loadPost();
    }
  };
  </script>
  
  <style scoped>
  </style>
  