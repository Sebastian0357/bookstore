<template>
  <ContentField>
    <form class="container text-center">
      <div class="row">
        <div class="col-6 col-md-4">
          <img src="@\assets\images\我与地坛.png" class="pic-icon" id="#image" />
          <div class="col">
            <label for="formFile" class="form-label">Default file input example</label>
            <input class="form-control" type="file" id="formFile">
          </div>
        </div>
        <div class="col-md-8">
          <div class="row">
            <label for="username" class="col-md-2 form-label">用户名</label>
            <input v-model="username" type="text" class="col form-control" id="username" placeholder="用户名" />
          </div>
          <div class="row">
            <label for="password" class="col-md-2 form-label">密码</label>
            <input v-model="password" type="password" class="col form-control" id="password" placeholder="请输入密码" />
          </div>
          <div class="row">
            <label for="password" class="col-md-2 form-label">手机号</label>
            <input v-model="password" type="password" class="col form-control" id="password" placeholder="请输入密码" />
          </div>
          <div class="row">
            <label for="password" class="col-md-2 form-label">生日</label>
            <input v-model="password" type="password" class="col form-control" id="password" placeholder="请输入密码" />
          </div>
          <div class="row">
            <label for="password" class="col-md-2 form-label">邮箱</label>
            <input v-model="password" type="password" class="col form-control" id="password" placeholder="请输入密码" />
          </div>
        </div>
      </div>
    </form>


    <div class="mb-3">
      <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bs-example-modal-lg">
        修 改
      </button>

      <!--    <input type="file" name="file1_1" id="file1_1">-->
      <!--    <input type="button" value="上传" onclick="uploadFile('file1_1');" />-->

      <!--    <a href="javascript:void(0)" onclick="uploadPhoto()">选择图片</a>-->
      <!--    <input type="file" id="photoFile" style="display: none;" onchange="upload()">-->
      <!--    <img id="preview_photo" src="" width="200px" height="200px">-->
      <!--    <style>-->
      <!--        img[src=""],img:not([src]){-->
      <!--            opacity:0;-->
      <!--        }-->
      <!--    </style>-->
    </div>
    <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
      id="myModal">
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
                  <!--                            <input type="text" onblur="CheckNumber(this.value)"  class="form-control"  id="modal_cus_name"  placeholder="请输入新的用户名(数字)">-->
                  <input type="text" onblur="IsEmptyForm(this.value)" class="form-control" id="modal_cus_name"
                    placeholder="请输入新的用户名" />
                  <div id="TiShi1"></div>
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">密码：</label>
                <div class="col-sm-9">
                  <input type="text" onblur="IsEmptyFormTwo(this.value)" class="form-control" id="modal_bj_prd"
                    placeholder="请输入新的密码" />
                  <div id="TiShi3"></div>
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">邮箱：</label>
                <div id="email"></div>
                <div class="col-sm-9">
                  <input type="text" onblur="IsEmail(this.value)" class="form-control" id="modal_up"
                    placeholder="请输入新邮箱" />
                  <div id="TiShi"></div>
                </div>
              </div>
              <!--                    新增的电话号码-->
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">手机号：</label>
                <div class="col-sm-9">
                  <input type="text" onblur="ValidateMobile(this.value)" class="form-control" id="modal_mobile"
                    placeholder="请输入新手机号" />
                  <div id="TiShi2"></div>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-dismiss="modal" id="sava-edit-btn">
              保存
            </button>
            <button type="button" class="btn btn-default" data-dismiss="modal">
              关闭
            </button>
          </div>
        </div>
      </div>
    </div>

    <table id="mytable"></table>
  </ContentField>
</template>
<script>
import ContentField from "@/components/ContentField.vue";

export default {
  components: {
    ContentField,
  },
};
</script>

<style scoped>
.container {
  width: 70%;
}

.row {
  margin-bottom: 1em;
}
input {
  width: 100px;
  margin: 0;
}
.form-control {
  width: 100%;
}
</style>