<template>
  <div>
    <div style="margin-bottom: 5px;">
      <!-- Search Input -->
      <div class="input-group mb-2">
        <input v-model="name" type="text" class="form-control" placeholder="请输入线路" @keyup.enter="loadPost"
          style="max-width: 200px;">
        <input v-model="approach" type="text" class="form-control" placeholder="请输入途径" @keyup.enter="loadPost"
          style="max-width: 200px; margin-left: 5px;">
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
          <th>线路名</th>
          <th>途径</th>
          <th>票价</th>
          <th>概述</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="row in tableData" :key="row.id">
          <td>{{ row.name }}</td>
          <td>{{ formatRoute(row.start, row.approach, row.end) }}</td>
          <td>{{ row.price }}</td>
          <td>{{ row.description }}</td>
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
                <label for="name">线路名称</label>
                <input type="text" class="form-control" id="name" v-model="form.name" required />
              </div>
              <div class="form-group">
                <label for="img">线路图片</label>
                <input type="file" class="form-control" id="img" @change="handleFileChange">
                <img v-if="form.img" :src="form.img" alt="预览图" class="img-preview">
              </div>
              <div class="form-group">
                <label for="start">起点</label>
                <input type="text" class="form-control" id="start" v-model="form.start" required />
              </div>
              <div class="form-group">
                <label for="approach">途径点</label>
                <input type="text" class="form-control" id="approach" v-model="form.approach"
                  placeholder="多个地点用'、'分隔" />
              </div>
              <div class="form-group">
                <label for="end">终点</label>
                <input type="text" class="form-control" id="end" v-model="form.end" required />
              </div>
              <div class="form-group">
                <label for="price">票价</label>
                <input type="number" class="form-control" id="price" v-model="form.price" required />
              </div>
              <div class="form-group">
                <label for="feature">特色介绍</label>
                <textarea class="form-control" id="feature" v-model="form.feature"></textarea>
              </div>
              <div class="form-group">
                <label for="description">详细描述</label>
                <textarea class="form-control" id="description" v-model="form.description"></textarea>
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
      approach: '',
      centerDialogVisible: false,
      form: {
        id: '',
        name: '',
        img: '',
        start: '',
        approach: '',
        end: '',
        price: '',
        feature: '',
        description: '',
        addtime: '',
      },
    };
  },
  methods: {
    loadPost() {
      axios.post('http://localhost:1118/line/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: { name: this.name, approach: this.approach },
      }, {
        headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") },
      }).then(res => {
        if (res.data.code === 200) {
          this.tableData = res.data.data;
          this.total = res.data.total;
        } else {
          this.$refs.messageBox.showFeedback('error', '获取数据失败');

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
    handleFileChange(event) {
      this.file = handleFileUpload(event);  // 调用通用文件上传方法
      
    },
    save() {
      if (!this.form.name || !this.form.start || !this.form.end || !this.form.price) {
        this.$refs.messageBox.showFeedback('error', '线路名称、起点、终点和票价不能为空！');
        return;
      }
      submitFile(this.file).then(fileUrl => {
        if (fileUrl) {
          this.form.img = fileUrl; // 只有文件上传成功才赋值
          console.log(this.form.img); // 确认 img 被正确赋值
        }
        const apiUrl = this.form.id ? '/line/update' : '/line/save';
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
    formatRoute(start, approach, end) {
      if (!start || !end) return '';
      const approachPoints = approach ? approach.split('、') : [];
      const formattedApproach = approachPoints.join(' - '); // 直接用 join 连接
      return [start, formattedApproach, end].filter(Boolean).join(' - ');
    },
    del(id) {
      axios.post('http://localhost:1118/line/delete', { id: id }, {  // 传 JSON 对象
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
    resetParam() {
      this.name = ''
      this.approach = ''
      this.loadPost()
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
