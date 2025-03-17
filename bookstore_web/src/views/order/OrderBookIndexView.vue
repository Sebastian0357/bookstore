<template>
    <div class="order-container">
        <h2>我的订单</h2>

        <!-- 订单表格 -->
        <table class="table table-bordered">
            <thead class="thead-light">
                <tr>
                    <!-- <th>订单编号</th> -->
                    <th>书籍名称</th>
                    <th>价格</th>
                    <th>订单状态</th>
                    <th>下单时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="order in orders" :key="order.id">
                    <!-- <td>{{ order.id }}</td> -->
                    <td>{{ order.bookname }}</td>
                    <td>¥{{ order.price }}</td>
                    <td>{{ order.status }}</td>
                    <td>{{ formatDate(order.date) }}</td>
                    <td>
                        <button class="btn btn-info btn-sm" @click="viewOrder(order)">查看</button>
                        <button class="btn btn-danger btn-sm ml-2" v-if="order.status === 'pending'"
                            @click="cancelOrder(order.id)">取消</button>
                        <button class="btn btn-primary btn-sm ml-2" @click="openReview(order)">评论</button>
                    </td>


                </tr>
            </tbody>
        </table>

        <!-- 分页 -->
        <!-- <nav>
            <ul class="pagination justify-content-center">
                <li class="page-item" :class="{ disabled: pageNum === 1 }">
                    <a class="page-link" href="#" @click="handlePageChange(pageNum - 1)">上一页</a>
                </li>
                <li class="page-item" v-for="n in Math.ceil(total / pageSize)" :key="n"
                    :class="{ active: pageNum === n }">
                    <a class="page-link" href="#" @click="handlePageChange(n)">{{ n }}</a>
                </li>
                <li class="page-item" :class="{ disabled: pageNum === Math.ceil(total / pageSize) }">
                    <a class="page-link" href="#" @click="handlePageChange(pageNum + 1)">下一页</a>
                </li>
            </ul>
        </nav> -->

        <!-- 订单详情弹窗 -->
        <div class="modal" tabindex="-1" role="dialog" v-if="showOrderDetail">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">订单详情</h5>
                    </div>
                    <div class="modal-body">
                        <p><strong>书籍名称:</strong> {{ selectedOrder.bookname }}</p>
                        <p><strong>价格:</strong> ¥{{ selectedOrder.price }}</p>
                        <p><strong>订单状态:</strong> {{ selectedOrder.status }}</p>
                        <p><strong>下单时间:</strong> {{ formatDate(selectedOrder.date) }}</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" @click="showOrderDetail = false">关闭</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 评论弹窗 -->
        <div class="modal" tabindex="-1" role="dialog" v-if="showReviewModal">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">书籍评论</h5>
                    </div>
                    <div class="modal-body">
                        <p><strong>书籍名称:</strong> {{ selectedOrder.bookname }}</p>

                        <!-- 评分（星星选择） -->
                        <div class="rating">
                            <span v-for="n in 5" :key="n" class="star" :class="{ 'filled': n <= rating }"
                                @click="setRating(n)">★</span>
                        </div>


                        <!-- 评论输入框 -->
                        <textarea v-model="commentText" placeholder="请输入您的评论..." class="form-control"></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" @click="showReviewModal = false">关闭</button>
                        <button type="button" class="btn btn-success" @click="submitReview">提交</button>
                    </div>
                </div>
            </div>
        </div>


    </div>
</template>

<script>
import axios from "axios";
export default {
    data() {
        return {
            orders: [],
            pageSize: 10,
            pageNum: 1,
            total: 0,
            showReviewModal: false,
            showOrderDetail: false, 
            selectedOrder: {},
            rating: 0, // 评分
            commentText: "", // 评论文本
        };
    },

    methods: {
        loadOrders() {
            const userId = this.$store.state.user.id;
            axios.get(`http://localhost:1118/order/list/${userId}`, {
                headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") },
            })
                .then(res => {
                    this.orders = res.data;
                })
                .catch(err => {
                    console.error('获取订单失败', err);
                });
        },
        handlePageChange(val) {
            this.pageNum = val;
            this.loadOrders();
        },
        viewOrder(order) {
            this.selectedOrder = order;
            this.showOrderDetail = true;
        },
        cancelOrder(orderId) {
            axios
                .post(`http://localhost:1118/orders/cancel/${orderId}`, {}, {
                    headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") },
                })
                .then((res) => {
                    if (res.data.code === 200) {
                        alert("订单已取消");
                        this.loadOrders();
                    } else {
                        alert("取消失败");
                    }
                })
                .catch((error) => {
                    console.error("取消订单失败", error);
                    alert("取消失败，请重试");
                });
        },
        // 打开评论弹窗
        openReview(order) {
            this.selectedOrder = order;
            this.rating = 0; // 重置评分
            this.commentText = ""; // 重置评论
            this.showReviewModal = true;
        },

        // 设置评分
        setRating(star) {
            this.rating = star;
        },

        // 提交评论
        submitReview() {
            if (!this.rating) {
                alert("请给书籍评分");
                return;
            }
            const commentData = {
                userid: this.$store.state.user.id,
                bookid: this.selectedOrder.bookid,
                comment: this.commentText,
                time: new Date().toISOString(),
                rating: this.rating,
            };
            axios
                .post("http://localhost:1118/comments/add", commentData, {
                    headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") },
                })
                .then((res) => {
                    if (res.data.code === 200) {
                        alert("评论提交成功");
                        this.showCommentModal = false;
                        this.loadOrders(); // 提交评论后刷新订单列表
                    } else {
                        alert("评论提交失败");
                    }
                })
                .catch((err) => {
                    console.error("提交评论失败", err);
                    alert("提交失败，请重试");
                });
        },
        formatDate(isoString) {
            const date = new Date(isoString);
            return date.getFullYear() + "-" +
                String(date.getMonth() + 1).padStart(2, "0") + "-" +
                String(date.getDate()).padStart(2, "0") + " " +
                String(date.getHours()).padStart(2, "0") + ":" +
                String(date.getMinutes()).padStart(2, "0") + ":" +
                String(date.getSeconds()).padStart(2, "0");
        }
    },
    beforeMount() {
        this.loadOrders();
    },
};
</script>

<style scoped>
.order-container {
    width: 80%;
    min-height: 800px;
    margin: 20px auto;
    background: white;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

h2 {
    text-align: center;
    margin-bottom: 20px;
}

/* 表格样式 */
.table {
    width: 100%;
    border-collapse: collapse;
}

thead {
    background-color: #f8f9fa;
}

th,
td {
    padding: 12px;
    text-align: center;
    border-bottom: 1px solid #ddd;
}

/* 按钮样式 */
button {
    padding: 5px 10px;
    border: none;
    color: white;
    cursor: pointer;
    border-radius: 5px;
}

.btn-info {
    background-color: #17a2b8;
}

.btn-danger {
    background-color: #dc3545;
}

.btn-danger:hover {
    background-color: #c82333;
}

/* 分页样式 */
.pagination {
    margin-top: 20px;
}

.page-item {
    margin: 0 5px;
}

.page-link {
    color: #007bff;
    cursor: pointer;
}

.page-item.active .page-link {
    background-color: #007bff;
    color: white;
}

/* 弹窗样式 */
.modal {
    display: block !important;
    visibility: visible !important;
    opacity: 1 !important;
}

.modal-dialog {
    max-width: 500px;
    margin: 30px auto;
}

.star {
    font-size: 24px;
    color: gray;
    cursor: pointer;
    transition: color 0.2s;
}

.filled {
    color: gold;
}
</style>