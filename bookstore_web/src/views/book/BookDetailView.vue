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
    </div>

    <!-- 右侧 书籍评论 -->
    <div class="comments-section">
      <!-- 简介放置在评论区上方 -->
      <h3>简介:</h3>
      <p class="description"> {{ this.book.description }}</p>
      
      <div class="rating">
        <p>书籍评分:</p>
        <span v-for="n in 5" :key="n" class="star" :class="n <= this.book.rating ? 'filled' : ''">★</span>
        <span>{{ book.rating }}/5</span>
      </div>

      <h3>书籍评论:</h3>
      <ul v-if="comments.length">
        <li v-for="comment in comments" :key="comment.id">
          <p><strong>{{ comment.username }}:</strong> {{ comment.comment }}</p>
          <small>{{ formatDate(comment.time) }}</small>
        </li>
      </ul>
      <p v-else>暂无评论</p>
    </div>

  </div>
</template>


<script>
import axios from "axios";
export default {
  data() {
    return {
      book: null,
      comments: [],
    };
  },
  methods: {
    fetchBookDetail() {
      const bookId = this.$route.params.id;
      axios
        .get(`http://localhost:1118/bookinfo/${bookId}`, {
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
        .get(`http://localhost:1118/comments/book/${bookId}`, {
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
        bookId: this.book.id,
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
    }

  },
  beforeMount() {
    this.fetchBookDetail();
  },
};
</script>

<style scoped>
.book-detail-container {
  display: flex;
  justify-content: space-between;
  width: 80%;
  min-height: 800px;
  margin: 20px auto;
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  position: relative;
}

/* 左侧书籍信息 */
.book-info {
  width: 50%;
  padding: 20px;
  text-align: center;
}

.image-container {
  display: flex;
  justify-content: center;
  margin-bottom: 15px;
}

.detail-img {
  width: 100%;
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
  color: #ddd; /* 默认空星的颜色 */
}

.star.filled {
  color: #f8c200;
}

.description {
  margin-top: 15px;
  font-size: 19px;
  color: #2e2e2e;
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

/* 右侧评论区 */
.comments-section {
  width: 50%;
  padding: 20px;
}

.comments-section h3 {
  margin-bottom: 20px;
  font-size: 22px;
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
</style>
