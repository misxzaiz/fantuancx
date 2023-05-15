<style>
  .flex-grow {
    flex-grow: 1;
  }
  .el-menu {
    font-weight: bolder;
  }
  .el-menu-logo {
    font-size: x-large;
  }
</style>

<template>
  <el-menu :default-active="activeIndex" class="el-menu-demo el-menu" mode="horizontal" :ellipsis="false" @select="handleSelect" >
    <el-menu-item index="0" @click="router('/')" class="el-menu-logo">Fantuan</el-menu-item>
    <div class="flex-grow"/>
    <el-menu-item index="1" @click="router('/login')">登录</el-menu-item>
    <el-menu-item index="2" @click="logout">注销</el-menu-item>
    <el-menu-item index="3" @click="demo">Demo</el-menu-item>
    <el-menu-item index="4" @click="router('/controller')">控制台</el-menu-item>
  </el-menu>
</template>


<script type="text/ecmascript-6">
  import axios from 'axios'


  export default {
    data() {
      return {

      };
    },
    methods: {
        router(uri) {
            this.$router.push(uri)
        },
        demo(){
          const token = localStorage.getItem('token')
          axios.get(window.$uriReq+'/demo', {
            headers: {
              'Authorization': `${token}`
            }
          })
          .then(res => {
            this.$message({
              message: res.data,
              type: 'success'
            })
          })
          .catch(error => {
            this.$message({
              message: error,
              type: 'error'
            })
          })
        },
        logout() {
          localStorage.removeItem('token');
          this.$message({
            message: '注销成功！',
            type: 'success'
          })
        }
    },
  };
</script>