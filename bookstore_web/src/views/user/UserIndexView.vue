<template>
  <ContentField>
    <form class="container text-center">
      <div class="row justify-content-center align-items-center">
        <!-- 用户头像和上传 -->
        <div class="col-12 col-md-4 mb-4 text-center">
          <img src="@/assets/images/我与地坛.png" class="pic-icon mb-3" id="image" alt="User Avatar" />
          <div>
            <label for="formFile" class="form-label">上传头像</label>
            <input class="form-control" type="file" id="formFile">
          </div>
        </div>

        <!-- 用户信息输入 -->
        <div class="col-12 col-md-8">
          <div class="row mb-3">
            <label for="username" class="col-md-3 col-form-label">用户名</label>
            <div class="col-md-9">
              <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名" />
            </div>
          </div>
          <div class="row mb-3">
            <label for="password" class="col-md-3 col-form-label">密码</label>
            <div class="col-md-9">
              <input v-model="password" type="password" class="form-control" id="password" placeholder="请输入密码" />
            </div>
          </div>
          <div class="row mb-3">
            <label for="phone" class="col-md-3 col-form-label">手机号</label>
            <div class="col-md-9">
              <input v-model="phone" type="text" class="form-control" id="phone" placeholder="请输入手机号" />
            </div>
          </div>
          <div class="row mb-3">
            <label for="birthday" class="col-md-3 col-form-label">生日</label>
            <div class="col-md-9">
              <input v-model="birthday" type="date" class="form-control" id="birthday" />
            </div>
          </div>
          <div class="row mb-4">
            <label for="email" class="col-md-3 col-form-label">邮箱</label>
            <div class="col-md-9">
              <input v-model="email" type="email" class="form-control" id="email" placeholder="请输入邮箱" />
            </div>
          </div>
        </div>
      </div>

      <!-- 修改按钮 -->
      <div class="d-flex justify-content-center mb-4">
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bs-example-modal-lg">
          修改信息
        </button>
      </div>
    </form>

    <!-- 编辑信息的 Modal -->
    <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" id="myModal">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
            <h4 class="modal-title" id="myModalLabel">编辑个人信息</h4>
          </div>
          <div class="modal-body">
            <form action="" class="form-horizontal">
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">用户名：</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="modal_cus_name" placeholder="请输入新的用户名" />
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">密码：</label>
                <div class="col-sm-9">
                  <input type="password" class="form-control" id="modal_bj_prd" placeholder="请输入新的密码" />
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">邮箱：</label>
                <div class="col-sm-9">
                  <input type="email" class="form-control" id="modal_up" placeholder="请输入新邮箱" />
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">手机号：</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="modal_mobile" placeholder="请输入新手机号" />
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-dismiss="modal" id="save-edit-btn">
              保存
            </button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">
              关闭
            </button>
          </div>
        </div>
      </div>
    </div>

  </ContentField>
</template>

<script>
import ContentField from "@/components/ContentField.vue";

export default {
  components: {
    ContentField,
  },
  data() {
    return {
      username: "",
      password: "",
      phone: "",
      birthday: "",
      email: "",
    };
  },
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
