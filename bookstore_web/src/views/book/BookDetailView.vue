<template>
    <div class="book-detail-container" v-if="book">
        <!-- 左侧 书籍信息 -->
        <div class="book-info">
            <div class="image-container">
                <img :src="book.img" alt="书籍封面" class="detail-img" />
            </div>

            <!-- 返回箭头 -->
            <button class="back-btn" @click="goBack">
                <i class="fas fa-arrow-left"></i> 返回
            </button>

            <h2>{{ book.bookname }}</h2>
            <p class="author">{{ book.author }}</p>
            <p><strong>价格:</strong> ¥{{ book.price }}</p>

            <button @click="addToBookshelf()" class="bookshelf-btn">加入书架</button>
            <button @click="addToOrders()" class="buy-btn">购买</button>

        </div>

        <!-- 右侧 书籍评论 -->
        <div class="comments-section">
            <p class="description description-indent" data-bs-toggle="modal" data-bs-target="#bookDescriptionModal">
                {{ book.description }}
            </p>


            <h5 class="mb-3">书籍评论:</h5>
            <!-- 书籍评分展示 -->
            <!-- <div class="rating mb-4">
                <span v-for="n in 5" :key="n" class="star" :class="n <= book.rating ? 'filled' : ''">★</span>
                <span>{{ book.rating }}/5</span>
            </div> -->

            <!-- 评论列表 -->
            <div class="comments-frame">

                <p v-if="!comments.length" class="no-comments">暂无评论</p>
                <ul v-else class="comments-list list-unstyled">
                    <li v-for="comment in comments" :key="comment.id"
                        class="comment-item mb-3 p-3 border rounded shadow-sm">
                        <div class="d-flex justify-content-between">
                            <strong>{{ comment.comment }}</strong>
                            <span class="text-muted comment-time">{{ formatDate(comment.time) }}</span>
                        </div>
                        <p class="mt-2">{{ comment.username }}</p>
                    </li>
                </ul>
            </div>

            <!-- 评论输入框 + 评分 -->
            <div class="comment-form">
                <!-- <div class="rating-form">
                    <span v-for="n in 5" :key="'rate-' + n" class="star" :class="n <= rating ? 'filled' : ''"
                        @click="setRating(n)">★</span>
                    <span>{{ rating }}/5</span>
                </div> -->
                <textarea v-model="newComment" class="form-control" placeholder="写下你的评论..." rows="2"></textarea>

                <button @click="submitComment()">提交评论</button>
            </div>
        </div>


        <!-- 模态框 Modal -->
        <div class="modal fade" id="bookDescriptionModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered" style="max-width: 500px;">
                <div class="modal-content" style="height: 430px;">
                    <div class="modal-body">
                        <!-- 简介部分 -->
                        <div class="description-section mb-4">
                            <h6><strong>简介</strong></h6>
                            <p>{{ book.description }}</p>
                        </div>

                        <!-- 版权信息部分 -->
                        <div class="description-section mb-4">
                            <h6><strong>版权信息</strong></h6>
                            <p><strong>作者：</strong>{{ book.author }}</p>
                            <p><strong>出版社：</strong>{{ book.press }}</p>
                            <p><strong>出版时间：</strong>{{ formatDateOnly(book.pressdate) }}</p>
                            <p><strong>分类：</strong>{{ categoryMap[book.type] || '未知分类' }}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    </div>
</template>


