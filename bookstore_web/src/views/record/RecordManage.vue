<template>
    <div>
<!--        <div style="margin-bottom: 5px;">-->
<!--            <el-input v-model="name" placeholder="请输入房间号" style="width: 200px;" suffix-icon="el-icon-search"-->
<!--                      @keyup.enter.native="loadPost"></el-input>-->

<!--            <el-button style="margin-left: 5px;" type="primary" @click="loadPost">查询</el-button>-->
<!--            <el-button type="success" @click="resetParam">重置</el-button>-->


<!--        </div>-->
        <el-table :data="tableData"
                  :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
                  border
        >
            <el-table-column label="ID" prop="id" width="60">
            </el-table-column>
            <el-table-column label="图书名" prop="roomname" width="150">
            </el-table-column>
            <el-table-column label="用户名" prop="username" width="150">
            </el-table-column>
            <el-table-column :formatter="formatDateDetail" label="评论时间" prop="createtime" width="180">
            </el-table-column>
            <el-table-column label="内容" prop="remark">
            </el-table-column>
        </el-table>
        <el-pagination
                :current-page="pageNum"
                :page-size="pageSize"
                :page-sizes="[5, 10, 20,30]"
                :total="total"
                layout="total, sizes, prev, pager, next, jumper"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange">
        </el-pagination>
    </div>
</template>

<script>
export default {
    name: "RecordManage",
    data() {
        return {
            user: JSON.parse(sessionStorage.getItem('CurUser')),
            tableData: [],
            pageSize: 10,
            pageNum: 1,
            total: 0,
            name: '',
            storage: '',
            centerDialogVisible: false,
            form: {
                id: '',
                name: '',
                storage: '',
                remark: ''
            },
        }
    },
    methods: {
        formatDate(row, column) {
            // 获取单元格数据
            let data = row[column.property]
            if (data == null) {
                return null
            }
            let dt = new Date(data)
            return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate()
        },
        formatDateDetail(row, column) {
            // 获取单元格数据
            let data = row[column.property]
            if (data == null) {
                return null
            }
            let dt = new Date(data)
            return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt.getSeconds()
        },

        formatGoodstype(row) {
            let temp = this.goodstypeData.find(item => {
                return item.id == row.goodstype
            })

            return temp && temp.name
        },
        resetForm() {
            this.$refs.form.resetFields();
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            this.pageNum = 1
            this.pageSize = val
            this.loadPost()
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.pageNum = val
            this.loadPost()
        },
        resetParam() {
            this.name = ''
            this.storage = ''
            this.goodstype = ''
        },
        loadStorage() {
            this.$axios.get(this.$httpUrl + '/storage/list').then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {
                    this.storageData = res.data
                } else {
                    alert('获取数据失败')
                }

            })
        },
        loadGoodstype() {
            this.$axios.get(this.$httpUrl + '/goodstype/list').then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {
                    this.goodstypeData = res.data
                } else {
                    alert('获取数据失败')
                }

            })
        },
        loadPost() {
            this.$axios.post(this.$httpUrl + '/record/listPageC1', {
                pageSize: this.pageSize,
                pageNum: this.pageNum,
                param: {
                    name: this.name,
                    goodstype: this.goodstype + '',
                    storage: this.storage + '',
                }
            }).then(res => res.data).then(res => {
                console.log(res)
                if (res.code == 200) {
                    this.tableData = res.data
                    this.total = res.total
                } else {
                    alert('获取数据失败')
                }

            })
        },
    },
    beforeMount() {
        this.loadStorage()
        this.loadGoodstype()
        this.loadPost()

    }
}
</script>

<style scoped>

</style>