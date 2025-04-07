<template>
    <div class="up">
        <!-- 搜索框部分 -->
        <div class="input-group flex-nowrap input-group-lg position-relative">
            <input v-model="searchQuery" @input="searchBooks" type="text" class="form-control rounded-pill"
                placeholder="搜索" aria-label="search" aria-describedby="addon-wrapping" />

            <!-- 搜索结果 -->
            <div v-if="books.length > 0" class="search-results mt-4">
                <h3>搜索结果</h3>
                <ul>
                    <li v-for="book in books" :key="book.id" @click="increaseSearchCount(book.id)">
                        <div class="book-item">
                            <img :src="book.img" alt="书籍封面" class="book-img" />
                            <div class="book-info">
                                <h5>{{ book.bookname }}</h5>
                                <p><strong>作者:</strong> {{ book.author }}</p>
                                <p><strong>出版社:</strong> {{ book.press }}</p>
                                <p><strong>价格:</strong> ¥{{ book.price }}</p>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>

            <!-- 没有找到结果时的提示 -->
            <div v-else-if="searchQuery" class="search-results mt-4">
                <p>没有找到相关书籍。</p>
            </div>
        </div>

        <!-- 最近热搜部分 -->
        <div class="container">
            <div class="row justify-content-md-center">
                <div class="col-sm col-md-auto hot-tag">最近热搜</div>
                <a href="" class="col-sm col-md-auto serach-tag" v-for="data in hotSearch" :key="data"
                    @click="goToDetail(data.id)">{{ data.bookname
                    }}</a>
            </div>
        </div>
    </div>

    <div class="mid">
        <!-- 换一批部分 -->
        <div class="refresh">
            <div class="d-inline p-2">大家都在看</div>
            <a href="" @click.prevent="next()" class="d-inline p-2 rank-tag" >换一批</a>
        </div>

        <!-- 推荐书籍部分 -->
        <div class="row row-cols-1 row-cols-md-4 g-4 cardcontainer">
            <div class="col recommend" v-for="data in tableData" :key="data" @click="goToDetail(data.id)">
                <div class="card h-100 card1">
                    <div class="bookimg">
                        <img :src="data.img" class="card-img-top" alt="..." />
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">{{ data.bookname }}</h5>
                        <p class="card-text">{{ data.author }}</p>
                        <small class="text-muted">大家都在读</small>
                    </div>
                </div>
            </div>
        </div>

        <!-- 热度榜和其他书籍部分 -->
        <div class="row row-cols-1 row-cols-md-2 g-4 cardcontainer">
            <div class="col" v-for="(tableData, colIndex) in tableList" :key="colIndex">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">{{ colIndex === 0 ? "热度榜" :
                            colIndex === 1 ? "新书榜" :
                                colIndex === 2 ? "畅销榜" :
                                    "评分榜" }}</h5>

                        <!-- 书籍列表部分 -->
                        <div class="grid-container">
                            <div class="grid-item" v-for="(data, index) in tableData" :key="index"
                                @click="goToDetail(data.id)">
                                <img :src="data.img" class="listimg card-img-top" alt="..." />
                                <div class="grid-item-text">
                                    <p>{{ data.bookname }}</p>
                                </div>
                            </div>
                            <router-link :to="getRankPath(colIndex)" class="rank-tag">查看全部</router-link>
                        </div>

                    </div>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
import axios from 'axios';

const state = {
    token: localStorage.getItem('jwt_token') ? localStorage.getItem('jwt_token') : '',
};

export default {
    data() {
        return {
            rankTitles: ["热度榜", "新书榜", "畅销榜", "评分榜"],
            tableList: [[], [], [], []],
            tableData: [],
            pageSize: 10,
            pageNum: 1,
            total: 0,
            searchQuery: "",  // 搜索框的输入内容
            books: [],        // 存储搜索结果
            hotSearch: [],  // 存储最近热搜
        }
    },
    methods: {
        loadPost() {
            axios.post('http://localhost:1118/bookinfo/listPage', {
                pageSize: 4,
                pageNum: (this.pageNum % (this.total / 4))
            }, {
                headers: { Authorization: "Bearer " + state.token },
            }).then(res => res.data).then(res => {
                if (res.code == 200) {
                    this.tableData = res.data;
                    this.total = res.total;
                } else {
                    alert('获取数据失败');
                }
            });
        },
        next() {
            this.pageNum = this.pageNum + 1;
            this.loadPost();
        },
        loadAllRanks() {
            // 定义想要获取的排行榜数量，所有榜单返回前 4 条
            const limit = 4;

            axios.all([
                axios.post('http://localhost:1118/bookinfo/hotRank', { pageSize: limit }, { headers: { Authorization: "Bearer " + state.token } }),
                axios.post('http://localhost:1118/bookinfo/newBookRank', { pageSize: limit }, { headers: { Authorization: "Bearer " + state.token } }),
                axios.post('http://localhost:1118/bookinfo/bestSellerRank', { pageSize: limit }, { headers: { Authorization: "Bearer " + state.token } }),
                axios.post('http://localhost:1118/bookinfo/ratingRank', { pageSize: limit }, { headers: { Authorization: "Bearer " + state.token } }),
                axios.post('http://localhost:1118/bookinfo/hotSearch', { pageSize: limit }, { headers: { Authorization: "Bearer " + state.token } })
            ]).then(axios.spread((hotRes, newRes, bestRes, ratingRes, searchRes) => {
                if (hotRes.data.code === 200) this.tableList[0] = hotRes.data.data;
                if (newRes.data.code === 200) this.tableList[1] = newRes.data.data;
                if (bestRes.data.code === 200) this.tableList[2] = bestRes.data.data;
                if (ratingRes.data.code === 200) this.tableList[3] = ratingRes.data.data;
                if (searchRes.data.code === 200) this.hotSearch = searchRes.data.data;
            }));

        },
        goToDetail(bookId) {
            this.$router.push(`/book/${bookId}`);
        },
        searchBooks() {
            if (this.searchQuery.trim() === "") {
                this.books = [];  // 如果搜索框为空，清空结果
                return;
            }
            // 显示加载提示
            this.loading = true;
            axios
                .get("http://localhost:1118/bookinfo/search", {
                    params: {
                        query: this.searchQuery,  // 传递搜索关键词
                    },
                    headers: {
                        Authorization: "Bearer " + state.token,
                    },
                })
                .then((res) => {
                    if (res.data.code === 200) {
                        this.books = res.data.data;  // 更新搜索结果
                    } else {
                        this.books = [];  // 如果没有找到相关书籍，清空结果
                        console.log('没有找到相关书籍');
                    }
                })
                .catch((error) => {
                    console.error("搜索失败:", error);
                    this.books = [];  // 出现错误时清空结果
                })
                .finally(() => {
                    // 隐藏加载提示
                    this.loading = false;
                });
        },
        // 方法：增加书籍搜索次数
        increaseSearchCount(bookId) {
            axios.post('http://localhost:1118/bookinfo/increaseSearchCount', {
                bookId: bookId,
            }, {
                headers: { Authorization: "Bearer " + state.token },
            })
                .then(res => {
                    if (res.data.code === 200) {
                        console.log('搜索次数更新成功' + res);
                        this.goToDetail(bookId);
                    } else {
                        alert('增加搜索次数失败');
                    }
                })
                .catch(error => {
                    console.error('请求错误', error);
                    alert('请求失败');
                });
        },
        getRankPath(index) {
            return index === 0 ? '/hotrank/' :
                index === 1 ? '/newbookrank/' :
                    index === 2 ? '/bestsellerrank/' : '/ratingrank/';
        },

    },
    beforeMount() {
        this.loadPost();
        this.loadAllRanks();
    }

};
</script>

