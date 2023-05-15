<template>
  <dir>
    <el-button type="primary" @click="router('/login')">登录</el-button>
    <el-button type="primary" @click="logout">注销</el-button>
    <el-button type="primary" @click="router('/controller')">控制台</el-button>
  </dir>

</template>

<script type="text/ecmascript-6">
  export default {
    data() {
      return {

      };
    },
    methods: {
        router(uri) {
            this.$router.push(uri)
        },
        logout() {
          localStorage.removeItem('token');
        }
    },
  };
</script>