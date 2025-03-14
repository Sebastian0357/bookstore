<template>
    <div>
        <div class="mb-3">
            <input v-model="name" class="form-control d-inline-block" placeholder="请输入分类名" style="width: 200px;"
                @keyup.enter="loadPost">
            <button class="btn btn-primary ml-2" @click="loadPost">查询</button>
            <button class="btn btn-secondary ml-2" @click="resetParam">重置</button>
            <button class="btn btn-primary ml-2" @click="add">新增</button>
        </div>

        <!-- Bootstrap Table -->
        <table class="table table-bordered">
            <thead class="thead-light">
                <tr>
                    <th>ID</th>
                    <th>分类名</th>
                    <th>备注</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(row, index) in tableData" :key="index">
                    <td>{{ row.id }}</td>
                    <td>{{ row.name }}</td>
                    <td>{{ row.remark }}</td>
                    <td>
                        <button class="btn btn-success btn-sm" @click="mod(row)">编辑</button>
                        <button class="btn btn-danger btn-sm ml-2" @click="del(row.id)">删除</button>
                    </td>
                </tr>
            </tbody>
        </table>

        <!-- Pagination -->
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item" :class="{ disabled: pageNum === 1 }">
                    <button class="page-link" @click="handleCurrentChange(pageNum - 1)">上一页</button>
                </li>
                <li class="page-item" v-for="page in totalPages" :key="page" :class="{ active: pageNum === page }">
                    <button class="page-link" @click="handleCurrentChange(page)">{{ page }}</button>
                </li>
                <li class="page-item" :class="{ disabled: pageNum === totalPages }">
                    <button class="page-link" @click="handleCurrentChange(pageNum + 1)">下一页</button>
                </li>
            </ul>
        </nav>

        <!-- Modal (for Add/Edit) -->
        <div class="modal fade" tabindex="-1" role="dialog" :class="{ 'show': centerDialogVisible }"
            style="display: block;" v-if="centerDialogVisible">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">{{ form.id ? '编辑' : '新增' }}分类</h5>
                        <button type="button" class="close" @click="centerDialogVisible = false">
                            <span>&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form @submit.prevent="save">
                            <div class="form-group">
                                <label for="categoryName">分类名</label>
                                <input type="text" v-model="form.name" class="form-control" id="categoryName"
                                    placeholder="请输入分类名" required>
                            </div>
                            <div class="form-group">
                                <label for="remark">备注</label>
                                <textarea v-model="form.remark" class="form-control" id="remark" rows="3"></textarea>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary"
                                    @click="centerDialogVisible = false">取消</button>
                                <button type="submit" class="btn btn-primary">保存</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    name: "GoodstypeManage",
    data() {
        return {
            tableData: [],
            pageSize: 10,
            pageNum: 1,
            total: 0,
            totalPages: 0,
            name: '',
            centerDialogVisible: false,
            form: {
                id: '',
                name: '',
                remark: ''
            }
        }
    },
    methods: {
        resetForm() {
            this.form = { id: '', name: '', remark: '' };
        },
        del(id) {
            this.$axios.get(this.$httpUrl + '/goodstype/del?id=' + id).then(res => res.data).then(res => {
                if (res.code === 200) {
                    this.$message({
                        message: '操作成功！',
                        type: 'success'
                    });
                    this.loadPost();
                } else {
                    this.$message({
                        message: '操作失败！',
                        type: 'error'
                    });
                }
            });
        },
        mod(row) {
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                this.form.id = row.id;
                this.form.name = row.name;
                this.form.remark = row.remark;
            });
        },
        add() {
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                this.resetForm();
            });
        },
        save() {
            if (!this.form.name) {
                this.$message({
                    message: '请输入分类名',
                    type: 'warning'
                });
                return;
            }

            const url = this.form.id ? '/goodstype/update' : '/goodstype/save';
            this.$axios.post(this.$httpUrl + url, this.form).then(res => res.data).then(res => {
                if (res.code === 200) {
                    this.$message({
                        message: '操作成功！',
                        type: 'success'
                    });
                    this.centerDialogVisible = false;
                    this.loadPost();
                    this.resetForm();
                } else {
                    this.$message({
                        message: '操作失败！',
                        type: 'error'
                    });
                }
            });
        },
        handleSizeChange(val) {
            this.pageNum = 1;
            this.pageSize = val;
            this.loadPost();
        },
        handleCurrentChange(val) {
            this.pageNum = val;
            this.loadPost();
        },
        resetParam() {
            this.name = '';
            this.loadPost();
        },
        loadPost() {
            axios.post('http://localhost:1118/goodstype/listPage', {
                pageSize: this.pageSize,
                pageNum: this.pageNum,
                param: { name: this.name }
            }, {
                headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") },
            }).then(res => {
                if (res.code === 200) {
                    this.tableData = res.data;
                    this.total = res.total;
                    this.totalPages = Math.ceil(this.total / this.pageSize);  // Calculate total pages
                } else {
                    alert('获取数据失败');
                }
            });
        }
    },
    beforeMount() {
        this.loadPost();
    }
}
</script>

<style scoped>
.modal-backdrop {
    z-index: 1040 !important;
}
</style>