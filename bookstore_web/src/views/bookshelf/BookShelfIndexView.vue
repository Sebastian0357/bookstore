<template>
  <div class="bookshelf-container">
    <h1 class="bookshelf-title">我的书架</h1>
    
    <div class="book-list">
      <div v-for="book in books" :key="book.id" class="book-card" @click="viewBookDetails(book.id)">
        <img :src="book.img" alt="书籍封面" class="book-image" />
        <div class="book-info">
          <h3 class="book-title">{{ book.bookname }}</h3>
          <p class="book-author">{{ book.author }}</p>
        </div>
      </div>
    </div>
    
    <div v-if="books.length === 0" class="no-books-message">
      <p>您的书架是空的，快来添加一些书籍吧！</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      books: [
        
      ]
    };
  },
  methods: {
    viewBookDetails(bookId) {
      // 跳转到书籍详情页面，或者打开一个详情弹窗
      this.$router.push(`/book/${bookId}`);
    },
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
    }
  },
  beforeMount() {
    this.getBookshelf()
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
  grid-template-columns: repeat(7, 1fr); /* 每行显示七本书 */
  gap: 20px;
  margin-bottom: 30px;
}

.book-card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  text-align: center;
  cursor: pointer;
}

.book-image {
  /* width: 100%
  ; */
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

.no-books-message {
  text-align: center;
  font-size: 1.2rem;
  color: #777;
}
</style>
