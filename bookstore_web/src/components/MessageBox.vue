<!-- MessageBox.vue -->
<template>
    <div v-if="show" class="message-box" :class="messageType">
      {{ messageText }}
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        show: false,
        messageType: '',
        messageText: ''
      };
    },
    methods: {
      // 显示消息
      showFeedback(type, message) {
        this.messageType = type;
        this.messageText = message;
        this.show = true;
  
        // 3秒后自动消失
        setTimeout(() => {
          this.show = false;
        }, 3000);
      }
    }
  };
  </script>
  
  <style scoped>
  .message-box {
    position: fixed;
    top: 20px;
    left: 50%;
    transform: translateX(-50%);
    padding: 10px 20px;
    border-radius: 5px;
    font-size: 16px;
    z-index: 9999;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  }
  
  .success {
    background-color: green;
    color: white;
  }
  
  .error {
    background-color: red;
    color: white;
  }
  </style>
  