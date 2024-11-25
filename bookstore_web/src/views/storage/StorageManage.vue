<template>
    <div>
        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="请输入书名" suffix-icon="el-icon-search" style="width: 200px;"
                      @keyup.enter.native="loadPost"></el-input>
            <el-input v-model="id" placeholder="请输入作者" suffix-icon="el-icon-search" style="margin-left: 5px; width: 200px;"
                      @keyup.enter.native="loadPost"></el-input>
            <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
            <el-button type="success" style="margin-left: 5px;" @click="resetParam">重置</el-button>

            <el-button type="primary" style="margin-left: 5px;" @click="add">新增</el-button>
        </div>
        <el-table :data="tableData"
                  :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
                  border
        >
            <el-table-column prop="id" label="图书编号" width="100px">
            </el-table-column>
            <el-table-column prop="type" label="类型" width="180">
            </el-table-column>
            <el-table-column prop="name" label="书名" width="180">
            </el-table-column>
            <el-table-column prop="author" label="作者" width="120">
            </el-table-column>
            <el-table-column prop="price" label="价格" width="120">
            </el-table-column>
            <el-table-column prop="count" label="库存" width="120">
            </el-table-column>

            <el-table-column prop="status" label="状态" width="120">
                <template #default="{ row }">
                    <el-tag v-if="row.status == '0'" type="success">上线</el-tag>
                    <el-tag v-if="row.status == '1'" type="danger">下线</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="operate" label="操作">
                <template slot-scope="scope">
                    <el-button :type="scope.row.status == '0' ? 'warning' : 'success'" @click="doCheck(scope.row)">
                        {{ scope.row.status == '0' ? '下线' : '上线' }}
                    </el-button>
                    <el-button size="small" type="info" @click="mod(scope.row)">编辑</el-button>
                    <el-popconfirm
                            title="确定删除吗？"
                            @confirm="del(scope.row.id)"
                            style="margin-left: 5px;"
                    >
                        <el-button slot="reference" size="small" type="danger" >删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[5, 10, 20,30]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
        </el-pagination>

        <el-dialog
                title="提示"
                :visible.sync="centerDialogVisible"
                width="30%"
                center>

            <el-form ref="form" :rules="rules" :model="form" label-width="80px">
                <el-form-item label="类型" prop="type">
                    <el-col :span="20">
                        <el-input v-model="form.type"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="书名" prop="name">
                    <el-col :span="20">
                        <el-input v-model="form.name"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="作者" prop="author">
                    <el-col :span="20">
                        <el-input v-model="form.author"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="价格" prop="price">
                    <el-col :span="20">
                        <el-input v-model="form.price"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="库存" prop="count">
                    <el-col :span="20">
                        <el-input v-model="form.count"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>

<script>

function formatDate(date) {
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    const hours = ('0' + date.getHours()).slice(-2);
    const minutes = ('0' + date.getMinutes()).slice(-2);
    const seconds = ('0' + date.getSeconds()).slice(-2);
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

export default {
        name: "StorageManage",
        data() {
            return {
                tableData: [],
                pageSize:10,
                pageNum:1,
                total:0,
                name:'',
                id:'',
                status:'',
                centerDialogVisible:false,
                user : JSON.parse(sessionStorage.getItem('CurUser')),
                form:{
                    id:'',
                    name:'',
                    type:'',
                    price:'',
                    status:0
                },
                rules: {
                    name: [
                        {required: true, message: '请输入房间名', trigger: 'blur'}
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
                this.$axios.get(this.$httpUrl+'/storage/del?id='+id).then(res=>res.data).then(res=>{
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
                this.centerDialogVisible = true
                this.$nextTick(()=>{
                    //赋值到表单
                    this.form.id = row.id
                    this.form.name = row.name
                    this.form.price = row.price
                    this.form.author = row.author
                    this.form.count = row.count
                    this.form.type = row.type
                })
            },
            add(){
                this.centerDialogVisible = true
                this.$nextTick(()=>{
                    this.resetForm()
                })

            },
            doRecord(row,sta){
                let nowTime = formatDate(new Date())
                let tim = new Date()
                console.log(nowTime)
                console.log(tim)
                this.$axios.post(this.$httpUrl+'/record/save',{
                    id:row.id,
                    roomname:row.name,
                    createtime:nowTime,
                    username:this.user.name,
                    remark:sta,
                    count:row.price,

                }).then(res=>res.data).then(res=>{
                    console.log(res)
                    if(res.code!=200){
                        alert('获取数据失败')
                    }
                })
            },
            doCheck(row){
                let t;
                let sta
                if(row.status == '0'){ t = 1, sta = "退房" }else{ t = 0, sta = "入住" }
                this.doRecord(row,sta)
                this.$axios.post(this.$httpUrl+'/storage/update',{
                    id:row.id,
                    status:t,
                }).then(res=>res.data).then(res=>{
                    console.log(res)
                    if(res.code==200){
                        this.$message({
                            message: '操作成功！',
                            type: 'success'
                        });
                        this.loadPost()
                    }else{
                        alert('获取数据失败')
                    }
                })
            },
            doSave(){
                this.$axios.post(this.$httpUrl+'/storage/save',this.form).then(res=>res.data).then(res=>{
                    console.log(res)
                    if(res.code==200){

                        this.$message({
                            message: '操作成功！',
                            type: 'success'
                        });
                        this.centerDialogVisible = false
                        this.loadPost()
                        this.resetForm()
                    }else{
                        this.$message({
                            message: '操作失败！',
                            type: 'error'
                        });
                    }

                })
            },
            doMod(){
                this.$axios.post(this.$httpUrl+'/storage/update',this.form).then(res=>res.data).then(res=>{
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
            resetParam(){
                this.name=''
                this.id=''
                this.loadPost()
            },
            loadPost(){
                this.$axios.post(this.$httpUrl+'/storage/listPageC1',{
                    pageSize:this.pageSize,
                    pageNum:this.pageNum,
                    param:{
                        name:this.name,
                        id:this.id
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
            this.loadPost()
        }
    }
</script>

<style scoped>

</style>