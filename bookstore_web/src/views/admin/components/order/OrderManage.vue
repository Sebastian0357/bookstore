<template>
  <div>
    <div style="margin-bottom: 5px;">
      <!-- 搜索栏 -->
      <div class="input-group mb-2">
        <input v-model="orderId" type="text" class="form-control" placeholder="请输入订单号" @keyup.enter="loadOrders"
          style="max-width: 200px;">
        <input v-model="username" type="text" class="form-control" placeholder="请输入用户名" @keyup.enter="loadOrders"
          style="max-width: 200px; margin-left: 5px;">
        <!-- 分类选择框 -->

        <select v-model="type" class="form-control" style="max-width: 200px; margin-left: 5px;">
          <option value="">全部</option> <!-- 默认值 -->
          <option value="spot">景区</option>
          <option value="line">线路</option>
        </select>
        <div class="input-group-append">
          <button class="btn btn-primary" style="margin-left: 5px;" @click="loadOrders">查询</button>
          <button class="btn btn-success" style="margin-left: 5px;" @click="resetParam">重置</button>
        </div>
      </div>

      <!-- 操作按钮 -->
      <!-- <button class="btn btn-primary" style="margin-left: 5px;" @click="addOrder">新增订单</button> -->
    </div>

    <!-- 订单表格 -->
    <table class="table table-bordered">
      <thead class="thead-light">
        <tr>
          <th>订单号</th>
          <th>用户名</th>
          <th>分类</th>
          <th>名称</th>
          <th>金额</th>
          <th>支付状态</th>
          <th>创建时间</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="order in orderData" :key="order.id">
          <td>{{ order.id }}</td>
          <td>{{ order.username }}</td>
          <td>{{ order.spot_name ? "景区" : "线路" }}</td>
          <td>{{ order.spot_name ? order.spot_name : order.line_name }}</td>
          <td>{{ order.price }}</td>
          <td>{{ order.status }}</td>
          <td>{{ formatDate(order.created_at) }}</td>
          <td>
            <button class="btn btn-success btn-sm" @click="confrim(order.id)">确认</button>
            <button class="btn btn-warning btn-sm ml-2" @click="cancle(order.id)" style="margin-left: 5px;">取消</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 分页 -->
    <nav>
      <ul class="pagination justify-content-center">
        <li class="page-item" :class="{ disabled: pageNum === 1 }">
          <a class="page-link" href="#" @click="changePage(pageNum - 1)">上一页</a>
        </li>
        <li class="page-item" v-for="n in Math.ceil(total / pageSize)" :key="n" :class="{ active: pageNum === n }">
          <a class="page-link" href="#" @click="changePage(n)">{{ n }}</a>
        </li>
        <li class="page-item" :class="{ disabled: pageNum === Math.ceil(total / pageSize) }">
          <a class="page-link" href="#" @click="changePage(pageNum + 1)">下一页</a>
        </li>
      </ul>
    </nav>

    <!-- 成功/失败消息 -->
    <MessageBox ref="messageBox" />
  </div>
</template>

<script>
import axios from 'axios';
import MessageBox from '@/components/MessageBox.vue'; // 引入消息组件

export default {
  components: { MessageBox },
  data() {
    return {
      orderData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      orderId: '',
      username: '',
      type: '',
      dialogVisible: false,
      form: {
        id: '',
        orderId: '',
        username: '',
        routeName: '',
        amount: '',
        status: 0,
        createTime: ''
      },
    };
  },
  methods: {
    // 加载订单列表
    loadOrders() {
      axios.post('http://localhost:1118/order/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: { orderId: this.orderId, username: this.username, type: this.type }
      }, {
        headers: { Authorization: "Bearer " + localStorage.getItem("jwt_token") }
      }).then(res => {
        if (res.data.code === 200) {
          this.orderData = res.data.data;
          this.total = res.data.total;
        } else {
          this.$refs.messageBox.showFeedback('error', '获取数据失败');
        }
      });
    },
    // 切换分页
    changePage(val) {
      this.pageNum = val;
      this.loadOrders();
    },
    confrim(id) {
      this.update("已确认", id)
    },
    cancle(id) {
      this.update("已取消", id)
    },
    update(status, id) {
      axios.post('http://localhost:1118/order/update', { status: status, id: id }, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("jwt_token")
        }
      }).then(res => {
        if (res.data.code === 200) {
          this.$refs.messageBox.showFeedback('success', '操作成功！');

          this.centerDialogVisible = false;
          this.loadOrders();
        } else {
          this.$refs.messageBox.showFeedback('error', '操作失败！');

        }
      });
    },
    // 重置搜索条件
    resetParam() {
      this.orderId = '';
      this.username = '';
      this.type = '';
      this.loadOrders();
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
  },
  beforeMount() {
    this.loadOrders();
  }
};
</script>