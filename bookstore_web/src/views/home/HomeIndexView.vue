<template>
  <div class="up">
    <div class="input-group flex-nowrap input-group-lg">
      <!-- <i class="icon-search"></i> -->
      <input type="text" class="form-control rounded-pill" placeholder="搜索" aria-label="search"
        aria-describedby="addon-wrapping" />
    </div>
    <div class="container">
      <div class="row justify-content-md-center">
        <div class="col-sm col-md-auto hot-tag">最近热搜</div>
        <a href="" class="col-sm col-md-auto serach-tag">小巷人家</a>
        <a href="" class="col-sm col-md-auto serach-tag">人生总会有答案</a>
        <a href="" class="col-sm col-md-auto serach-tag">最小阻力之路</a>
        <a href="" class="col-sm col-md-auto serach-tag">看世界</a>
      </div>
    </div>
  </div>
  <div class="mid">

    <div class="refresh">
      <div class="d-inline p-2">大家都在看</div>
      <a href="" @click.prevent="next()" class="d-inline p-2">换一批</a>
    </div>

    <div class="row row-cols-1 row-cols-md-4 g-4 cardcontainer">
      <div class="col recommend" v-for="data in tableData" :key="data">
        <div class="card h-100 card1">
          <div class="bookimg">
            <img :src=data.img class="card-img-top" alt="..." />
            <div></div>
          </div>
          <div class="card-body">
            <h5 class="card-title" prop="bookname">{{ data.bookname }}</h5>
            <p class="card-text">{{ data.author }}</p>
            <small class="text-muted">大家都在读</small>
          </div>
        </div>
      </div>
    </div>

    <div class="row row-cols-1 row-cols-md-2 g-4 cardcontainer">
      <div class="col" v-for="colIndex in 4" :key="colIndex">
        <div class="card">
          <h5 class="card-title" v-if="colIndex == 1">热度榜</h5>
          <h5 class="card-title" v-else-if="colIndex == 2">2</h5>
          <h5 class="card-title" v-else-if="colIndex == 3">3</h5>
          <h5 class="card-title" v-else-if="colIndex == 4">4</h5>

          <ul class="list-group list-group-horizontal" v-for="(data1, index) in tableData" :key="data1">
            <a class="list-group-item list-group-item-action" v-if="index * 2 < tableData.length">
              <img :src=data1.img class="listimg card-img-top" alt="..." />
              {{ tableData[index * 2].bookname }}
              <div>

              </div>
            </a>
            <a class="list-group-item list-group-item-action" v-if="index * 2 + 1 < tableData.length">
              <img :src=data1.img class="listimg card-img-top" alt="..." />
              {{ tableData[index * 2 + 1].bookname }}
              <div>

              </div>
            </a>
          </ul>

        </div>
      </div>
    </div>


  </div>
</template>
<script>
import axios from 'axios';
const state = {     // 全局管理的数据存储
  token: localStorage.getItem('jwt_token') ? localStorage.getItem('jwt_token') : '',   // token
};
export default {
  components: {},

  data() {
    return {
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      centerDialogVisible: false,
      inDialogVisible: false,
      innerVisible: false,
    }
  },
  methods: {
    loadPost() {
      axios.post('http://localhost:1118/bookinfo/listPage', {
        pageSize: 4,
        pageNum: (this.pageNum % (this.total / 4))
      }, {
        headers: {
          Authorization: "Bearer " + state.token,
        },
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
    next() {
      this.pageNum = this.pageNum + 1, 
      this.loadPost()
    },
  },
  beforeMount() {
    this.loadPost()
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

div.input-group {
  width: 50%;
  margin: 2rem auto;
  margin-bottom: 1rem;
}

.refresh {
  padding-left: 1rem;
  width: 75%;
  margin: auto;
  padding-top: 3rem;
  text-align: left;
}

div.d-inline {
  font-size: larger;
}

a.d-inline {
  text-decoration: none;
  color: black;
}

.hot-tag {
  margin-left: 1rem;
  padding: 8px;
  font-size: small;
}

.serach-tag {
  margin-left: 1rem;
  background-color: rgb(229, 230, 232);
  padding: 8px 20px 8px 20px;
  font-size: small;
  text-decoration: none;
  color: black;
  border-radius: 500px;
  text-align: center;
}

div.mid {
  margin-top: 4rem;
}

div.cardcontainer {
  width: 75%;
  margin: 0 auto;
}

div.card {
  padding-bottom: 3rem;
  border-radius: 15px;
  border-style: none;
}

.card-title {
  text-align: center;
}

div.bookimg {
  width: 35%;
  margin: auto;
  margin-top: 2rem;
}

.card-img-list {
  width: 80%;
  margin: auto;
  margin-left: 1em;
  display: flex;
}

.card-text {
  text-align: center;
}

.text-muted {
  display: flex;
  justify-content: center;
  /* 水平居中 */
}

div.col {
  margin-top: 0;
  padding: 15px;
  transition: transform 0.3s ease-in-out;
}

div.recommend:hover {
  transform: scale(1.06);
}

a.list-group-item {
  width: 50%;
}

.listimg {
  width: 25%;
}
</style>