<style scoped>
.up {
    background-color: rgb(237, 238, 240);
}

.mid {
    background-color: rgb(244, 245, 247);
}

.input-group {
    width: 50%;
    margin: 2rem auto;
}

.refresh {
    padding-left: 1rem;
    width: 75%;
    margin: auto;
    padding-top: 3rem;
    text-align: left;
}

.hot-tag {
    margin-left: 1rem;
    padding: 8px;
    font-size: small;
}

.serach-tag {
    margin-left: 1rem;
    background-color: rgb(229, 230, 232);
    padding: 8px 20px;
    font-size: small;
    text-decoration: none;
    color: black;
    border-radius: 500px;
}

.cardcontainer {
    width: 75%;
    margin: 0 auto;
}

.card {
    padding-bottom: 3rem;
    border-radius: 15px;
    border-style: none;
}

.card-title {
    text-align: center;
}

.bookimg {
    width: 35%;
    margin: auto;
    margin-top: 2rem;
}

.card-img-top {
    width: 100%;
}

.card-text {
    text-align: center;
}

.text-muted {
    display: flex;
    justify-content: center;
}

.col {
    margin-top: 0;
    padding: 15px;
    transition: transform 0.3s ease-in-out;
}

.recommend:hover {
    transform: scale(1.06);
}

/* 使图片适应 */
.listimg {
    width: 25%;
}

.grid-container {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    /* 两列布局 */
    gap: 16px;
    /* 项目之间的间距 */
}

.grid-item {
    display: flex;
    align-items: center;
    padding: 10px;
    border-bottom: 1px solid #ddd;
    /* 每行之间的浅灰色下划线 */
}

.grid-item img {
    margin-right: 10px;
    /* 图片和文本之间的间距 */
    width: 50px;
    /* 根据需要设置图片宽度 */
    height: auto;
}

.grid-item-text {
    display: flex;
    flex-direction: column;
    justify-content: center;
    text-align: left;
}

.img-container {
    width: 40%;
    /* 设置图片容器宽度 */
    display: inline-block;
    /* 使图片块级显示，且与文字保持并排 */
}

.listimg {
    width: 100%;
    /* 图片占满容器的宽度 */
}

.bookname {
    margin-left: 10px;
    /* 给书名添加一点左边距 */
    display: inline-block;
    /* 让书名与图片同一行 */
    vertical-align: top;
    /* 让书名垂直对齐到顶部 */
}

.input-group {
    position: relative;
}

/* 搜索结果样式 */
.search-results {
    position: absolute;
    top: 100%;
    /* 定位在搜索框下方 */
    left: 0;
    right: 0;
    background-color: white;
    border: 1px solid #ddd;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    max-height: 600px;
    overflow-y: auto;
    z-index: 999;
    padding: 10px;
}

.search-results ul {
    list-style: none;
    padding: 0;
    margin: 0;
}

.search-results li {
    display: flex;
    align-items: center;
    padding: 10px 0;
    cursor: pointer;
    transition: background-color 0.3s;
}

.search-results li:hover {
    background-color: #f1f1f1;
}

.book-item {
    display: flex;
    align-items: center;
}

.book-img {
    width: 50px;
    height: 75px;
    object-fit: cover;
    margin-right: 10px;
}

.book-info h5 {
    margin: 0;
    font-size: 16px;
}

.book-info p {
    margin: 5px 0;
    font-size: 12px;
}

.no-results {
    margin-top: 10px;
    color: gray;
    font-size: 14px;
}

.rank-tag {
    text-decoration: none;
    /* 去下划线 */
    color: black;
    /* 统一颜色 */
}

.rank-tag:hover {
    text-decoration: underline;
}
</style>
