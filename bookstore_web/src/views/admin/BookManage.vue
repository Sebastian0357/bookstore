<template>
    <div>
        <div style="margin-bottom: 5px;">
            <div class="input-group mb-2">
                <input v-model="name" type="text" class="form-control" placeholder="请输入图书名"
                    style="max-width: 200px; margin-left: 5px;" @keyup.enter="loadPost">
                <div class="input-group-append">
                    <button class="btn btn-primary" style="margin-left: 5px;" @click="loadPost">查询</button>
                    <button class="btn btn-success" style="margin-left: 5px;" @click="resetParam">重置</button>
                </div>
            </div>

            <button class="btn btn-primary" style="margin-left: 5px;" @click="add">新增</button>
        </div>

        <!-- 表格 -->
        <table class="table table-bordered">
            <thead class="thead-light">
                <tr>
                    <th>图书名称</th>
                    <th>作者</th>
                    <th>分类</th>
                    <th>出版社</th>
                    <th>出版日期</th>
                    <th>价格</th>
                    <th>库存</th>
                    <th>评分</th>
                    <th>热度</th>
                    <th>销量</th>
                    <th>搜索</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="row in tableData" :key="row.id">
                    <td>{{ row.bookname }}</td>
                    <td>{{ row.author }}</td>
                    <td>{{ categoryMap[row.type] || '未知' }}</td>
                    <td>{{ row.press }}</td>
                    <td>{{ formatDate(row.pressdate) }}</td>
                    <td>{{ row.price }}</td>
                    <td>{{ row.count }}</td>
                    <td>{{ row.rating }}</td>
                    <td>{{ row.popularity }}</td>
                    <td>{{ row.sales }}</td>
                    <td>{{ row.search }}</td>
                    <td>
                        <button class="btn btn-success btn-sm" @click="mod(row)">编辑</button>
                        <button class="btn btn-danger btn-sm ml-2" style="margin-left: 5px;"
                            @click="del(row.id)">删除</button>
                    </td>
                </tr>
            </tbody>
        </table>

        <!-- 分页 -->
        <nav>
            <ul class="pagination justify-content-center">
                <li class="page-item" :class="{ disabled: pageNum === 1 }">
                    <a class="page-link" href="#" @click="handleCurrentChange(pageNum - 1)">上一页</a>
                </li>
                <li class="page-item" v-for="n in Math.ceil(total / pageSize)" :key="n"
                    :class="{ active: pageNum === n }">
                    <a class="page-link" href="#" @click="handleCurrentChange(n)">{{ n }}</a>
                </li>
                <li class="page-item" :class="{ disabled: pageNum === Math.ceil(total / pageSize) }">
                    <a class="page-link" href="#" @click="handleCurrentChange(pageNum + 1)">下一页</a>
                </li>
            </ul>
        </nav>

        <!-- 编辑弹窗 -->
        <div class="modal" tabindex="-1" role="dialog" v-if="centerDialogVisible">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">图书维护</h5>
                    </div>
                    <div class="modal-body">
                        <form ref="form">
                            <div class="form-group">
                                <label>图书名称</label>
                                <input type="text" class="form-control" v-model="form.bookname" required />
                            </div>

                            <!-- 图片上传 -->
                            <div class="form-group">
                                <label>上传图片</label>
                                <input type="file" class="form-control-file" @change="handleFileUpload"
                                    accept="image/jpeg, image/png">
                                <div v-if="form.img" class="mt-2">
                                    <img :src="form.img" alt="预览图片" class="img-thumbnail" style="max-width: 200px;">
                                </div>
                            </div>

                            <div class="form-group">
                                <label>作者</label>
                                <input type="text" class="form-control" v-model="form.author" />
                            </div>

                            <div class="form-group">
                                <label>出版社</label>
                                <input type="text" class="form-control" v-model="form.press" />
                            </div>

                            <!-- :max限制日期不能为未来日 -->
                            <div class="form-group">
                                <label>出版日期</label>
                                <input type="date" class="form-control" v-model="form.pressdate" :max="today" />
                            </div>

                            <!-- step="0.01" 表示支持 0.01 为单位的小数输入 -->
                            <div class="form-group">
                                <label>价格</label>
                                <input type="number" class="form-control" v-model="form.price" step="0.01" />
                            </div>

                            <div class="form-group">
                                <label>库存</label>
                                <input type="number" class="form-control" v-model="form.count" />
                            </div>

                            <div class="form-group">
                                <label>分类</label>
                                <select class="form-control" v-model="form.type">
                                    <option v-for="(label, key) in categoryMap" :key="key" :value="parseInt(key)">
                                        {{ label }}
                                    </option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>描述</label>
                                <textarea class="form-control" v-model="form.description"></textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" @click="centerDialogVisible = false">取
                            消</button>
                        <button type="button" class="btn btn-primary" @click="save">确 定</button>
                    </div>
                </div>
            </div>
        </div>

        <MessageBox ref="messageBox" />
    </div>
