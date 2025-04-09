<template>
    <div class="bookshelf-container">
        <h1 class="bookshelf-title">我的书架</h1>

        <!-- 按钮容器，三个按钮同一行 -->
        <div class="button-container">
            <button v-if="!isSelectionMode" @click="enterSelectionMode" class="btn btn-selection">选择模式</button>
            <button v-if="isSelectionMode" @click="exitSelectionMode" class="btn btn-selection">退出选择</button>
            <button v-if="isSelectionMode" class="btn btn-selection" @click="openConfirmDialog('purchase')" :disabled="selectedBooks.length === 0">购买</button>
            <button v-if="isSelectionMode" class="btn btn-selection" @click="openConfirmDialog('remove')" :disabled="selectedBooks.length === 0">移除</button>
        </div>

        <div class="book-list">
            <div v-for="book in books" :key="book.id" 
                class="book-card"
                @click="toggleSelection(book)"
                :class="{'selected': selectedBooks.includes(book)}">
                
                <img :src="book.img" alt="书籍封面" class="book-image" />
                <div class="book-info">
                    <h3 class="book-title">{{ book.bookname }}</h3>
                    <p class="book-author">{{ book.author }}</p>
                </div>
            </div>
        </div>

        <!-- 没有书籍的提示 -->
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
            selectedBooks: [], // 存储选中的书籍
            isSelectionMode: false, // 是否进入选择模式
            showDialog: false,
            dialogTitle: '',
            dialogMessage: '',
            action: '', // 当前操作：购买或移除
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

        // 进入选择模式
        enterSelectionMode() {
            this.isSelectionMode = true;
        },

        // 退出选择模式
        exitSelectionMode() {
            this.isSelectionMode = false;
            this.selectedBooks = []; // 清空已选的书籍
        },

        // 切换书籍选择状态
        toggleSelection(book) {
            if (this.isSelectionMode) {
                const index = this.selectedBooks.indexOf(book);
                if (index === -1) {
                    this.selectedBooks.push(book); // 添加到选中列表
                } else {
                    this.selectedBooks.splice(index, 1); // 从选中列表移除
                }
            } else {
                // 退出选择模式时默认跳转到书籍详情
                this.goToDetail(book.id);
            }
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
                        this.selectedBooks = []; // 清空选择的书籍
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

        removeBooks() {
            const userId = this.$store.state.user.id;
            const bookIds = this.selectedBooks.map(book => book.id);

            axios.post('http://localhost:1118/bookshelf/remove', bookIds, {
                params: { userId },
                headers: { Authorization: 'Bearer ' + localStorage.getItem('jwt_token') }
            })
                .then(res => {
                    if (res.data.code === 200) {
                        this.showFeedback('success', '书籍已移除！');
                        this.selectedBooks = [];
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

        goToDetail(bookId) {
            this.$router.push(`/book/${bookId}`);
        }
    },
    beforeMount() {
        this.getBookshelf();
    }
};
</script>

<style scoped>
.bookshelf-container {
    width: 80%;
    aspect-ratio: 1.8;
    margin: 40px auto;
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

.button-container {
    display: flex;
    justify-content: flex-start;
    gap: 5px; /* 统一按钮间距为 5px */
    margin-bottom: 20px;
}

.btn {
    padding: 8px 20px;
    font-size: 1rem;
    border-radius: 5px;
    border: none;
    cursor: pointer;
    margin-left: 5px;
}

.btn-selection {
    background-color: #f0f0f0; /* 统一颜色为选择模式的背景色 */
    color: #333;
}

.book-list {
    display: grid;
    grid-template-columns: repeat(7, 1fr);
    gap: 20px;
    margin-bottom: 20px;
}

.book-card {
    position: relative;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    text-align: center;
    aspect-ratio: 1 / 1.618;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    transition: background-color 0.3s ease;
}

.book-card.selected {
    background-color: #f0f0f0;
    border: 2px solid #ff6600;
}

.book-image {
    width: 100%;
    height: auto;
    max-height: 65%;
    object-fit: contain;
}

.book-info {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.book-title {
    font-size: 1rem;
    color: #333;
    margin: 5px 0;
}

.book-author {
    font-size: 0.9rem;
    color: #666;
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
    background-color: #F44336;
    color: white;
}

.dialog-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background: rgba(0, 0, 0, 0.3);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
}

.dialog {
    background: white;
    padding: 20px 30px;
    border-radius: 8px;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
    width: 300px;
    max-width: 80%;
    text-align: center;
}

</style>
