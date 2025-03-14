<template>
    <div>
      <div class="mb-3">
        <input v-model="name" type="text" class="form-control d-inline-block" style="width: 200px;" placeholder="请输入名字" @keyup.enter="loadPost" />
        <select v-model="sex" class="form-select d-inline-block ms-2" style="width: auto;" aria-label="性别">
          <option v-for="item in sexs" :key="item.value" :value="item.value">{{ item.label }}</option>
        </select>
        <button class="btn btn-primary ms-2" @click="loadPost">查询</button>
        <button class="btn btn-success ms-2" @click="resetParam">重置</button>
        <button class="btn btn-primary ms-2" @click="add">新增</button>
      </div>
  
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>ID</th>
            <th>账号</th>
            <th>姓名</th>
            <th>年龄</th>
            <th>性别</th>
            <th>角色</th>
            <th>电话</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="row in tableData" :key="row.id">
            <td>{{ row.id }}</td>
            <td>{{ row.no }}</td>
            <td>{{ row.name }}</td>
            <td>{{ row.age }}</td>
            <td>
              <span class="badge" :class="row.sex === 1 ? 'bg-primary' : 'bg-success'">{{ row.sex === 1 ? '男' : '女' }}</span>
            </td>
            <td>
              <span :class="row.roleId === 0 ? 'badge bg-danger' : (row.roleId === 1 ? 'badge bg-primary' : 'badge bg-success')">
                {{ row.roleId === 0 ? '超级管理员' : (row.roleId === 1 ? '管理员' : '用户') }}
              </span>
            </td>
            <td>{{ row.phone }}</td>
            <td>
              <button class="btn btn-success btn-sm" @click="mod(row)">编辑</button>
              <button class="btn btn-danger btn-sm ms-2" @click="del(row.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
  
      <pagination :current-page="pageNum" :total="total" :page-size="pageSize" @page-change="handlePageChange" />
  
      <div v-if="centerDialogVisible" class="modal fade show" tabindex="-1" style="display: block;">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">提示</h5>
              <button type="button" class="btn-close" @click="centerDialogVisible = false" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <form @submit.prevent="save">
                <div class="mb-3">
                  <label for="no" class="form-label">账号</label>
                  <input id="no" v-model="form.no" type="text" class="form-control" required />
                </div>
                <div class="mb-3">
                  <label for="name" class="form-label">名字</label>
                  <input id="name" v-model="form.name" type="text" class="form-control" required />
                </div>
                <div class="mb-3">
                  <label for="password" class="form-label">密码</label>
                  <input id="password" v-model="form.password" type="password" class="form-control" required />
                </div>
                <div class="mb-3">
                  <label for="age" class="form-label">年龄</label>
                  <input id="age" v-model="form.age" type="number" class="form-control" required />
                </div>
                <div class="mb-3">
                  <label for="sex" class="form-label">性别</label>
                  <div>
                    <input type="radio" id="male" value="1" v-model="form.sex" />
                    <label for="male">男</label>
                    <input type="radio" id="female" value="0" v-model="form.sex" />
                    <label for="female">女</label>
                  </div>
                </div>
                <div class="mb-3">
                  <label for="phone" class="form-label">电话</label>
                  <input id="phone" v-model="form.phone" type="text" class="form-control" required />
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" @click="centerDialogVisible = false">取消</button>
                  <button type="submit" class="btn btn-primary">确定</button>
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
        name: "AdminManage",
        data() {
            let checkAge = (rule, value, callback) => {
                if(value>150){
                    callback(new Error('年龄输入过大'));
                }else{
                    callback();
                }
            };
            let checkDuplicate =(rule,value,callback)=>{
                if(this.form.id){
                    return callback();
                }
                this.$axios.get(this.$httpUrl+"/user/findByNo?no="+this.form.no).then(res=>res.data).then(res=>{
                    if(res.code!=200){

                        callback()
                    }else{
                        callback(new Error('账号已经存在'));
                    }
                })
            };

            return {
                tableData: [],
                pageSize:10,
                pageNum:1,
                total:0,
                name:'',
                sex:'',
                sexs:[
                    {
                        value: '1',
                        label: '男'
                    }, {
                        value: '0',
                        label: '女'
                    }
                ],
                centerDialogVisible:false,
                form:{
                    id:'',
                    no:'',
                    name:'',
                    password:'',
                    age:'',
                    phone:'',
                    sex:'0',
                    roleId:'1'
                },
                rules: {
                    no: [
                        {required: true, message: '请输入账号', trigger: 'blur'},
                        {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'},
                        {validator:checkDuplicate,trigger: 'blur'}
                    ],
                    name: [
                        {required: true, message: '请输入名字', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'}
                    ],
                    age: [
                        {required: true, message: '请输入年龄', trigger: 'blur'},
                        {min: 1, max: 3, message: '长度在 1 到 3 个位', trigger: 'blur'},
                        {pattern: /^([1-9][0-9]*){1,3}$/,message: '年龄必须为正整数字',trigger: "blur"},
                        {validator:checkAge,trigger: 'blur'}
                    ],
                    phone: [
                        {required: true,message: "手机号不能为空",trigger: "blur"},
                        {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur"}
                    ]
                }
            }
        },
        methods:{
            resetForm() {
                this.$refs.form.resetFields();
            },
            del(id){
                console.log(id)

                this.$axios.get(this.$httpUrl+'/user/del?id='+id).then(res=>res.data).then(res=>{
                    console.log(res)
                    if(res.code==200){

                        this.$message({
                            message: '操作成功！',
                            type: 'success'
                        });
                        this.loadPost()
                    }else{
                        this.$message({
                            message: '操作失败！',
                            type: 'error'
                        });
                    }

                })
            },
            mod(row){
                console.log(row)

                this.centerDialogVisible = true
                this.$nextTick(()=>{
                    //赋值到表单
                    this.form.id = row.id
                    this.form.no = row.no
                    this.form.name = row.name
                    this.form.password = ''
                    this.form.age = row.age +''
                    this.form.sex = row.sex +''
                    this.form.phone = row.phone
                    this.form.roleId = row.roleId
                })
            },
            add(){

                this.centerDialogVisible = true
                this.$nextTick(()=>{
                    this.resetForm()
                })

            },
            doSave(){
                this.$axios.post(this.$httpUrl+'/user/save',this.form).then(res=>res.data).then(res=>{
                    console.log(res)
                    if(res.code==200){

                        this.$message({
                            message: '操作成功！',
                            type: 'success'
                        });
                        this.centerDialogVisible = false
                        this.loadPost()
                        this. resetForm()
                    }else{
                        this.$message({
                            message: '操作失败！',
                            type: 'error'
                        });
                    }

                })
            },
            doMod(){
                this.$axios.post(this.$httpUrl+'/user/update',this.form).then(res=>res.data).then(res=>{
                    console.log(res)
                    if(res.code==200){

                        this.$message({
                            message: '操作成功！',
                            type: 'success'
                        });
                        this.centerDialogVisible = false
                        this.loadPost()
                        this. resetForm()
                    }else{
                        this.$message({
                            message: '操作失败！',
                            type: 'error'
                        });
                    }

                })
            },
            save(){
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        if(this.form.id){
                            this.doMod();
                        }else{
                            this.doSave();
                        }
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });

            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.pageNum=1
                this.pageSize=val

                this.loadPost()

            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.pageNum=val

                this.loadPost()
            },
            loadGet(){
                this.$axios.get(this.$httpUrl+'/user/list').then(res=>res.data).then(res=>{
                    console.log(res)
                })
            },
            resetParam(){
                this.name=''
                this.sex=''
                this.loadPost()
            },
            loadPost(){
                this.$axios.post(this.$httpUrl+'/user/listPageC1',{
                    pageSize:this.pageSize,
                    pageNum:this.pageNum,
                    param:{
                        name:this.name,
                        sex:this.sex,
                        roleId:'1'
                    }
                }).then(res=>res.data).then(res=>{
                    console.log(res)
                    if(res.code==200){
                        this.tableData=res.data
                        this.total=res.total
                    }else{
                        alert('获取数据失败')
                    }

                })
            }
        },
        beforeMount() {
            //this.loadGet();
            this.loadPost()
        }
    }
</script>

<style scoped>

</style>