<script>
import axios from "axios";
import { formatDateOnly } from "@/util/format";
export default {
    data() {
        return {
            book: null,
            bookId:'',
            rating: 0,  // 用来存储用户评分
            newComment: "",
            comments: [],
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
        };
    },
    methods: {
        fetchBookDetail() {
             this.bookId = this.$route.params.id;
            axios
                .get(`http://localhost:1118/bookinfo/${this.bookId}`, {
                    headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") },
                })
                .then((res) => {
                    if (res.data.code === 200) {
                        this.book = res.data.data;
                    }
                })
                .catch((error) => {
                    console.error("获取书籍详情失败", error);
                });

            axios
                .get(`http://localhost:1118/comments/book/${this.bookId}`, {
                    headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") },
                })
                .then((res) => {
                    if (res.data.code === 200) {
                        this.comments = res.data.data;
                    }
                })
                .catch(() => {
                    this.comments = [];
                });
        },
        goBack() {
            this.$router.go(-1);
        },
        setRating(newRating) {
            this.rating = newRating;  // 设置用户评分
        },
        formatDateOnly(date) {
            return formatDateOnly(date)
        },
        formatDate(isoString) {
            const date = new Date(isoString);
            return date.getFullYear() + '-' +
                String(date.getMonth() + 1).padStart(2, '0') + '-' +
                String(date.getDate()).padStart(2, '0') + ' ' +
                String(date.getHours()).padStart(2, '0') + ':' +
                String(date.getMinutes()).padStart(2, '0') + ':' +
                String(date.getSeconds()).padStart(2, '0');
        },
        addToBookshelf() {
            const bookshelf = {
                userId: this.$store.state.user.id,
                bookId: this.bookId,
                joinTime: new Date()  // 使用当前时间
            };

            axios.post('http://localhost:1118/bookshelf/add', bookshelf, {
                headers: { Authorization: 'Bearer ' + localStorage.getItem('jwt_token') }
            })
                .then(res => {
                    if (res.data.code === 200) {
                        alert('加入书架成功');
                    } else {
                        alert('加入书架失败：' + res.data.msg);
                    }
                })
                .catch(err => {
                    console.error('加入书架失败', err);
                    alert('加入书架失败，请重试');
                });
        },
        addToOrders() {
            const order = {
                userid: this.$store.state.user.id,
                bookid: this.bookId,
                bookname: this.book.bookname,
                author: this.book.author,
                price: this.book.price,
                date: new Date(),           // 当前下单时间
                status: '未完成'             // 默认订单状态
            };

            axios.post('http://localhost:1118/order/add', order, {
                headers: { Authorization: 'Bearer ' + localStorage.getItem('jwt_token') }
            })
                .then(res => {
                    if (res.data.code === 200) {
                        alert('下单成功');
                    } else {
                        alert('下单失败：' + res.data.msg);
                    }
                })
                .catch(err => {
                    console.error('下单失败', err);
                    alert('下单失败，请重试');
                });
        },
        submitComment() {
            if (!this.newComment.trim()) {
                alert("评论内容不能为空");
                return;
            }

            const commentData = {
                userid: this.$store.state.user.id,
                bookid: this.book.id,
                comment: this.newComment,
                time: new Date()
            };

            axios.post('http://localhost:1118/comments/add', commentData, {
                headers: { Authorization: 'Bearer ' + localStorage.getItem('jwt_token') }
            })
                .then(res => {
                    if (res.data.code === 200) {
                        alert("评论提交成功");
                        this.newComment = "";
                        this.fetchBookDetail();  // 重新获取评论列表
                    } else {
                        alert("评论提交失败：" + res.data.msg);
                    }
                })
                .catch(err => {
                    console.error("评论提交失败", err);
                    alert("评论提交失败，请稍后重试");
                });
        }

    },
    beforeMount() {
        this.fetchBookDetail();
    },
};
</script>

<style scoped>
.modal-header .btn-close {
    background-color: transparent;
    /* 背景透明 */
    border: none;
    /* 去掉边框 */
    color: #6c757d;
    /* 设置为灰色 */
    font-size: 1.5rem;
    /* 字体大小 */
    cursor: pointer;
    /* 鼠标样式 */
    opacity: 0.6;
    /* 初始透明度 */
    border-radius: 50%;
    /* 圆形按钮 */
    width: 30px;
    /* 按钮宽度 */
    height: 30px;
    /* 按钮高度 */
    display: flex;
    justify-content: center;
    align-items: center;
}

.modal-header .btn-close:hover {
    opacity: 1;
    /* 悬停时不透明 */
    color: #007bff;
    /* 悬停时变成蓝色 */
}


.book-detail-container {
    display: flex;
    justify-content: space-between;
    width: 80%;
    min-height: 800px;
    margin: 60px auto;
    background: white;
    padding: 20px;
    border-radius: 15px;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    position: relative;
}

/* 左侧书籍信息 */
.book-info {
    width: 50%;
    padding: 20px;
    text-align: center;
}

.description {
    cursor: pointer;
    transition: color 0.2s;
}

.description:hover {
    color: #6c757d;
    /* Bootstrap 的灰色文字 */
}

.image-container {
    display: flex;
    justify-content: center;
    margin-bottom: 15px;
}

.detail-img {
    width: 60%;
    max-width: 250px;
    height: auto;
    border-radius: 5px;
}

h2 {
    margin-top: 15px;
    font-size: 24px;
}