</template>

<script>
import axios from 'axios';
import MessageBox from '@/components/MessageBox.vue';
import { formatDate } from '@/util/format';
export default {
    components: { MessageBox },
    data() {
        return {
            tableData: [],
            pageSize: 10,
            pageNum: 1,
            total: 0,
            name: '',
            centerDialogVisible: false,
            form: {
                id: '',
                bookname: '',
                type: '',
                author: '',
                press: '',
                pressdate: '',
                price: '',
                count: '',
                img: '',
                popularity: '',
                sales: '',
                rating: '',
                search: '',
                description: ''
            },
            categoryMap: {
                1: "精品小说",
                2: "历史",
                3: "文学",
                4: "艺术",
                5: "人物传记",
                6: "哲学宗教",
                7: "计算机",
                8: "心理",
                9: "社会文化",
                10: "个人成长",
                11: "经济理财",
                12: "政治军事",
                13: "童书",
                14: "教育学习",
                15: "科学技术",
                16: "生活百科",
                17: "期刊杂志",
                18: "原版书",
                19: "医学健康",
                20: "男生小说",
                21: "女生小说"
            },
            file: null
        };
    },
    methods: {
        loadPost() {
            axios.post('http://localhost:1118/bookinfo/listPage', {
                pageSize: this.pageSize,
                pageNum: this.pageNum,
                param: { name: this.name },
            }, {
                headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") }
            }).then(res => {
                if (res.data.code === 200) {
                    this.tableData = res.data.data;
                    this.total = res.data.total;
                } else {
                    this.$refs.messageBox.showFeedback('error', '获取数据失败');
                }
            });
        },
        formatDate(date) {
            return formatDate(date)
        },
        handleFileUpload(event) {
            this.file = event.target.files[0];
            const allowedTypes = ["image/jpeg", "image/png"];
            if (!allowedTypes.includes(this.file.type)) {
                this.$refs.messageBox.showFeedback('error', '仅支持 JPG/PNG 图片格式');
                this.file = null;
                return;
            }
            if (this.file.size > 10 * 1024 * 1024) {
                this.$refs.messageBox.showFeedback('error', '文件大小不能超过 10MB');
                this.file = null;
            }
        },
        submitFile() {
            return new Promise((resolve, reject) => {
                if (!this.file) {
                    resolve(null);
                    return;
                }
                const formData = new FormData();
                formData.append("file", this.file);
                axios.post("http://localhost:1118/upload/file", formData, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                        "Content-Type": "multipart/form-data",
                    }
                }).then(res => {
                    resolve(res.data.url);
                }).catch(err => {
                    this.$refs.messageBox.showFeedback('error', "上传失败：" + err.response.data);
                    reject(err);
                });
            });
        },
        handleCurrentChange(val) {
            this.pageNum = val;
            this.loadPost();
        },
        mod(row) {
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                const formattedRow = { ...row };
                if (formattedRow.pressdate) {
                    formattedRow.pressdate = formattedRow.pressdate.slice(0, 10); // 只取 YYYY-MM-DD
                }
                this.form = formattedRow;
            });
        },
        add() {
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                const today = new Date().toISOString().slice(0, 10); // 获取当前日期并格式化为 YYYY-MM-DD
                this.form = {
                    id: '',
                    bookname: '',
                    type: '',
                    author: '',
                    press: '',
                    pressdate: today, // 设置默认值为当前日期
                    price: '',
                    count: '',
                    img: '',
                    popularity: '',
                    sales: '',
                    rating: '',
                    search: '',
                    description: ''
                };
            });
        },
        save() {
            this.submitFile().then(fileUrl => {
                if (fileUrl) {
                    this.form.img = fileUrl;
                }
                const apiUrl = this.form.id ? '/bookinfo/update' : '/bookinfo/save';
                if (this.form.price < 0){
                    this.$refs.messageBox.showFeedback('error', '价格不小于0');
                    return;
                }
                axios.post('http://localhost:1118' + apiUrl, this.form, {
                    headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") }
                }).then(res => {
                    if (res.data.code === 200) {
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
            axios.post('http://localhost:1118/bookinfo/delete', { id }, {
                headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") }
            }).then(res => {
                if (res.data.code === 200) {
                    this.$refs.messageBox.showFeedback('success', '删除成功');
                    this.loadPost();
                } else {
                    this.$refs.messageBox.showFeedback('error', '删除失败');
                }
            });
        },
        resetParam() {
            this.name = '';
            this.loadPost();
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
    visibility: visible !important;
    opacity: 1 !important;
}

.modal-dialog {
    max-width: 600px;
    margin: 30px auto;
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