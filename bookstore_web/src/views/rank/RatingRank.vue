<template>
    <div class="hot-rank-page">
        <div class="container">
            <h2 class="text-center my-4">评分榜</h2>
            <div class="row row-cols-1 row-cols-md-4 g-4">
                <div class="col" v-for="book in books" :key="book.id" @click="goToDetail(book.id)">
                    <div class="card h-100 card1">
                        <div class="bookimg">
                            <img :src="book.img" class="card-img-top" alt="书籍封面" />
                        </div>
                        <div class="card-body">
                            <h5 class="card-title">{{ book.bookname }}</h5>
                            <p class="card-text">{{ book.author }}</p>
                            <small class="text-muted">评分：{{ book.rating }}</small>
                        </div>
                    </div>
                </div>
            </div>
            <div class="text-center mt-4">
                <button class="btn btn-outline-primary me-2" @click="prevPage" :disabled="currentPage === 1">上一页</button>
                <span class="mx-2">第 {{ currentPage }} 页</span>
                <button class="btn btn-outline-primary ms-2" @click="nextPage">下一页</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

const state = {
    token: localStorage.getItem('jwt_token') || ''
};

export default {
    data() {
        return {
            books: [],
            currentPage: 1,
            pageSize: 12
        };
    },
    methods: {
        loadBooks() {
            axios.post('http://localhost:1118/bookinfo/ratingRank', {
                pageNum: this.currentPage,
                pageSize: this.pageSize
            }, {
                headers: { Authorization: 'Bearer ' + state.token }
            }).then(res => {
                if (res.data.code === 200) {
                    this.books = res.data.data;
                } else {
                    alert('加载评分榜失败');
                }
            }).catch(err => {
                console.error('请求失败', err);
            });
        },
        nextPage() {
            this.currentPage++;
            this.loadBooks();
        },
        prevPage() {
            if (this.currentPage > 1) {
                this.currentPage--;
                this.loadBooks();
            }
        },
        goToDetail(bookId) {
            this.$router.push(`/book/${bookId}`);
        }
    },
    created() {
        this.loadBooks();
    }
};
</script>


<style scoped>
.hot-rank-page {
    background-color: rgb(244, 245, 247);
    min-height: 100vh;
    padding: 2rem 0;
}

.card1 {
    transition: transform 0.3s ease-in-out;
    border-radius: 15px;
    border-style: none;
}

.card1:hover {
    transform: scale(1.05);
}

.bookimg {
    width: 35%;
    margin: auto;
    margin-top: 2rem;
}

.card-img-top {
    width: 100%;
}

.card-title {
    text-align: center;
}

.card-text {
    text-align: center;
}

.text-muted {
    display: flex;
    justify-content: center;
}
</style>