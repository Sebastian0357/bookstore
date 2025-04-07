<template>
  <div>
    <div style="margin-bottom: 5px;">
      <!-- Search Input -->
      <div class="input-group mb-2">
        <input v-model="name" type="text" class="form-control" placeholder="请输入美食名"
          style="max-width: 200px; margin-left: 5px;" @keyup.enter="loadPost">
        <div class="input-group-append">
          <button class="btn btn-primary" style="margin-left: 5px;" @click="loadPost">查询</button>
          <button class="btn btn-success" style="margin-left: 5px;" @click="resetParam">重置</button>
        </div>
      </div>


      <!-- Buttons -->
      <button class="btn btn-primary" style="margin-left: 5px;" @click="add">新增</button>
    </div>

    <!-- Table -->
    <table class="table table-bordered">
      <thead class="thead-light">
        <tr>
          <th>美食名</th>
          <th>简介</th>
          <th>价格</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="row in tableData" :key="row.id">
          <td>{{ row.name }}</td>
          <td>{{ row.description }}</td>
          <td>{{ row.price }}</td>
          <!-- <td v-if="user.role != 2"> -->
          <td>
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

    <!-- Modal Dialog -->
    <div class="modal" tabindex="-1" role="dialog" v-if="centerDialogVisible">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">物品维护</h5>
          </div>
          <div class="modal-body">
            <form ref="form">
              <div class="form-group">
                <label for="name">物品名</label>
                <input type="text" class="form-control" id="name" v-model="form.name" required />
              </div>
              <div class="form-group">
                <label for="img">上传图片</label>
                <input type="file" class="form-control" id="img" @change="handleFileUpload" />
                <img v-if="form.img" :src="form.img" alt="预览图" class="img-preview" />
              </div>
              <div class="form-group">
                <label for="price">价格</label>
                <input type="number" class="form-control" id="price" v-model="form.price" required />
              </div>
              <div class="form-group">
                <label for="name">附近景点</label>
                <input type="text" class="form-control" id="scenic_spot" v-model="form.scenicSpot" required />
              </div>
              <div class="form-group">
                <label for="remark">备注</label>
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

    <!-- 成功/失败消息 -->
    <MessageBox ref="messageBox" />
    
  </div>
</template>

<script>
import axios from 'axios';
import { handleFileUpload, submitFile } from '@/util/fileUpload';
import MessageBox from '@/components/MessageBox.vue';// 引入MessageBox组件

export default {
  components: {
    MessageBox
  },
  data() {
    return {
      // roleId: localStorage.getItem("roleId"),
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
        bookname: '',
        scenic_spot: '',
        count: '',
        description: ''
      }
    };
  },
  methods: {
    loadPost() {
      axios.post('http://localhost:1118/food/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: { name: this.name },
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
    resetParam() {
      this.name = ''
      this.loadPost()
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
        this.form = { bookname: '',
        scenic_spot: '',
        count: '',
        description: '' };
      });
    },
    handleFileUpload(event) {
      this.file = handleFileUpload(event);  // 调用通用文件上传方法
      console.log(this.file + "ssss")
    },
    save() {
      if (!this.form.name || !this.form.price) {
        this.$refs.messageBox.showFeedback('error', '美食名称、价格不能为空！');
        return;
      }
      submitFile(this.file).then(fileUrl => {
        if (fileUrl) {
          this.form.img = fileUrl; // 只有文件上传成功才赋值
          console.log(this.form.img); // 确认 img 被正确赋值
        }
        const apiUrl = this.form.id ? '/food/update' : '/food/save';
        axios.post('http://localhost:1118' + apiUrl, this.form, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("jwt_token")
          }
        }).then(res => {
          if (res.data.code === 200) {
            this.$refs.messageBox.showFeedback('success', '操作成功！');

            this.centerDialogVisible = false;
            this.loadPost();
          } else {
            this.$refs.messageBox.showFeedback('error', '操作失败！');

          }
        });
      }).catch(() => {
        console.log('文件上传失败，请重试');
      });
    },
    del(id) {
      axios.post('http://localhost:1118/food/delete', { id: id }, {  // 传 JSON 对象
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
};
</script>

<style scoped>
.modal {
  display: block !important;
  /* 确保模态框显示 */
  visibility: visible !important;
  /* 确保可见 */
  opacity: 1 !important;
  /* 确保不透明 */
}

.modal-dialog {
  max-width: 600px;
  /* 可根据需要调整宽度 */
  margin: 30px auto;
  /* 使模态框居中显示 */
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
