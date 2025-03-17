<template>
    <div class="bookshelf-container">
        <h1 class="bookshelf-title">我的书架</h1>

        <div class="book-list">
            <div v-for="book in books" :key="book.id" class="book-card">
                <div class="book-actions">
                    <input type="checkbox" v-model="selectedBooks" :value="book" class="book-checkbox" />
                </div>
                <img :src="book.img" alt="书籍封面" class="book-image" />
                <div class="book-info">
                    <h3 class="book-title">{{ book.bookname }}</h3>
                    <p class="book-author">{{ book.author }}</p>
                </div>
            </div>
        </div>

        <button class="btn btn-primary purchase-btn" @click="openConfirmDialog('purchase')"
            :disabled="selectedBooks.length === 0">
            购买选中的书籍
        </button>
        <button class="btn btn-danger remove-btn" @click="openConfirmDialog('remove')"
            :disabled="selectedBooks.length === 0">
            移除选中的书籍
        </button>

        <div v-if="books.length === 0" class="no-books-message">
            <p>您的书架是空的，快来添加一些书籍吧！</p>
        </div>

        <!-- 自定义弹窗 -->
        <div v-if="showDialog" class="dialog-overlay">
            <div class="dialog">
                <h3>{{ dialogTitle }}</h3>
                <p>{{ dialogMessage }}</p>
                <div class="dialog-actions">
                    <button @click="handleDialogResponse(true)" class="btn btn-primary">确定</button>
                    <button @click="handleDialogResponse(false)" class="btn btn-secondary">取消</button>
                </div>
            </div>
        </div>
        <!-- 成功/失败消息 -->
        <div v-if="showMessage" class="message-box" :class="messageType">
            {{ messageText }}
        </div>
    </div>
</template>

<script>
import axios from "axios";
export default {
    data() {
        return {
            books: [],
            selectedBooks: [],
            orders: [],
            showDialog: false,
            dialogTitle: '',
            dialogMessage: '',
            action: '',
            showMessage: false,
            messageText: '',
            messageType: '' // 'success' or 'error'
        };
    },
    methods: {
        getBookshelf() {
            const userId = this.$store.state.user.id;
            axios.get(`http://localhost:1118/bookshelf/list/${userId}`, {
                headers: { Authorization: 'Bearer ' + localStorage.getItem('jwt_token') }
            })
                .then(res => {
                    this.books = res.data;
                })
                .catch(err => {
                    console.error('获取书架失败', err);
                });
        },
        openConfirmDialog(action) {
            this.action = action;
            if (action === 'purchase') {
                this.dialogTitle = '确认购买';
                this.dialogMessage = '确定要购买选中的书籍吗？';
            } else if (action === 'remove') {
                this.dialogTitle = '确认移除';
                this.dialogMessage = '确定要移除选中的书籍吗？';
            }
            this.showDialog = true;
        },
        handleDialogResponse(isConfirmed) {
            this.showDialog = false;
            if (isConfirmed) {
                if (this.action === 'purchase') {
                    this.purchaseBooks();
                } else if (this.action === 'remove') {
                    this.removeBooks();
                }
            }
        },
        showFeedback(type, message) {
            this.messageType = type;
            this.messageText = message;
            this.showMessage = true;

            // 3 秒后自动消失
            setTimeout(() => {
                this.showMessage = false;
            }, 3000);
        },

        purchaseBooks() {
            const userId = this.$store.state.user.id;
            const orderData = this.selectedBooks.map(book => ({
                id: book.id,
                bookname: book.bookname,
                type: book.type,
                author: book.author,
                press: book.press,
                pressdate: book.pressdate,
                price: book.price,
                count: book.count,
                img: book.img,
                popularity: book.popularity,
                sales: book.sales,
                rating: book.rating,
                search: book.search,
                description: book.description
            }));

            axios.post('http://localhost:1118/bookshelf/purchase', orderData, {
                params: { userId },
                headers: { Authorization: 'Bearer ' + localStorage.getItem('jwt_token') }
            })
                .then(res => {
                    if (res.data.code === 200) {
                        this.orders.push(...res.data.data);
                        this.selectedBooks = [];
                        this.showFeedback('success', '购买成功！');
                    } else {
                        this.showFeedback('error', res.data.message || '购买未完成');
                    }
                })
                .catch(err => {
                    console.error('购买失败', err);
                    this.showFeedback('error', '购买失败，请稍后重试');
                });
        },

        // 修正的移除书籍请求
        removeBooks() {
            const userId = this.$store.state.user.id; // 获取当前用户的 ID
            const bookIds = this.selectedBooks.map(book => book.id); // 获取选中的书籍 ID

            // 这里将请求修正回去
            axios.post('http://localhost:1118/bookshelf/remove', bookIds, {
                params: { userId },
                headers: { Authorization: 'Bearer ' + localStorage.getItem('jwt_token') }
            })
                .then(res => {
                    if (res.data.code === 200) {
                        this.showFeedback('success', '书籍已移除！');
                        this.selectedBooks = []; // 清空选择的书籍
                        this.getBookshelf(); // 删除后刷新书架数据
                    } else {
                        this.showFeedback('error', '移除失败，请稍后重试');
                    }
                })
                .catch(err => {
                    console.error('移除书籍失败', err);
                    this.showFeedback('error', '移除失败，请稍后重试');
                });
        },
    },
    beforeMount() {
        this.getBookshelf();
    }
};
</script>

