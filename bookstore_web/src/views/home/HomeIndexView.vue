<template>
  <div class="up">
    <div class="input-group flex-nowrap input-group-lg">
      <!-- <i class="icon-search"></i> -->
      <input
        type="text"
        class="form-control rounded-pill"
        placeholder="搜索"
        aria-label="search"
        aria-describedby="addon-wrapping"
      />
    </div>
    <div class="container">
      <div class="row justify-content-md-center">
        <div class="col-sm col-md-auto hot-tag">最近热搜</div>
        <a href="" class="col-sm col-md-auto serach-tag">我与地坛</a>
        <a href="" class="col-sm col-md-auto serach-tag">我与地坛地坛地坛</a>
        <a href="" class="col-sm col-md-auto serach-tag">我与地坛</a>
        <a href="" class="col-sm col-md-auto serach-tag">我与地坛地坛地坛</a>
      </div>
    </div>
  </div>
  <div class="mid">

    <div class="refresh">
      <div class="d-inline p-2">大家都在看</div>
      <a href="" class="d-inline p-2">换一批</a>
    </div>

    <div class="row row-cols-1 row-cols-md-4 g-4 cardcontainer">
      <div class="col">
        <div class="card h-100">
          <div class="bookimg">
            <img
              src="@/assets/images/我与地坛.png"
              class="card-img-top"
              alt="..."
            />
          </div>
          <div class="card-body">
            <h5 class="card-title">我与地坛</h5>
            <p class="card-text">余华</p>
            <small class="text-muted">大家都在读</small>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card h-100">
          <div class="bookimg">
            <img
              src="@/assets/images/我与地坛.png"
              class="card-img-top"
              alt="..."
            />
          </div>
          <div class="card-body">
            <h5 class="card-title">我与地坛</h5>
            <p class="card-text">余华</p>
            <small class="text-muted">大家都在读</small>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card h-100">
          <div class="bookimg">
            <img
              src="@/assets/images/t6_546339.jpg"
              class="card-img-top"
              alt="..."
            />
          </div>
          <div class="card-body">
            <h5 class="card-title">我与地坛</h5>
            <p class="card-text">余华</p>
            <small class="text-muted">大家都在读</small>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card h-100">
          <div class="bookimg">
            <img
              src="@/assets/images/我与地坛.png"
              class="card-img-top"
              alt="..."
            />
          </div>
          <div class="card-body">
            <h5 class="card-title">我与地坛</h5>
            <p class="card-text">余华</p>
            <small class="text-muted">大家都在读</small>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
  <script>
export default {
  components: {},
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
}
.card-title {
  text-align: center;
}
div.bookimg {
  width: 35%;
  margin: auto;
  margin-top: 2rem;
}
.card-text {
  text-align: center;
}
.text-muted {
  display: flex;
  justify-content: center; /* 水平居中 */
}
div.col {
  margin-top: 0;
  padding: 15px;
  transition: transform 0.3s ease-in-out;
}
div.card {
  border-radius: 15px;
  border-style: none;
}
div.col:hover {
  transform: scale(1.06);
}
</style>