.author {
    font-size: 16px;
    color: #555;
    margin-bottom: 10px;
}

.rating {
    font-size: 18px;
    color: #f8c200;
}

.star {
    font-size: 20px;
    color: #ddd;
    /* 默认空星的颜色 */
}

.star.filled {
    color: #f8c200;
}

.description {
    margin-top: 15px;
    font-size: 19px;
    color: #2e2e2e;
    min-height: 25%;
}

button {
    margin-top: 20px;
    padding: 8px 12px;
    border: none;
    background: #007bff;
    color: white;
    cursor: pointer;
    border-radius: 5px;
}

.bookshelf-btn {
    background-color: #28a745;
    margin-top: 10px;
}

.bookshelf-btn:hover {
    background-color: #218838;
}

.modal-sm-custom {
    max-width: 500px;
    height: 430px;
}

.modal-sm-custom .modal-content {
    height: 100%;
    overflow-y: auto;
}

.modal-body {
    font-size: 14px;
    color: #555;
}

.description-section,
.copyright-section {
    margin-bottom: 20px;
}

h6 {
    font-weight: bold;
    font-size: 16px;
    margin-bottom: 10px;
}

p {
    font-size: 14px;
    color: #666;
}

.description {
    cursor: pointer;
    transition: background-color 0.2s;
}

.description:hover {
    background-color: #f2f2f2;
}

/* 返回箭头按钮 */
.back-btn {
    position: absolute;
    top: 20px;
    left: 20px;
    background: transparent;
    color: #007bff;
    border: none;
    font-size: 20px;
    cursor: pointer;
    display: flex;
    align-items: center;
}

.back-btn i {
    margin-right: 5px;
}

/* 右侧布局 */
.right-section {
    width: 50%;
    display: flex;
    flex-direction: column;
}

/* 右侧布局 */
.comments-section {
    width: 50%;
    padding: 20px;
}

.comments-section h3 {
    margin-bottom: 20px;
    font-size: 22px;
}

.comments-frame {
    max-height: 250px;
    /* 限制评论框的最大高度 */
    overflow-y: auto;
    /* 启用竖向滚动 */
    padding-right: 10px;
    /* 右侧间距 */
}

.comments-list {
    list-style-type: none;
    padding: 0;
    margin: 0;
}

.comment-item {
    margin-bottom: 1px;
    /* 每条评论之间的间距 */
    display: flex;
    justify-content: space-between;
    /* 左右对齐 */
    align-items: center;
}

.comment-content {
    flex: 1;
    /* 评论内容部分占满剩余空间 */
}

.comment-time {
    font-size: 12px;
    color: #888;
    margin-left: 10px;
    /* 时间距离评论内容有间隔 */
    white-space: nowrap;
    /* 防止时间换行 */
}

/* 评分功能 */
.rating-form {
    display: flex;
    align-items: center;
}

.rating-form .star {
    font-size: 24px;
    color: #ddd;
    cursor: pointer;
}

.rating-form .star.filled {
    color: #f8c200;
}

.rating-form span {
    margin-left: 5px;
}

.comment-form {
    margin-top: 10px;
    display: flex;
    flex-direction: column;
}


.comment-form textarea {
    padding: 10px;
    width: 100%;
    border: 1px solid #ccc;
    border-radius: 5px;
    resize: none;
}

.comment-form button {
    margin-top: 10px;
    padding: 8px 12px;
    border: none;
    background: #007bff;
    color: white;
    cursor: pointer;
    border-radius: 5px;
}

.comment-form button:hover {
    background: #0056b3;
}

ul {
    list-style: none;
    padding: 0;
}

li {
    border-bottom: 1px solid #ddd;
    padding: 10px 0;
}

small {
    color: gray;
    font-size: 12px;
}

.description-section {
    flex: 1;
    padding: 20px;
    border-bottom: 1px solid #ddd;
}

.comments-section {
    flex: 1;
    padding: 20px;
}

.comments-section h3 {
    margin-bottom: 20px;
    font-size: 22px;
}

.bookshelf-btn {
    background-color: #28a745;
    margin-top: 10px;
    margin-right: 10px;
    /* 添加右边距 */
}

.bookshelf-btn:hover {
    background-color: #218838;
}

.buy-btn {
    background-color: #ffc107;
    color: black;
    margin-top: 10px;
}

.buy-btn:hover {
    background-color: #e0a800;
}

.description-indent {
    text-indent: 2em;
}
</style>
