<template>
  <div>
    <div style="margin-bottom: 5px;">
      <!-- Search Input -->
      <div class="input-group mb-2">
        <input v-model="name" type="text" class="form-control" placeholder="请输入景点名"
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
          <th>景点名</th>
          <th>地址</th>
          <th>开放时间</th>
          <th>票价</th>
          <th>概述</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="row in tableData" :key="row.id">
          <td>{{ row.spotName }}</td>
          <td>{{ row.adress }}</td>
          <td>{{ row.openDate }}</td>
          <td>{{ row.fee }}</td>
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
                <label for="spotName">景点名称</label>
                <input type="text" class="form-control" id="spotName" v-model="form.spotName" required />
              </div>

              <!-- 图片上传 -->
              <div class="form-group">
                <label for="imgUpload">上传图片</label>
                <input type="file" class="form-control-file" id="imgUpload" @change="handleFileUpload"
                  accept="image/jpeg, image/png">

                <!-- 预览已上传的图片 -->
                <div v-if="form.img" class="mt-2">
                  <img :src="form.img" alt="预览图片" class="img-thumbnail" style="max-width: 200px;">
                  <!-- <button class="btn btn-danger btn-sm mt-2" @click="removeImage">移除图片</button> -->
                </div>
              </div>


              <div class="form-group">
                <label for="openDate">开放时间</label>
                <input type="text" class="form-control" id="openDate" v-model="form.openDate" />
              </div>

              <div class="form-group">
                <label for="food">美食推荐</label>
                <textarea class="form-control" id="food" v-model="form.food"></textarea>
              </div>

              <div class="form-group">
                <label for="adress">地址</label>
                <input type="text" class="form-control" id="adress" v-model="form.adress" required />
              </div>

              <div class="form-group">
                <label for="fee">门票费用</label>
                <input type="number" class="form-control" id="fee" v-model="form.fee" step="0.01" />
              </div>

              <div class="form-group">
                <label for="description">景点描述</label>
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
import MessageBox from '@/components/MessageBox.vue';
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
        id: '',
        spotName: '',   // 景点名称
        img: '',         // 图片
        openDate: '',   // 开放时间
        food: '',        // 美食推荐
        adress: '',      // 地址
        fee: '',         // 门票费用
        description: ''  // 景点描述
      },
    };
  },
  methods: {
    loadPost() {
      axios.post('http://localhost:1118/spotinfo/listPage', {
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
          this.$refs.messageBox.showFeedback('error', '获取数据失败');
        }
      });
    },
    handleFileUpload(event) {
      this.file = event.target.files[0];

      // 1. 检查文件类型
      const allowedTypes = ["image/jpeg", "image/png", "application/pdf"];
      if (!allowedTypes.includes(this.file.type)) {
        this.$refs.messageBox.showFeedback('error', '不支持的文件类型，仅支持 JPG/PNG/PDF');
        this.file = null;
        return;
      }

      // 2. 检查文件大小
      const maxSize = 10 * 1024 * 1024; // 10MB
      if (this.file.size > maxSize) {
        this.$refs.messageBox.showFeedback('error', '文件大小不能超过 10MB');
        this.file = null;
      }
    },
    submitFile() {
      return new Promise((resolve, reject) => {
        if (!this.file) {
          resolve(null); // 如果没有文件，直接返回 null，继续执行后续操作
          return;
        }

        const  formData = new FormData();
        formData.append("file", this.file);

        axios.post("http://localhost:1118/upload/file", formData, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("jwt_token"),
            "Content-Type": "multipart/form-data",
          }
        }).then(response => {
          this.fileUrl = response.data.url;
          resolve(response.data.url); // 上传成功后，返回文件 URL
        }).catch(error => {
          this.$refs.messageBox.showFeedback('error', "上传失败：" + error.response.data);
          reject(error);
        });
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
      this.submitFile().then(fileUrl => {
        if (fileUrl) {
          this.form.img = fileUrl; // 只有文件上传成功才赋值
          console.log(this.form.img); // 确认 img 被正确赋值
        }

        const apiUrl = this.form.id ? '/spotinfo/update' : '/spotinfo/save';

        // 这里的 axios 请求放在文件上传成功之后
        axios.post('http://localhost:1118' + apiUrl, this.form, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("jwt_token")
          }
        }).then(res => {
          if (res.data.code == 200) {
            this.$refs.messageBox.showFeedback('success', '操作成功');
            this.centerDialogVisible = false;
            this.loadPost();
          } else {
            this.$refs.messageBox.showFeedback('error', '操作失败');
          }
        });

      }).catch(() => {
        this.$refs.messageBox.showFeedback('error', '文件上传失败，请重试');
      });
    },
    del(id) {
      axios.post('http://localhost:1118/spotinfo/delete', { id: id }, {  // 传 JSON 对象
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
