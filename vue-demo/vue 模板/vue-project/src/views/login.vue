<template>
    <div>
      <form @submit.prevent="handleSubmit">
        <div>
          <label for="username">用户名：</label>
          <input type="text" id="username" v-model="username" />
        </div>
        <div>
          <label for="password">密码：</label>
          <input type="password" id="password" v-model="password" />
        </div>
        <button type="submit">登录</button>
      </form>
    </div>
  </template>
  
  <script lang="ts">
  export default {
    data() {
      return {
        username: '',
        password: '',
      };
    },
    methods: {
      handleSubmit() {
        // 在这里可以进行登录验证的逻辑
        console.log('username:', this.username);
        console.log('password:', this.password);
      },
    },
  };
  </script>
  