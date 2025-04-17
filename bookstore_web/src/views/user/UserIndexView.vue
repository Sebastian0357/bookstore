<template>
    <ContentField>
      <form class="container text-center">
        <div class="row justify-content-center align-items-center">
          <!-- 用户头像和上传 -->
          <div class="col-12 col-md-4 mb-4 text-center">
            <img v-if="user.photo" :src="user.photo" class="pic-icon" alt="用户头像">
            <div>
              <label for="formFile" class="form-label">上传头像</label>
              <input type="file" class="form-control" id="img" @change="handleFileChange">
            </div>
          </div>
  
          <!-- 用户信息输入 -->
          <div class="col-12 col-md-8">
            <div class="row mb-3">
              <label for="username" class="col-md-3 col-form-label">用户名</label>
              <div class="col-md-9">
                <input v-model="user.username" type="text" class="form-control" id="username" placeholder="请输入用户名" />
              </div>
            </div>
            <div class="row mb-3">
              <label for="sex" class="col-md-3 col-form-label">性别</label>
              <div class="col-md-9">
                <select v-model="user.sex" class="form-control" id="sex">
                  <option value="男">男</option>
                  <option value="女">女</option>
                </select>
              </div>
            </div>
            <div class="row mb-3">
              <label for="phonenumber" class="col-md-3 col-form-label">手机号</label>
              <div class="col-md-9">
                <input v-model="user.phonenumber" type="tel" pattern="\d{11}" class="form-control" id="phonenumber"
                  placeholder="请输入手机号" />
              </div>
            </div>
            <div class="row mb-3">
              <label for="birthday" class="col-md-3 col-form-label">生日</label>
              <div class="col-md-9">
                <input v-model="user.birthday" type="date" class="form-control" id="birthday" />
              </div>
            </div>
            <div class="row mb-3">
              <label for="email" class="col-md-3 col-form-label">邮箱</label>
              <div class="col-md-9">
                <input v-model="user.email" type="email" class="form-control" id="email" placeholder="请输入邮箱" />
              </div>
            </div>
            <div class="row mb-4">
              <label for="role" class="col-md-3 col-form-label">角色</label>
              <div class="col-md-9">
                <input type="text" class="form-control" id="role" :value="roleText" disabled />
              </div>
            </div>
          </div>
        </div>
  
        <!-- 修改按钮 -->
        <div class="d-flex justify-content-center mb-4">
          <button @click="update(user.id)" type="button" class="btn btn-primary" data-toggle="modal"
            data-target="#editModal">
            修改信息
          </button>
        </div>
      </form>
    </ContentField>
    <!-- 成功/失败消息 -->
    <MessageBox ref="messageBox" />
  </template>
  
  
  <script>
  import axios from 'axios';
  import MessageBox from '@/components/MessageBox.vue';// 引入MessageBox组件
  import { handleFileUpload, submitFile } from '@/util/fileUpload';
import ContentField from '@/components/ContentField.vue';
  export default {
    components: {
      MessageBox,
      ContentField
    },
    data() {
      return {
        user: {},
        file: ''
      };
    },
    computed: {
      roleText() {
        return this.role === 1 ? "管理员" : "用户";
      },
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
              this.$refs.messageBox.showFeedback('error', '获取个人信息失败');
  
            }
          });
      },
      handleFileChange(event) {
        this.file = handleFileUpload(event);  // 调用通用文件上传方法
      },
      update() {
        submitFile(this.file).then(fileUrl => {
          if (fileUrl) {
            this.user.photo = fileUrl; // 只有文件上传成功才赋值
            console.log(this.user.photo); // 确认 img 被正确赋值
          }
          if(!/^\d{11}$/.test(this.user.phonenumber)) {
            this.$refs.messageBox.showFeedback('error', '输入十一位手机号');
            return;
          }
          axios
            .post(
              'http://localhost:1118/user/update', this.user, {
              headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") },
            }).then(res => {
              if (res.data.code === 200) {
                this.$refs.messageBox.showFeedback('success', '修改个人信息成功');
                this.loadPost()
              } else {
                this.$refs.messageBox.showFeedback('error', '修改个人信息失败');
  
              }
            });
        }).catch(() => {
          console.log('文件上传失败，请重试');
        });
      }
    },
    mounted() {
      this.loadPost()
    }
  };
  </script>
<style scoped>

.container {
  width: 80%;
  margin: auto;
  padding-top: 30px;
}

.pic-icon {
  height: 240px;
  object-fit: cover;
  margin-bottom: 15px;
  /* border-radius: 50%; */
}

.row {
  margin-bottom: 1em;
}

label {
  font-weight: bold;
}

.form-control {
  padding: 0.8rem;
  font-size: 1rem;
}

.btn {
  padding: 10px 20px;
}

.modal-content {
  padding: 20px;
}

.modal-footer .btn {
  padding: 8px 20px;
}

</style>
