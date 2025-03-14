<template>
  <div>
    <div style="margin-bottom: 5px;">
      <!-- Search Input -->
      <div class="input-group mb-2">
        <input v-model="name" type="text" class="form-control" placeholder="请输入物品名" @keyup.enter="loadPost">
        <div class="input-group-append">
          <button class="btn btn-primary" @click="loadPost">查询</button>
        </div>
      </div>



      <!-- Buttons -->
      <button class="btn btn-success" @click="resetParam">重置</button>
      <button class="btn btn-primary" style="margin-left: 5px;" @click="add" v-if="role === 0">新增</button>
    </div>

    <!-- Table -->
    <table class="table table-bordered">
      <thead class="thead-light">
        <tr>
          <th>ID</th>
          <th>书名</th>
          <th>作者</th>
          <th>出版社</th>
          <th>概述</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="row in tableData" :key="row.id">
          <td>{{ row.id }}</td>
          <td>{{ row.bookname }}</td>
          <td>{{ row.author }}</td>
          <td>{{ row.press }}</td>
          <td>{{ row.description }}</td>
          <td v-if="this.roleId != 2">
            <button class="btn btn-success btn-sm" @click="mod(row)">编辑</button>
            <button class="btn btn-danger btn-sm ml-2" @click="del(row.id)">删除</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Pagination -->
    <nav>
      <ul class="pagination justify-content-center">
        <li class="page-item" :class="{ disabled: pageNum === 1 }">
          <a class="page-link" href="#" @click="handleCurrentChange(pageNum - 1)">Previous</a>
        </li>
        <li class="page-item" v-for="n in Math.ceil(total / pageSize)" :key="n" :class="{ active: pageNum === n }">
          <a class="page-link" href="#" @click="handleCurrentChange(n)">{{ n }}</a>
        </li>
        <li class="page-item" :class="{ disabled: pageNum === Math.ceil(total / pageSize) }">
          <a class="page-link" href="#" @click="handleCurrentChange(pageNum + 1)">Next</a>
        </li>
      </ul>
    </nav>

    <!-- Modal Dialog -->
    <div class="modal" tabindex="-1" role="dialog" v-if="centerDialogVisible">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">书籍维护</h5>
      </div>
      <div class="modal-body">
        <form ref="form">
          <div class="form-group">
            <label for="itemName">书名</label>
            <input type="text" class="form-control" id="itemName" v-model="form.bookname" required />
          </div>
          <div class="form-group">
            <label for="goodstype">分类</label>
            <select id="goodstype" class="form-control" v-model="form.goodstype" required>
              <option v-for="item in goodstypeData" :key="item.id" :value="item.id">{{ item.name }}</option>
            </select>
          </div>
          <div class="form-group">
            <label for="itemName">作者</label>
            <input type="text" class="form-control" id="itemName" v-model="form.author" required />
          </div>
          <div class="form-group">
            <label for="itemName">出版社</label>
            <input type="text" class="form-control" id="itemName" v-model="form.press" required />
          </div>
          <div class="form-group">
            <label for="itemName">出版时间</label>
            <input type="text" class="form-control" id="itemName" v-model="form.pressdate" required />
          </div>
          <div class="form-group">
            <label for="itemCount">价格</label>
            <input type="number" class="form-control" id="itemCount" v-model="form.price" required />
          </div>
          <div class="form-group">
            <label for="itemCount">数量</label>
            <input type="number" class="form-control" id="itemCount" v-model="form.count" required />
          </div>
          <div class="form-group">
            <label for="remark">简介</label>
            <textarea class="form-control" id="remark" v-model="form.description"></textarea>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" @click="centerDialogVisible = false">取 消</button>
        <button type="button" class="btn btn-primary" @click="save">确 定</button>
      </div>
    </div>
  </div>
</div>


  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "GoodsManage",
  data() {
    return {
      roleId: localStorage.getItem("roleId"),
      storageData: [],
      goodstypeData: [],
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name: '',
      storage: '',
      goodstype: '',
      centerDialogVisible: false,
      form: {
        id: '',
        bookname: '',
        type: '',
        author: '',
        press: '',
        pressdate: '',
        price:'',
        count:'',
        description:''
      }
    };
  },
  methods: {
    loadPost() {
      axios.post('http://localhost:1118/goods/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: { name: this.name, goodstype: this.goodstype + '', storage: this.storage + '' },
      },{
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
    mod(row) {
      this.centerDialogVisible = true;
      // 将选中的行数据传递给表单
      this.$nextTick(() => {
        this.form = { ...row };
      });
      console.log(this.form)
    },
    add() {
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.form = { id: '', name: '', storage: '', goodstype: '', count: '', remark: '' };
      });
    },
    save() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const apiUrl = this.form.id ? '/goods/update' : '/goods/save';
          this.$axios.post(this.$httpUrl + apiUrl, this.form).then(res => {
            if (res.code == 200) {
              this.$message({ message: '操作成功！', type: 'success' });
              this.centerDialogVisible = false;
              this.loadPost();
            } else {
              this.$message({ message: '操作失败！', type: 'error' });
            }
          });
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
.modal {
  display: block !important;  /* 确保模态框显示 */
  visibility: visible !important; /* 确保可见 */
  opacity: 1 !important; /* 确保不透明 */
}

.modal-dialog {
  max-width: 600px; /* 可根据需要调整宽度 */
  margin: 30px auto; /* 使模态框居中显示 */
}

.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  z-index: 1040;
}

</style>