<style scoped>
.bookshelf-container {
    width: 80%;
    margin: 0 auto;
    padding: 20px;
    background: #ffffff;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.bookshelf-title {
    text-align: center;
    font-size: 2rem;
    color: #333;
    margin-bottom: 30px;
}

.book-list {
    display: grid;
    grid-template-columns: repeat(7, 1fr);
    gap: 20px;
    margin-bottom: 30px;
}

.book-card {
    position: relative;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    text-align: center;
    padding: 10px;
}

.book-actions {
    display: flex;
    justify-content: space-between;
    padding: 10px;
}

.book-checkbox {
    cursor: pointer;
}

.remove-btn {
    background-color: #ff4d4f;
    color: white;
    border: none;
    padding: 5px 10px;
    cursor: pointer;
}

.book-image {
    height: 200px;
    object-fit: cover;
    border-bottom: 2px solid #eee;
}

.book-info {
    padding: 15px;
}

.book-title {
    font-size: 1rem;
    color: #333;
    margin: 10px 0;
}

.book-author {
    font-size: 0.9rem;
    color: #666;
}

.purchase-btn,
.remove-btn {
    display: block;
    margin: 20px auto;
    padding: 10px 20px;
    font-size: 1rem;
}

.order-list {
    margin-top: 40px;
}

.order-title {
    text-align: center;
    font-size: 1.5rem;
    color: #333;
}

.order-item {
    padding: 10px;
    border-bottom: 1px solid #eee;
}

.no-books-message {
    text-align: center;
    font-size: 1.2rem;
    color: #777;
}

.dialog-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
}

.dialog {
    background: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.3);
    width: 400px;
    text-align: center;
}

.dialog-actions {
    margin-top: 20px;
}

.dialog button {
    margin: 0 10px;
    padding: 10px 20px;
    font-size: 1rem;
}

.btn-primary {
    background-color: #007bff;
    color: white;
    border: none;
}

.btn-secondary {
    background-color: #ccc;
    color: white;
    border: none;
}

.message-box {
    position: fixed;
    top: 20px;
    left: 50%;
    transform: translateX(-50%);
    padding: 10px 20px;
    border-radius: 5px;
    font-size: 16px;
    z-index: 999;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.success {
    background-color: #4CAF50;
    color: white;
}

.error {
    background-color: #FF4D4F;
    color: white;
}
</style>
