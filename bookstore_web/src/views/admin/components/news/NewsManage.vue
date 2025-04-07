<template>
  <div>
    <div style="margin-bottom: 5px;">
      <!-- Search Input -->
      <div class="input-group mb-2">
        <input v-model="name" type="text" class="form-control" placeholder="请输入标题" @keyup.enter="loadPost"
          style="max-width: 200px;">
        <input v-model="release" type="text" class="form-control" placeholder="请输入发布人" @keyup.enter="loadPost"
          style="max-width: 200px; margin-left: 5px;">
        <div class="input-group-append">
          <button class="btn btn-primary" @click="loadPost" style="margin-left: 5px;">查询</button>
          <button class="btn btn-success" @click="resetParam" style="margin-left: 5px;">重置</button>
        </div>
      </div>
      <!-- Buttons -->
      <button class="btn btn-primary" style="margin-left: 5px;" @click="add">新增</button>
    </div>

    <!-- Table -->
    <table class="table table-bordered">
      <thead class="thead-light">
        <tr>
          <th>标题</th>
          <th>发布人</th>
          <th>浏览量</th>
          <th>添加时间</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="row in tableData" :key="row.id">
          <td>{{ row.title }}</td>
          <td>{{ row.release }}</td>
          <td>{{ row.click }}</td>
          <td>{{ formatDate(row.addtime) }}</td>
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
                <label for="title">标题</label>
                <input type="text" class="form-control" id="title" v-model="form.title" required />
              </div>

              <div class="form-group">
                <label for="img">上传图片</label>
                <input type="file" class="form-control" id="img" @change="handleFileUpload" />
                <img v-if="form.img" :src="form.img" alt="预览图" class="img-preview" />
              </div>

              <div class="form-group">
                <label for="release">发布人</label>
                <input type="text" class="form-control" id="release" v-model="form.release" required />
              </div>

              <div class="form-group">
                <label for="news">新闻内容</label>
                <textarea class="form-control" id="news" v-model="form.news" required></textarea>
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
      release: '',
      centerDialogVisible: false,
      form: {
        id: '',
        title: '',
        classification: '',
        img: '',
        release: '',
        click: '',
        news: '',
        addtime: ''
      }

    };
  },
  methods: {
    loadPost() {
      axios.post('http://localhost:1118/news/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: { name: this.name, release: this.release },
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
        this.form = {
        id: '',
        title: '',
        classification: '',
        img: '',
        release: '',
        click: '',
        news: '',
        addtime: ''};
      });
    },
    handleFileUpload(event) {
      this.file = handleFileUpload(event);  // 调用通用文件上传方法
    },
    save() {
      if (!this.form.title || !this.form.news) {
        this.$refs.messageBox.showFeedback('error', '标题、内容不能为空！');
        return;
      }
      submitFile(this.file).then(fileUrl => {
        if (fileUrl) {
          this.form.img = fileUrl; // 只有文件上传成功才赋值
          console.log(this.form.img); // 确认 img 被正确赋值
        }
        const apiUrl = this.form.id ? '/news/update' : '/news/save';
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
      axios.post('http://localhost:1118/news/delete', { id: id }, {  // 传 JSON 对象
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
      this.release = ''
      this.loadPost()
    },
    formatDate(isoString) {
      const date = new Date(isoString);
      return date.getFullYear() + '-' +
        String(date.getMonth() + 1).padStart(2, '0') + '-' +
        String(date.getDate()).padStart(2, '0') + ' ' +
        String(date.getHours()).padStart(2, '0') + ':' +
        String(date.getMinutes()).padStart(2, '0') + ':' +
        String(date.getSeconds()).padStart(2, '0');
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
